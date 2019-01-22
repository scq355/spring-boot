package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.domain.*;
import com.canagler.p2p.crawler.repository.*;
import com.canagler.p2p.crawler.util.NumberUtil;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by scq on 2018-04-08 11:26:01
 */
@Service(value = "webInfoService")
public class WebInfoService extends BaseService {
	private final Logger LOGGER = LoggerFactory.getLogger(WebInfoService.class);

	@Autowired
	private WebInfoRepository webInfoRepository;
	@Autowired
	private WebInfoOptionRepository webInfoOptionRepository;
	@Autowired
	private WebConfigRepository webConfigRepository;
	@Autowired
	private WebTokenRepository webTokenRepository;
	@Autowired
	private FirstPageContentRepository firstPageContentRepository;

	/**
	 * 添加爬取网页信息
	 * @param name：网站名
	 * @param url：URL
	 * @param params：带匹配关键字
	 */
	@Transactional
	public void save(String name, String url, String params, Integer infoType) {
		try {
			String generateId = NumberUtil.getSecondTimestamp(new Date()) + "#" + url;
			WebInfo webInfo;
			switch (infoType) {
				case 1:
					webInfo = new WebInfo(generateId, url, name, params, new Date(), new Date(), null, infoType);
					break;
				case 2:
					webInfo = new WebInfo(generateId, url, name, infoType, new Date(), new Date());
					break;
				default:
					webInfo = new WebInfo(generateId, url, name, params, new Date(), new Date(), null, infoType);
					break;
			}
			webInfoRepository.save(webInfo);
			webInfoOptionRepository.save(new WebInfoOption(generateId, name));
			webConfigRepository.save(new WebConfig(generateId, name));
			webTokenRepository.save(new WebToken(generateId, name));
			firstPageContentRepository.save(new FirstPageContent(generateId, url, name));
		} catch (Exception e) {
			LOGGER.error("添加爬取网页相关信息出错：{}", e.getMessage());
		}
	}

	/**
	 * 保存对象
	 */
	public void save(WebInfo webInfo) {
		webInfoRepository.save(webInfo);
	}

	/**
	 * 获取待爬取网页列表
	 */
	public List<WebInfo> findAllByIsShow(Integer isShow) {
		return webInfoRepository.findAllByIsShow(isShow);
	}

	/**
	 * 更新显示状态
	 */
	public UpdateResult updateShowStatus(Integer isShow, String webInfoId) {
		if (isShow != null) {
			Query query = new Query(Criteria.where("_id").is(webInfoId));
			Update update =  new Update();
			update.set("isShow", isShow);
			return mongoTemplate.updateMulti(query, update, WebInfo.class);
		}
		return null;
	}

	/**
	 * 删除网页信息
	 */
	@Transactional
	public void deleteWebInfo(String webInfoId) {
		try {
			webInfoRepository.deleteById(webInfoId);
			webInfoOptionRepository.deleteById(webInfoId);
			webConfigRepository.deleteById(webInfoId);
			webTokenRepository.deleteById(webInfoId);
		} catch (Exception e) {
			LOGGER.error("网页信息删除失败：{}", e.getMessage());
		}
	}


	/**
	 * 根据ID获取网页信息
	 * @param id：id
	 */
	public WebInfo findById(String id) {
		Optional<WebInfo> webInfo = webInfoRepository.findById(id);
		return webInfo.orElse(null);
	}

	/**
	 * 更新数据
	 * @param id：ID
	 * @param name：网址名
	 * @param params：关键字
	 */
	@Transactional
	public void update(String id, String name, String params) {
		try {
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("name", name);
			update.set("params", params);
			mongoTemplate.updateMulti(query, update, WebInfo.class);
			Update updateRelease = new Update();
			updateRelease.set("name", name);
			mongoTemplate.updateMulti(query, updateRelease, WebInfoOption.class);
			mongoTemplate.updateMulti(query, updateRelease, WebConfig.class);
			mongoTemplate.updateMulti(query, updateRelease, WebToken.class);
		} catch (Exception e) {
			LOGGER.error("网页信息更新失败：{}", e.getMessage());
		}
	}

	/**
	 * 进度状态
	 * @param id：ID
	 * @param processStatus：锁定状态
	 */
	public void updateProcessStatus(String id, Integer processStatus) {
		try {
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("processStatus", processStatus);
			mongoTemplate.updateMulti(query, update, WebInfo.class);
		} catch (Exception e) {
			LOGGER.error("修改状态出错：{}", e.getMessage());
		}
	}

	/**
	 * 请求次数
	 * @param id：ID
	 * @param requestNum：请求次数
	 */
	public void updateRequestNum(String id, Integer requestNum) {
		try {
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("requestNum", requestNum);
			mongoTemplate.updateMulti(query, update, WebInfo.class);
		} catch (Exception e) {
			LOGGER.error("修改请求次数出错：{}", e.getMessage());
		}
	}
}
