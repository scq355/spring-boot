package com.canagler.p2p.crawler.discuss;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * Created by scq on 2018-04-19 10:36:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application-test.properties")
public class GetTitleInfoTest {

	@Value("${cookie.weidai}")
	public static String COOKIE_WEIDAI;

	@Test
	public void getTitlePageInfo() {
		try {
			String pageContent = CrawlerUtil.getPageInfo("http://www.wangdaijiamen.com/forum-53-1.html", 0, 0, COOKIE_WEIDAI);
			Map<String, String> titleInfo = CrawlerUtil.getCrawlerTitles("http://www.wangdaijiamen.com/", pageContent, "list-new-list clearfix");
			System.out.println(JSON.toJSONString(titleInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getPageNumber() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("http://www.wangdaijiamen.com/forum-53-1.html", Constants.REQUEST_GET, 0, COOKIE_WEIDAI);
		Map<String, Object> pageInfoMap = CrawlerUtil.getPageNumAndPageUrls(pageContent, "div.list-pages", " ");
		System.out.println(JSON.toJSONString(pageInfoMap));
	}

}
