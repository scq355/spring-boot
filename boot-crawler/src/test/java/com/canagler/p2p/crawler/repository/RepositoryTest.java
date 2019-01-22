package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.enums.InfoTypeEnum;
import com.canagler.p2p.crawler.repository.WebInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scq on 2018-04-14 12:55:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
	@Autowired
	private WebInfoRepository webInfoRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void testRepositoryIn() {
		List<Integer> processStatusList = new ArrayList<>();
		processStatusList.add(1);
		processStatusList.add(4);

		List<WebInfo> webInfoList = webInfoRepository.findAllByInfoTypeAndProcessStatusIsIn(InfoTypeEnum.INFO_DISCUSS.getType(), processStatusList);

		for (WebInfo e : webInfoList) {
			System.out.println(e.getName() + " " + e.getProcessStatus());
		}
	}

	@Test
	public void testRepository() {
		List<WebInfo> webInfoList = mongoTemplate.find(new Query(Criteria.where("processStatus").in(1, 2)), WebInfo.class);

		for (WebInfo e : webInfoList) {
			System.out.println(e.toString());
		}
	}
}
