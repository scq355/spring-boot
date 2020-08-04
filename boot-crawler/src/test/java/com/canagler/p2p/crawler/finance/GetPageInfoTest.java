package com.canagler.p2p.crawler.finance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.FinanceCreditor;
import com.canagler.p2p.crawler.service.SchedulerService;
import com.canagler.p2p.crawler.service.crawler.CrawlerDiscussService;
import com.canagler.p2p.crawler.service.crawler.CrawlerFinanceService;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取一张网页，将数据转换保存到对象模型中
 * Created by scq on 2018-07-12 16:45:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetPageInfoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetPageInfoTest.class);

	@Value("${cookie.weidai}")
	public String COOKIE_WEIDAI;
	@Autowired
	CrawlerDiscussService crawlerDiscussService;
	@Autowired
	CrawlerFinanceService crawlerFinanceService;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	SchedulerService schedulerService;

	/**
	 * 陆金所
	 */
	@Test
	public void testGetPageInfo() {
		String url = "https://list.lu.com/list/transfer-p2p?currentPage=4";
		Integer requestType = Constants.REQUEST_GET;
		Integer loginRequired = Constants.LOGIN_REQUIRED;
		try {
			String result = CrawlerUtil.getPageInfo(url, requestType, loginRequired, COOKIE_WEIDAI);

			List<String> nameList = CrawlerUtil.getCrawlerContent(result, ".product-name");
			List<String> rateList = CrawlerUtil.getCrawlerContent(result, ".interest-rate p");
			List<String> deadlineList = CrawlerUtil.getCrawlerContent(result, ".invest-period p");
			List<String> priceList = CrawlerUtil.getCrawlerContent(result, ".collection-currency");
			List<String> adjustmentList = CrawlerUtil.getCrawlerContent(result, ".product-depreciation p");
			List<String> transferList = CrawlerUtil.getCrawlerContent(result, ".product-amount em");

			Integer num = nameList.size();

			List<FinanceCreditor> financeCreditorList = new ArrayList<>(num);

			for (int i = 0; i < num; i++) {
				FinanceCreditor financeCreditor = new FinanceCreditor();
				String id = nameList.get(i).replace(" 转", "").replace("稳盈-安e+ ", "");
				String name = nameList.get(i).replace(" 转", "");
				String rate = rateList.get(i).replace("%", "");
				String deadline = deadlineList.get(i).replace("个月", "");
				String price = priceList.get(i).replace(",", "").replace("元", "");
				String adjustment = adjustmentList.get(i).split(" ")[0];
				String transfer = transferList.get(i).replace(",", "").replace("元", "");

//				System.out.println("[" + name + " * " + rate + " * " + deadline + " * " + price + " * " + adjustment + " * " + transfer + "]");
				financeCreditor.setId(id);
				financeCreditor.setName(name);
				financeCreditor.setDeadline(new Integer(deadline));
				financeCreditor.setAnnualRate(new BigDecimal(rate));
				financeCreditor.setPrice(new BigDecimal(price));
				financeCreditor.setAdjustment(new BigDecimal(adjustment));
				financeCreditor.setTransferAmount(new Double(transfer));
				financeCreditor.setWebName("陆金所");

				financeCreditorList.add(financeCreditor);
			}

//			mongoTemplate.insertAll(financeCreditorList);

			System.out.println(JSON.toJSONString(financeCreditorList));

		} catch (IOException e) {
			LOGGER.error("错误={}", e.getMessage());
		}
	}

	/**
	 * 微贷网
	 */
	@Test
	public void testGetPageInfoI() {
		String url = "https://www.weidai.com.cn/list/transferList.json?periodType=0&sort=0&page=2&rows=10";
		Integer requestType = Constants.REQUEST_GET;
		Integer loginRequired = Constants.LOGIN_REQUIRED;

		try {
			String result = CrawlerUtil.getPageInfo(url, requestType, loginRequired, COOKIE_WEIDAI);

			JSONObject resultObject = JSONObject.parseObject(result);

			if (!"1000".equals(resultObject.getString("resultCode"))) {
				LOGGER.warn("请求结果失败！");
			}

			JSONArray dataArray = JSONArray.parseArray(resultObject.getJSONObject("data").getString("data"));

			List<FinanceCreditor> financeCreditorList = new ArrayList<>(dataArray.size());
			for (int i = 0; i < dataArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) dataArray.get(i);
				String name = jsonObject.getString("goodsTitle");
				BigDecimal rate = jsonObject.getBigDecimal("rate");
				String deadline = jsonObject.getString("durationDesc");
				BigDecimal price = jsonObject.getBigDecimal("goodsValue");
				BigDecimal adjustment = jsonObject.getBigDecimal("goodsPrice");
				Double transfer = jsonObject.getDouble("goodsPrice");

				FinanceCreditor financeCreditor = new FinanceCreditor();
				financeCreditor.setName(name);
				financeCreditor.setDeadline(new Integer(deadline));
				financeCreditor.setAnnualRate(rate);
				financeCreditor.setPrice(price);
				financeCreditor.setAdjustment(adjustment);
				financeCreditor.setTransferAmount(transfer);
				financeCreditor.setWebName("微贷网");

				financeCreditorList.add(financeCreditor);
			}

			System.out.println(JSON.toJSONString(financeCreditorList));

		} catch (IOException e) {
			LOGGER.error("错误={}", e.getMessage());
		}
	}


	@Test
	public void testInvoke() throws IOException {

		String url = "https://list.lu.com/list/transfer-p2p?currentPage=4";
		Integer requestType = Constants.REQUEST_GET;
		Integer loginRequired = Constants.LOGIN_REQUIRED;

		String result = CrawlerUtil.getPageInfo(url, requestType, loginRequired, COOKIE_WEIDAI);
		crawlerFinanceService.getInfoFromLjs(result, "陆金所");

//		String urls = "https://www.weidai.com.cn/list/transferList.json?periodType=0&sort=0&page=2&rows=10";
//		Integer requestTypes = Constants.REQUEST_GET;
//		Integer loginRequireds = Constants.LOGIN_REQUIRED;
//		String results = CrawlerUtil.getPageInfo(urls, requestTypes, loginRequireds, COOKIE_WEIDAI);
//		crawlerFinanceService.getInfoFromWdw(results, "微贷网");
	}

	@Test
	public void testSchedule() {
		schedulerService.scanReleaseFinanceData();
	}

}
