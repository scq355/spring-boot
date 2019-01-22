package com.canagler.p2p.crawler.service.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.*;
import com.canagler.p2p.crawler.service.*;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 债权转让
 * Created by scq on 2018-07-12 10:50:44
 */
@Service(value = "/crawlerFinanceService")
public class CrawlerFinanceService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerFinanceService.class);

	public static Integer count = 0;

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private WebTokenService webTokenService;
	@Autowired
	private WebConfigService webConfigService;
	@Autowired
	private WebInfoOptionService webInfoOptionService;

	/**
	 * 获取网页概览信息
	 * 获取分页信息
	 * 获取登录权限凭证
	 * 循环请求网页，遍历数据，数据转换，保存入库
	 */
	@Transactional
	public void crawlerFinance(String id) {
		Integer processStatus;
		try {
			// 获取配置数据
			WebInfoOption webInfoOption  = webInfoOptionService.findById(id);
			WebToken webToken = webTokenService.findById(id);
			WebConfig webConfig = webConfigService.findById(id);
			if (webInfoOption == null || webToken == null || webConfig == null) {
				return;
			}
			// 开始处理
			webInfoService.updateProcessStatus(id, Constants.PROCESS_IN_OPERATION);
			// 构造每页的具体URL链接地址，获取每页内容，匹配关键字
			for (int i = 1; i <= webConfig.getTotalPageNum(); i++) {
				String prePageUrl = webConfig.getPagingUrl() + i;
				String prePageInfo = CrawlerUtil.getPageInfo(prePageUrl, webInfoOption.getRequestType(), webInfoOption.getLoginRequired(), webToken.getCertificate());
				if ("陆金所".equals(webInfoOption.getName())) {
					getInfoFromLjs(prePageInfo, webInfoOption.getName());
				} else if ("微贷网".equals(webInfoOption.getName())) {
					++count;
					getInfoFromWdw(prePageInfo, webInfoOption.getName());
				}
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


	/**
	 * 获取陆金所的标的信息
	 * @param prePageInfo：网页信息
	 * @param webName：网站名称
	 */
	public void getInfoFromLjs(String prePageInfo, String webName) {
		// 从页面中抽取需要的数据
		List<String> nameList = CrawlerUtil.getCrawlerContent(prePageInfo, ".product-name");
		List<String> rateList = CrawlerUtil.getCrawlerContent(prePageInfo, ".interest-rate p");
		List<String> deadlineList = CrawlerUtil.getCrawlerContent(prePageInfo, ".invest-period p");
		List<String> priceList = CrawlerUtil.getCrawlerContent(prePageInfo, ".collection-currency");
		List<String> adjustmentList = CrawlerUtil.getCrawlerContent(prePageInfo, ".product-depreciation p");
		List<String> transferList = CrawlerUtil.getCrawlerContent(prePageInfo, ".product-amount em");

		Integer num = nameList.size();

		List<FinanceCreditor> financeCreditorList = new ArrayList<>(num);

		for (int i = 0; i < num; i++) {
			String id = nameList.get(i).replace(" 转", "").replace("稳盈-安e+ ", "");
			String name = nameList.get(i).replace(" 转", "");
			String rate = rateList.get(i).replace("%", "");
			String deadline = deadlineList.get(i).replace("个月", "");
			String price = priceList.get(i).replace(",", "").replace("元", "");
			String adjustment = adjustmentList.get(i).split(" ")[0];
			String transfer = transferList.get(i).replace(",", "").replace("元", "");

			FinanceCreditor financeCreditor = new FinanceCreditor(null,
					name,
					new Integer(deadline) * 30,
					new BigDecimal(price),
					new BigDecimal(adjustment),
					new Double(transfer),
					new BigDecimal(rate),
					webName);
			financeCreditorList.add(financeCreditor);
		}
		mongoTemplate.insertAll(financeCreditorList);
	}


	/**
	 * 获取微贷网的标的信息
	 * @param prePageInfo：网页信息
	 * @param webName：网站名称
	 */
	public void getInfoFromWdw(String prePageInfo, String webName) {

		JSONObject resultObject = JSONObject.parseObject(prePageInfo);

		if (!"1000".equals(resultObject.getString("resultCode"))) {
			LOGGER.warn("请求结果失败！");
			return;
		}

		JSONArray dataArray = JSONArray.parseArray(resultObject.getJSONObject("data").getString("data"));

		List<FinanceCreditor> financeCreditorList = new ArrayList<>(dataArray.size());
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) dataArray.get(i);
			String id = jsonObject.getString("goodsNo");
			String name = jsonObject.getString("goodsTitle");
			BigDecimal rate = jsonObject.getBigDecimal("rate").multiply(new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP));
			String deadlineStr = jsonObject.getString("durationDesc").replace("个月","/").replace("天", "");
			String month = deadlineStr.split("/")[0];
			String day = deadlineStr.split("/")[1];
			Integer deadline = new Integer(month) * 30 + new Integer(day);
			BigDecimal price = jsonObject.getBigDecimal("goodsValue");
			BigDecimal adjustment = jsonObject.getBigDecimal("goodsPrice");
			Double transfer = jsonObject.getDouble("goodsPrice");

			FinanceCreditor financeCreditor = new FinanceCreditor(null,
					name,
					deadline,
					price,
					adjustment,
					transfer,
					rate,
					webName);

			financeCreditorList.add(financeCreditor);
		}
		System.out.println("================================第" + count + "批数据插入......");
		mongoTemplate.insertAll(financeCreditorList);
	}
}
