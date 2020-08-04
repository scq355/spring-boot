package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.repository.WebInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

/**
 * Created by scq on 2018-04-18 16:09:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebInfoRepositoryTest {

	@Autowired
	private WebInfoRepository webInfoRepository;

	@Test
	public void testQuery() {
		String name=  null;

	}

}
