package com.canagler.p2p.crawler.discuss;

import com.canagler.p2p.crawler.common.NegativeKeyWords;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(NegativeKeyWords.values().length);
	}

}
