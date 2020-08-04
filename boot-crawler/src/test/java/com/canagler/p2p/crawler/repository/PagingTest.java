package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.util.CrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * Created by scq on 2018-04-16 09:38:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application-test.properties")
public class PagingTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PagingTest.class);

	@Value("${cookie.weidai}")
	public static String COOKIE_WEIDAI;

	@Test
	public void testGetPageBounds() {
		try {
			String content = CrawlerUtil.getPageInfo("https://www.wdzj.com/dangan/qsw/dianping/", 1, 1, COOKIE_WEIDAI);
			String pageContent = CrawlerUtil.getCrawlerPageContent(content, ".bd");
			Map<String, Object> pageMap = CrawlerUtil.getPageNumAndPageUrls(content, ".pageList", " ");
			System.out.println(pageContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPageBoundsII() {
		try {
			String content = CrawlerUtil.getPageInfo("http://www.wdzg.com/forums/section/27elckwz4/page6.html", 0, 0, COOKIE_WEIDAI);
			String pageContent = CrawlerUtil.getCrawlerPageContent(content, ".sl-body");
			Map<String, Object> pageMap = CrawlerUtil.getPageNumAndPageUrls(content, ".pager-bar", " ");
			System.out.println(pageContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	static String url = "http://www.wdzg.com/forums/section/27elckwz4/page6.html";

	@Test
	public void test() {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
			Element singerListDiv = doc.getElementsByAttributeValue("class", "sl-body").first();
			//获取该div中所有a标签
			Elements alist = singerListDiv.getElementsByTag("a");
			int count = 0;
			//获得标题和地址
			for (Element link : alist) {
				String linkHref = link.attr("href");
				String linkText = link.ownText().trim();
				if ("javascript:;".equals(linkHref) || "".equals(linkText)) {
					continue;
				} else {
					System.out.println("标题：" + linkText + " 地址:" + linkHref);
					count++;
				}
			}
			System.out.println(count);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
