package com.canagler.p2p.crawler.util;

import com.canagler.p2p.crawler.common.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by scq on 2018-04-10 14:43:05
 */
public class HttpRequestUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);

	static HttpClient init() {
		return HttpClients.createDefault();
	}

	public static String getPageInfoGet(String url) throws IOException {
		HttpClient httpClient = init();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", Constants.USER_AGENT);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity=response.getEntity();
		//获取网页内容，指定编码
		return EntityUtils.toString(entity,"UTF-8");
	}

	public static String getPageInfoGet(String url, String token) throws IOException {
		HttpClient httpClient = init();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", Constants.USER_AGENT);
		httpGet.setHeader("Cookie", token);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity=response.getEntity();
		//获取网页内容，指定编码
		return EntityUtils.toString(entity,"UTF-8");
	}

	public static String getPageInfoPost(String url) throws IOException {
		HttpClient httpClient = init();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("User-Agent", Constants.USER_AGENT);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		//获取网页内容，指定编码
		return EntityUtils.toString(entity,"UTF-8");
	}

	public static String getPageInfoPost(String url, String token) throws IOException {
		HttpClient client= HttpClients.createDefault();
		//创建httpget实例
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("User-Agent", Constants.USER_AGENT);
		httpPost.setHeader("Cookie", token);
		//执行 get请求
		HttpResponse response=client.execute(httpPost);
		//返回获取实体
		HttpEntity entity=response.getEntity();
		//获取网页内容，指定编码
		return EntityUtils.toString(entity,"UTF-8");
	}
}
