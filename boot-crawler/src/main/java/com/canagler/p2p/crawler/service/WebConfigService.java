package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.WebConfig;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.repository.WebConfigRepository;
import com.google.common.base.Strings;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by scq on 2018-04-08 11:26:17
 */
@Service(value = "webConfigService")
public class WebConfigService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfigService.class);

	@Autowired
	private WebInfoOptionService webInfoOptionService;
	@Autowired
	private WebConfigRepository webConfigRepository;
	@Autowired
	private WebInfoService webInfoService;

	/**
	 * 保存网页配置
	 * @param pageMethod：分页详细地址
	 * @param webInfoId：网站ID
	 */
	public void saveWebConfig(String webInfoId, String pagingUrl,
							  String name, String pageMethod, String contentDetailSelector,
							  String pageContentSelector, String pagingSelector,
							  String pageSeparator, Integer totalPageNum) {
		try {
			webConfigUpdateWebInfo(webInfoId);
			pageSeparator = "space".equals(pageSeparator) ? " " : pageSeparator;
			webConfigRepository.save(new WebConfig(webInfoId, pagingUrl, name,
					pageMethod, contentDetailSelector, pageContentSelector, pagingSelector, pageSeparator, totalPageNum));
		} catch (Exception e) {
			LOGGER.error("保存分页配置出错：{}", e.getMessage());
		}
	}

	/**
	 * 根据ID获取页面的配置
	 * @param id：ID
	 */
	public WebConfig findById(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return null;
		}
		Optional<WebConfig> webConfig = webConfigRepository.findById(id);
		return webConfig.orElse(null);
	}

	/**
	 * 网页配置列表
	 */
	public List<WebConfig> list() {
		return webConfigRepository.findAll();
	}

	/**
	 * 网页配置修改
	 * @param id：ID
	 * @param pagingUrl：分页地址
	 * @param pageMethod：分页方式
	 * @param pageContentSelector：内容截取样式
	 * @param pagingSelector：分页截取样式
	 * @param pageSeparator：分页分隔符
	 */
	public UpdateResult updateWebConfig(String id, String pagingUrl, String pageMethod,
										String pageContentSelector, String contentDetailSelector,
										String pagingSelector, String pageSeparator, Integer totalPageNum) {
		try {
			webConfigUpdateWebInfo(id);
			Query query = new Query(Criteria.where("_id").is(id));
			pageSeparator = "space".equals(pageSeparator) ? " " : pageSeparator;
			Update update = new Update();
			update.set("pagingUrl", pagingUrl);
			update.set("pageMethod", pageMethod);
			update.set("pageContentSelector", pageContentSelector);
			update.set("contentDetailSelector", contentDetailSelector);
			update.set("pagingSelector", pagingSelector);
			update.set("pageSeparator", pageSeparator);
			update.set("totalPageNum", totalPageNum);
			return mongoTemplate.updateMulti(query, update, WebConfig.class);
		} catch (Exception e) {
			LOGGER.error("网页配置修改出错：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 更新关联状态
	 * @param webInfoId：ID
	 */
	private void webConfigUpdateWebInfo(String webInfoId) {
		WebInfo webInfo = webInfoService.findById(webInfoId);
		if (webInfo != null) {
			Integer processStatus = Constants.PROCESS_STATUS_PAGIN;
			WebInfoOption webInfoOption = webInfoOptionService.findById(webInfoId);
			if (webInfoOption != null) {
				if (Constants.LOGIN_NOT_REQUIRED.equals(webInfoOption.getLoginRequired())) {
					processStatus = Constants.PROCESS_STATUS_LOGIN;
				}
			}
			webInfo.setProcessStatus(processStatus);
			webInfoService.save(webInfo);
		}
	}
}
