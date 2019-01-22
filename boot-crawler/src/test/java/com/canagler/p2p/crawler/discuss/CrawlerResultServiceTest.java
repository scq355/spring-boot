package com.canagler.p2p.crawler.discuss;

import com.canagler.p2p.crawler.domain.CrawlerResult;
import com.canagler.p2p.crawler.repository.CrawlerResultRepository;
import com.canagler.p2p.crawler.service.CrawlerResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by scq on 2018-04-09 16:36:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerResultServiceTest {
	@Autowired
	private CrawlerResultService crawlerResultService;
	@Autowired
	private CrawlerResultRepository crawlerResultRepository;

	@Test
	public void testSave() {
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.ru", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.cn", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.cc", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.gov", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.def", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
		crawlerResultService.save(new CrawlerResult("https://www.qianshiwang.kr", "钱市网", "https://www.qianshiwang.com", 12, 1, new Date()));
	}

	@Test
	public void testPaging() {
		Pageable pageable = new PageRequest(0,3, Sort.Direction.DESC,"updateAt");
		Page<CrawlerResult> crawlerResultPageable = crawlerResultRepository.findAll(pageable);
		System.out.println(crawlerResultPageable.getTotalElements() + "" + crawlerResultPageable.getTotalPages() + crawlerResultPageable.getContent() + "");
	}
}
