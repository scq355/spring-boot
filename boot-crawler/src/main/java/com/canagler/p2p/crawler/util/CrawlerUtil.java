package com.canagler.p2p.crawler.util;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.common.NegativeKeyWords;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网页匹配工具类
 * Created by scq on 2018-07-13 10:59:45
 */
public class CrawlerUtil{
	/**
	 * 获取网页内容
	 * @param url：网页地址
	 * @param requestType：请求类型：0：get, 1:post
	 * @param loginRequired：是否需要登录（登录凭证）
	 */
	public static String getPageInfo(String url, Integer requestType, Integer loginRequired, String cookie) throws IOException {
		StringBuilder pageContent = new StringBuilder();
		if (requestType.equals(Constants.REQUEST_GET)) {
			if (loginRequired.equals(Constants.LOGIN_REQUIRED)) {
				pageContent.append(HttpRequestUtil.getPageInfoGet(url, cookie));
			} else {
				pageContent.append(HttpRequestUtil.getPageInfoGet(url));
			}
		} else {
			if (loginRequired.equals(Constants.LOGIN_REQUIRED)) {
				pageContent.append(HttpRequestUtil.getPageInfoPost(url, cookie));
			} else {
				pageContent.append(HttpRequestUtil.getPageInfoPost(url));
			}
		}
		return pageContent.toString();
	}

	/**
	 * 获取需要爬取的页面具体范围中的文字字符串
	 * @param pageContent：页面内容（HTML文本）
	 * @param pageContentSelector：HTML页面具体内容的选择标签
	 */
	public static String getCrawlerPageContent(String pageContent, String pageContentSelector) {
		Document document = Jsoup.parse(pageContent);
		Elements comments = document.select(pageContentSelector);
		StringBuilder stringBuilder = new StringBuilder();
		for (Element e : comments) {
			stringBuilder.append(e.text());
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取需要爬取的页面具体范围中的文字字符串
	 * @param pageContent:页面内容（HTML文本）
	 * @param pageContentSelector:HTML页面具体内容的选择标签
	 */
	public static List<String> getCrawlerContent(String pageContent, String pageContentSelector) {
		Document document = Jsoup.parse(pageContent);
		Elements comments = document.select(pageContentSelector);
		List<String> resultList = new ArrayList<>();
		for (Element e : comments) {
			resultList.add(e.text());
		}
		return resultList;
	}

	/**
	 * 获取论坛类型页面的标题，超链接
	 * @param url：URL
	 * @param pageContent：页面内容
	 * @param pageContentSelector：内容样式选择器
	 */
	public static Map<String, String> getCrawlerTitles(String url, String pageContent, String pageContentSelector) {
		Document document = Jsoup.parse(pageContent);
		Element singerListDiv = document.getElementsByAttributeValue("class", pageContentSelector).first();
		//获取该div中所有a标签
		Elements alist = singerListDiv.getElementsByTag("a");
		//获得标题和地址
		Map<String, String> titleInfoMap = new HashMap<>();
		for (Element link : alist) {
			String getLink = link.attr("href");
			String linkText = link.ownText().trim();
			if (!Constants.HREF_KEY_JAVASCRIPT.equals(getLink)) {
				if (!(Constants.STRING_ZERO.equals(linkText) ||
						Constants.HREF_KEY_DUPLICATE_KEY.equals(linkText))) {
					titleInfoMap.put(linkText, getLink);
				}
			}
		}
		System.out.println(JSON.toJSONString(titleInfoMap));
		return titleInfoMap;
	}


	/**
	 * 根据页面内容确定抓取的页面总数，每个页面具体的地址
	 * @param pageContent：HTML内容
	 * @param pagingSelector：分页标签
	 * @param pageSeparator: 分页分隔符（一般为空格：" "）
	 */
	public static Map<String, Object> getPageNumAndPageUrls(String pageContent, String pagingSelector, String pageSeparator) {
		Map<String, Object> resultMap = new HashMap<>();
		Document document = Jsoup.parse(pageContent);

		Elements pages = document.select(pagingSelector);
		/**
		 * eg:
		 * 1 2 3 4 5 6 7 8 9 10 ... 38 下一页
		 * 首页 上一页 1 2 3 4 5 ... 下一页 末页 共312条数据 1/16
		 */
		String tempStr = pages.first().text();
		Integer pageBound = NumberUtil.getPageBounds(tempStr);
		resultMap.put("pageBound", pageBound);
		return resultMap;
	}


	public static Map<String, Object> getPageNumAndPageUrls(String pageContent, String pagingSelector) {
		Map<String, Object> resultMap = new HashMap<>();
		Document document = Jsoup.parse(pageContent);

		Elements pages = document.select(pagingSelector);

		String tempStr = pages.text();
		Integer pageBound = NumberUtil.getPageBounds(tempStr);
		resultMap.put("pageBound", pageBound);
		return resultMap;
	}


	/**
	 * 根据给定的关键字匹配文本内容
	 * @param context:文本字符串
	 * @param params:关键字
	 */
	public static List<String> matchParams(String context, String params) {
		List<String> negativeWordList = new ArrayList<>();
		for (int i = 1; i <= NegativeKeyWords.values().length ; i++) {
			negativeWordList.add(NegativeKeyWords.getName(i));
		}
		String[] paramsArray = params.split("，");
		String replacedContext = context;
		for (int i = 0; i < paramsArray.length; i++) {
			for (int j = 0; j < negativeWordList.size(); j++) {
				replacedContext = StringUtils.replace(replacedContext, negativeWordList.get(j) + paramsArray[i], "*");
			}
		}
		List<String> matchedParams = new ArrayList<>();
		for (int i = 0; i < paramsArray.length; i++) {
			if (StringUtils.contains(replacedContext, paramsArray[i])) {
				if (!Constants.COMPANY_NAME_QSW.equals(paramsArray[i])) {
					matchedParams.add(paramsArray[i]);
				}
			}
		}
		return matchedParams;
	}

}
