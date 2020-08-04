package com.canagler.p2p.crawler.discuss;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by scq on 2018-04-17 09:04:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ContextMatchTest {

	@Value("${cookie.weidai}")
	public static String COOKIE_WEIDAI;

	@Test
	public void testMatch() {
		String context = "你就是个拖。利息准时到账，提现超快，骗谁啊？我第一笔投资16点多了还没回款，投资时的返现，提现24小时后才到账。你就是个水军。现在P2P备案政策不明朗，各位且投且珍惜吧！但愿在钱市网能安全下车";
		String params = "钱市网，水军，拖";
		List<String> result = CrawlerUtil.matchParams(context, params);
		System.out.println(JSON.toJSONString(result));
	}


	@Test
	public void testMatchII() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://bbs.wacai.com/group/16842/1/8", 0, 0, COOKIE_WEIDAI);
		String selectedContent = CrawlerUtil.getCrawlerPageContent(pageContent, "ul.group-detail-list");
		Map<String, String> titleInfo = CrawlerUtil.getCrawlerTitles("https://bbs.wacai.com/group/16842/1/8", pageContent, "group-detail-list");
		System.out.println(JSON.toJSONString(titleInfo));

	}
}
