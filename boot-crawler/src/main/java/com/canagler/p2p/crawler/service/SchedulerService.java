package com.canagler.p2p.crawler.service;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.FirstPageContent;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.enums.InfoTypeEnum;
import com.canagler.p2p.crawler.repository.WebInfoRepository;
import com.canagler.p2p.crawler.service.crawler.CrawlerDiscussService;
import com.canagler.p2p.crawler.service.crawler.CrawlerFinanceService;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import com.google.common.base.Strings;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by scq on 2018-04-11 20:07:32
 */
@Service(value = "schedulerService")
public class SchedulerService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

	@Autowired
	private FirstPageContentService firstPageContentService;
	@Autowired
	private CrawlerDiscussService crawlerDiscussService;
	@Autowired
	private WebInfoOptionService webInfoOptionService;
	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private WebInfoRepository webInfoRepository;
	@Autowired
	private CrawlerFinanceService crawlerFinanceService;
	@Autowired
	private FinanceStaticService financeStaticService;

	private static void printOutLogBegin(String methodName) {
        LOGGER.info("========================开始抓取扫描，方法名{}，开始时间：{}========================", methodName,
                DateFormatUtils.format(new Date(), Constants.DATE_TIME_PATTERN));
    }


    private static void printOutLogEnd(String methodName) {
        LOGGER.info("========================开始抓取扫描，方法名{}，结束时间：{}========================", methodName,
                DateFormatUtils.format(new Date(), Constants.DATE_TIME_PATTERN));
    }

	/**
	 * 用户评论任务
	 */
	@Scheduled(cron="0/10 * * * * ?")
	public void scanReleaseDiscussData() {
	    printOutLogBegin("scanReleaseDiscussData");
		try {
			// 需要抓取的数据状态：用户评论&&可爬取状态&&爬取失败
			List<Integer> crawlerAbleStatusList = new ArrayList<>();
			crawlerAbleStatusList.add(Constants.PROCESS_STATUS_LOGIN);
			crawlerAbleStatusList.add(Constants.PROCESS_FAIL_END);
			List<WebInfo> webInfoList = webInfoRepository.findAllByInfoTypeAndProcessStatusIsIn(InfoTypeEnum.INFO_DISCUSS.getType(), crawlerAbleStatusList);
			LOGGER.info("用户评论：待爬取的数据：{}", webInfoList.toString());
			for (WebInfo webInfo : webInfoList) {
				if (webInfo.getRequestNum() > Constants.MAX_REQUEST_NUM) {
					webInfoService.updateProcessStatus(webInfo.getId(), Constants.PROCESS_ERROR_END);
					continue;
				}
				WebInfoOption webInfoOption  = webInfoOptionService.findById(webInfo.getId());
				String pageContent = CrawlerUtil.getPageInfo(webInfo.getUrl(), Constants.REQUEST_GET, webInfoOption.getLoginRequired(), COOKIE_WEIDAI);
				FirstPageContent firstPageContent = firstPageContentService.findById(webInfo.getId());
				if (firstPageContent != null) {
					String localContent = firstPageContent.getPageContent();
					if (Strings.isNullOrEmpty(localContent)) { // 第一次无数据
						crawlerDiscussService.crawlerDiscuss(webInfo.getId(), webInfo.getUrl(), webInfo.getParams(), webInfo.getName());
						firstPageContentService.updateFirstPageContent(webInfo.getId(), pageContent);
					} else if (!localContent.equals(pageContent)) { // 数据有更新
						crawlerDiscussService.crawlerDiscuss(webInfo.getId(), webInfo.getUrl(), webInfo.getParams(), webInfo.getName());
						firstPageContentService.updateFirstPageContent(webInfo.getId(), pageContent);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("用户评论：抓取信息出错：{}", e.getMessage());
		}
		printOutLogEnd("scanReleaseDiscussData");
	}

	/**
	 * 债权转让任务：每天上午十点，晚下午六点各一次
	 */
	@Scheduled(cron = "0 0 10,18 * * ?")
	public void scanReleaseFinanceData() {
        printOutLogBegin("scanReleaseFinanceData");
		List<Integer> crawlerAbleStatusList = new ArrayList<>();
		crawlerAbleStatusList.add(Constants.PROCESS_STATUS_LOGIN);
		crawlerAbleStatusList.add(Constants.PROCESS_FAIL_END);
		crawlerAbleStatusList.add(Constants.PROCESS_SUCCESS_END);
		List<WebInfo> webInfoList = webInfoRepository.findAllByInfoTypeAndProcessStatusIsIn(InfoTypeEnum.INFO_FINANCE.getType(), crawlerAbleStatusList);
		LOGGER.info("债权转让：待爬取的数据：{}", JSON.toJSONString(webInfoList));

		// 全量更新:删除历史数据
		mongoTemplate.remove(new Query(), "financeCreditor");
		for (WebInfo webInfo : webInfoList) {
			crawlerFinanceService.crawlerFinance(webInfo.getId());
		}

		// 数据统计，更新，保存
		financeStaticService.staticDataUpdate();
        printOutLogEnd("scanReleaseFinanceData");
	}
}
