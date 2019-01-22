package com.canagler.p2p.crawler.finance;

import com.canagler.p2p.crawler.service.FinanceStaticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by scq on 2018-07-16 14:58:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FinanceStaticTest {
	@Autowired
	private FinanceStaticService financeStaticService;

	@Test
	public void testStaticUpdate() {
		financeStaticService.staticDataUpdate();
	}
}
