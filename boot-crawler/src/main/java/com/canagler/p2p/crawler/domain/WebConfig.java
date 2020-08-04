package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description : 网页分页信息配置
 * Created by scq on 2018-04-08 10:34:22
 */
@Document
public class WebConfig {
	@Id
	private String id;
	// 具体分页链接(全局页面加载：pagingUrl == id, 局部刷新加载：pagingUrl != id)
	private String pagingUrl;
	// 分页方式（分页后的每个页面具体的url:一般为俩部分 前缀+后缀 or 直接跟页数）
	private String pageMethod;
	// 页面内容(页面标题)截取标签字符串
	private String pageContentSelector;
	// 页面详情样式选择器
	private String contentDetailSelector;
	// 分页标签截取字符串
	private String pagingSelector;
	// 分页分隔符
	private String pageSeparator;
	// 网页名称
	private String name;
	// 总页数
	private Integer totalPageNum;

	public WebConfig() {}

	public WebConfig(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public WebConfig(String id,
					 String pagingUrl,
					 String name,
					 String pageMethod,
					 String contentDetailSelector,
					 String pageContentSelector,
					 String pagingSelector,
					 String pageSeparator,
					 Integer totalPageNum) {
		this.id = id;
		this.pagingUrl = pagingUrl;
		this.name = name;
		this.contentDetailSelector = contentDetailSelector;
		this.pageMethod = pageMethod;
		this.pageContentSelector = pageContentSelector;
		this.pagingSelector = pagingSelector;
		this.pageSeparator = pageSeparator;
		this.totalPageNum = totalPageNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPagingUrl() {
		return pagingUrl;
	}

	public void setPagingUrl(String pagingUrl) {
		this.pagingUrl = pagingUrl;
	}

	public String getPageMethod() {
		return pageMethod;
	}

	public void setPageMethod(String pageMethod) {
		this.pageMethod = pageMethod;
	}

	public String getPageSeparator() {
		return pageSeparator;
	}

	public void setPageSeparator(String pageSeparator) {
		this.pageSeparator = pageSeparator;
	}

	public String getContentDetailSelector() {
		return contentDetailSelector;
	}

	public void setContentDetailSelector(String contentDetailSelector) {
		this.contentDetailSelector = contentDetailSelector;
	}

	public String getPageContentSelector() {
		return pageContentSelector;
	}

	public String getPagingSelector() {
		return pagingSelector;
	}

	public void setPageContentSelector(String pageContentSelector) {
		this.pageContentSelector = pageContentSelector;
	}

	public void setPagingSelector(String pagingSelector) {
		this.pagingSelector = pagingSelector;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Integer totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	@Override
	public String toString() {
		return "WebConfig{" +
				"id='" + id + '\'' +
				", pagingUrl='" + pagingUrl + '\'' +
				", pageMethod='" + pageMethod + '\'' +
				", pageContentSelector='" + pageContentSelector + '\'' +
				", contentDetailSelector='" + contentDetailSelector + '\'' +
				", pagingSelector='" + pagingSelector + '\'' +
				", pageSeparator='" + pageSeparator + '\'' +
				", name='" + name + '\'' +
				", totalPageNum=" + totalPageNum +
				'}';
	}
}
