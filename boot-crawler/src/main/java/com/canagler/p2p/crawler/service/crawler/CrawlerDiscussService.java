package com.canagler.p2p.crawler.service.crawler;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.*;
import com.canagler.p2p.crawler.service.*;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 支持部分类型：（GET/POPST && 全局分页/局部分页 && 分页菜单显示最后一页页码 && 每页标题跟内容在同一页）
 * Created by scq on 2018-04-08 14:23:16
 */
@Service(value = "crawlerDiscussService")
public class CrawlerDiscussService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerDiscussService.class);

	// 网贷之家TOKEN
	@Value("${cookie.wdzj}")
	public String COOKIE_WDZJ;

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private WebTokenService webTokenService;
	@Autowired
	private WebConfigService webConfigService;
	@Autowired
	private WebInfoOptionService webInfoOptionService;
	@Autowired
	private CrawlerResultService crawlerResultService;

	/**
	 * 根据首页获取页数，抓取每页信息，匹配关键字，保存结果到数据库
	 * @param url：首页地址
	 * @param params：关键字
	 * @param name：网页名称
	 */
	@Transactional
	public void crawlerDiscuss(String id, String url, String params, String name) {
		Integer processStatus;
		try {
			// 获取配置数据
			WebInfoOption webInfoOption  = webInfoOptionService.findById(id);
			WebToken webToken = webTokenService.findById(id);
			WebConfig webConfig = webConfigService.findById(id);
			if (webInfoOption == null || webToken == null || webConfig == null) {
				return;
			}
			// 获取首页内容
			String pageContent = CrawlerUtil.getPageInfo(url, Constants.REQUEST_GET, webInfoOption.getLoginRequired(), COOKIE_WEIDAI);
			// 获取页数
			Map<String, Object> pageInfoMap = CrawlerUtil.getPageNumAndPageUrls(pageContent, webConfig.getPagingSelector(), webConfig.getPageSeparator());
			// 获取分页的具体俩段URI（eg: prefix = "/list-0-0-" suffix = ".html"）
			Integer pageNum = (Integer) pageInfoMap.get("pageBound");
			String pageUris = webConfig.getPageMethod();
			// URI拼接
			String[] uriReges = new String[2];
			if (pageUris.contains(":")) {
				uriReges = pageUris.split(":");
			} else {
				uriReges[0] = "";
				uriReges[1] = "";
			}
			// 开始处理
			webInfoService.updateProcessStatus(id, Constants.PROCESS_IN_OPERATION);
			// 构造每页的具体URL链接地址，获取每页内容，匹配关键字
			for (int i = 1; i <= pageNum; i++) {
				String prePageUrl = webConfig.getPagingUrl() + uriReges[0] + i + uriReges[1];
				String prePageInfo = CrawlerUtil.getPageInfo(prePageUrl, webInfoOption.getRequestType(), webInfoOption.getLoginRequired(), COOKIE_WEIDAI);
				List<String> matchedParamList = new ArrayList<>();
				CrawlerResult crawlerResult = new CrawlerResult();
				// 评论内容跟标题不在同一页
				if (Constants.CONTENT_LOCATION_DIFF.equals(webInfoOption.getContentLocationType())) {
					Map<String, String> titleInfoMap = CrawlerUtil.getCrawlerTitles(url, pageContent, webConfig.getPageContentSelector());
					Iterator iterator = titleInfoMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						String title = (String) entry.getKey();
						if (title.contains(Constants.COMPANY_NAME_QSW)) {
							// 深入匹配
							String contentLink = url + entry.getValue();
							String contentDetail = CrawlerUtil.getPageInfo(contentLink, Constants.REQUEST_GET, Constants.LOGIN_NOT_REQUIRED, COOKIE_WEIDAI);
							String selectedDetail = CrawlerUtil.getCrawlerPageContent(contentDetail, webConfig.getContentDetailSelector());
							matchedParamList = CrawlerUtil.matchParams(selectedDetail, params);
							crawlerResult = new CrawlerResult(prePageUrl, name, id, i, new Date(), JSON.toJSONString(matchedParamList));
						}
					}
				} else if (Constants.CONTENT_LOCATION_SAME.equals(webInfoOption.getContentLocationType())) {
					String prePageContent = CrawlerUtil.getCrawlerPageContent(prePageInfo, webConfig.getPageContentSelector());
					matchedParamList = CrawlerUtil.matchParams(prePageContent, params);
					crawlerResult = new CrawlerResult(prePageUrl, name, id, i, new Date(), JSON.toJSONString(matchedParamList));
				}
				if (matchedParamList.size() > 0) {
					crawlerResult.setStatus(Constants.CRAWLER_RESULT_MACHED_PROCESS);
				} else {
					crawlerResult.setStatus(Constants.CRAWLER_RESULT_NOT_MACHED);
				}
				crawlerResultService.save(crawlerResult);
			}
			processStatus = Constants.PROCESS_SUCCESS_END;
		} catch (Exception e) {
			processStatus = Constants.PROCESS_FAIL_END;
			LOGGER.error("抓取数据出错：{}", e.getMessage());
		}
		WebInfo webInfo = webInfoService.findById(id);
		if (webInfo != null) {
			Integer reuqestNum = webInfo.getRequestNum();
			webInfo.setRequestNum(++reuqestNum);
			webInfoService.updateRequestNum(id, reuqestNum);
		}
		webInfoService.updateProcessStatus(id, processStatus);
	}
}
