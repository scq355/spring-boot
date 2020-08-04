package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Description : 网页详细地址抓取结果
 * Created by scq on 2018-04-08 10:34:47
 */
@Document
public class CrawlerResult {
	@Id
	private String id;
	// 网页名称
	private String name;
	// 网页概要ID
	private String webInfoId;
	// 所在页数
	private Integer pageNum;
	// 特征关键字匹配结果(0：未匹配到无需处理，1：匹配到了待处理，2：匹配到了处理完成)
	private Integer status;
	// 更改日期
	private Date updateAt;
	// 匹配到的关键字
	private String matchedParams;

	public CrawlerResult() {}

	public CrawlerResult(String id, String name, String webInfoId, Integer pageNum, Integer status, Date updateAt) {
		this.id = id;
		this.name = name;
		this.webInfoId = webInfoId;
		this.pageNum = pageNum;
		this.status = status;
		this.updateAt = updateAt;
	}

	public CrawlerResult(String id, String name, String webInfoId, Integer pageNum, Date updateAt, String matchedParams) {
		this.id = id;
		this.name = name;
		this.webInfoId = webInfoId;
		this.pageNum = pageNum;
		this.updateAt = updateAt;
		this.matchedParams = matchedParams;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebInfoId() {
		return webInfoId;
	}

	public void setWebInfoId(String webInfoId) {
		this.webInfoId = webInfoId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getMatchedParams() {
		return matchedParams;
	}

	public void setMatchedParams(String matchedParams) {
		this.matchedParams = matchedParams;
	}

	@Override
	public String toString() {
		return "CrawlerResult{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", webInfoId='" + webInfoId + '\'' +
				", pageNum=" + pageNum +
				", status=" + status +
				", updateAt=" + updateAt +
				", matchedParams='" + matchedParams + '\'' +
				'}';
	}
}
