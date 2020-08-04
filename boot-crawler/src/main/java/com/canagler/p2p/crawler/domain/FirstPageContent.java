package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 网站"首页"信息
 * Created by scq on 2018-04-18 09:09:09
 */
@Document
public class FirstPageContent {
	@Id
	private String id;
	private String url;
	private String name;
	private String pageContent;
	private Date updateAt;

	public FirstPageContent() {}

	public FirstPageContent(String id, String url, String name) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.updateAt = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "WebInfoFirstPageContent{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				", name='" + name + '\'' +
				", pageContent='" + pageContent + '\'' +
				", updateAt=" + updateAt +
				'}';
	}
}
