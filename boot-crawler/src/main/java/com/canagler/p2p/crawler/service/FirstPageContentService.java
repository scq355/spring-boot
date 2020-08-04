package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.domain.FirstPageContent;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.repository.FirstPageContentRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by scq on 2018-04-18 09:20:14
 */
@Service(value = "firstPageContentService")
public class FirstPageContentService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstPageContentService.class);

	@Autowired
	private FirstPageContentRepository firstPageContentRepository;

	/**
	 * 根据ID获取信息
	 * @param id：ID
	 */
	public FirstPageContent findById(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return null;
		}
		Optional<FirstPageContent> firstPageContentOptional = firstPageContentRepository.findById(id);
		return firstPageContentOptional.orElse(null);
	}

	/**
	 * 更新信息
	 */
	public void updateFirstPageContent(String id, String pageContent) {
		try {
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("pageContent", pageContent);
			update.set("updateAt", new Date());
			mongoTemplate.updateMulti(query, update, WebInfoOption.class);
		} catch (Exception e) {
			LOGGER.error("首页内容更新失败：{}", e.getMessage());
		}
	}


}
