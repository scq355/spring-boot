package com.canagler.p2p.crawler.discuss;

import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by scq on 2018-04-09 11:51:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application-test.properties")
public class CrawlerUtilsTests {

	@Value("${cookie.weidai}")
	public static String COOKIE_WEIDAI;

	@Test
	public void getPageInfoTest() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://qianshiwang.p2peye.com/comment/list-0-0-1.html", 0, 0, COOKIE_WEIDAI);
		pageContent = pageContent.trim();
		FileOutputStream fileOutputStream = new FileOutputStream("D:\\workspaces\\crawler\\src\\test\\resources\\webInfo.txt");
		fileOutputStream.write(pageContent.getBytes());
		fileOutputStream.close();
		System.out.println(pageContent);
	}

	@Test
	public void getCrawlerPageContentTestI() {
		String pageContent = null;
		try {
			pageContent = CrawlerUtil.getPageInfo("https://qianshiwang.p2peye.com/comment/list-0-0-1.html", 1, 0, COOKIE_WEIDAI);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String subContent = CrawlerUtil.getCrawlerPageContent(pageContent, "#comment");
		System.out.println(subContent);
	}


	@Test
	public void getCrawlerPageContentTestII() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://www.wdzj.com/front/dianpingInfo/896/20/6", 2, 1, COOKIE_WEIDAI);
		String subContent = CrawlerUtil.getCrawlerPageContent(pageContent, ".bd");
		System.out.println(subContent);
	}

	@Test
	public void getPageNumAndPageUrlsTestI() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://qianshiwang.p2peye.com/comment/list-0-0-5.html", 1, 0, COOKIE_WEIDAI);
		Map<String, Object> pageNumMap = CrawlerUtil.getPageNumAndPageUrls(pageContent, "div.c-page", " ");
		System.out.println(pageNumMap.get("pageBound"));
	}

	@Test
	public void getPageNumAndPageUrlsTestII() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://www.wdzj.com/front/dianpingInfo/896/20/6", 2, 1, COOKIE_WEIDAI);
		Map<String, Object> pageNumMap = CrawlerUtil.getPageNumAndPageUrls(pageContent, ".pageList", " ");
		System.out.println(pageNumMap.get("pageBound"));
	}

	@Test
	public void matchParamsTest() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://qianshiwang.p2peye.com/comment/list-0-0-1.html", 1, 0, COOKIE_WEIDAI);
		String subContent = CrawlerUtil.getCrawlerPageContent(pageContent, "#comment");
		System.out.println(subContent);
		List<String> result = CrawlerUtil.matchParams(subContent, "钱市网&不靠谱");
		System.out.println(result);
	}

	@Test
	public void notMatchedTest() throws IOException {
		String pageContent = CrawlerUtil.getPageInfo("https://qianshiwang.p2peye.com/comment/list-0-0-11.html", 1, 0, COOKIE_WEIDAI);
		String subContent = CrawlerUtil.getCrawlerPageContent(pageContent, "#comment");
		System.out.println(subContent);
		List<String> result = CrawlerUtil.matchParams(subContent, "钱市网&不靠谱");
		System.out.println(result);
	}

//	@Test
//	public void crawlerTest() {
//		CrawlerUtil.crawler("https://qianshiwang.p2peye.com/comment/",  "钱市网&不靠谱", "网贷天眼");
//	}
}
