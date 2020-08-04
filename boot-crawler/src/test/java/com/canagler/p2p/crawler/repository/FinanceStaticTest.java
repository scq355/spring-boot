package com.canagler.p2p.crawler.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by scq on 2018-07-17 09:41:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FinanceStaticTest {
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 全部删除
	 */
	@Test
	public void testDelete() {
		mongoTemplate.remove(new Query(), "financeCreditor");
	}
}
