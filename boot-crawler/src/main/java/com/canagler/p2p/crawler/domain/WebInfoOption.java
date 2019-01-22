package com.canagler.p2p.crawler.domain;

import com.canagler.p2p.crawler.common.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 待抓取网页分页方式，登录要求相关配置
 * Created by scq on 2018-04-11 16:54:27
 */
@Document
public class WebInfoOption {

	@Id
	private String id;
	// 请求类型（GET/POST）
	private Integer requestType;
	// 内容位置类型（内容信息跟标题信息是否在同一个页面：0：同一个页面，1：不同页面）
	private Integer contentLocationType;
	// 分页类型（局部分页/全局分页）
	private Integer pagingType;
	// 登录要求（是否允许登录）
	private Integer loginRequired;
	// 网站名称
	private String name;

	public WebInfoOption() {}

	public WebInfoOption(String id, String name) {
		this.id = id;
		this.name = name;
		this.contentLocationType = Constants.CONTENT_LOCATION_SAME;
	}

	public WebInfoOption(String id, Integer requestType, Integer pagingType, Integer loginRequired, String name) {
		this.id = id;
		this.requestType = requestType;
		this.pagingType = pagingType;
		this.loginRequired = loginRequired;
		this.name = name;
		this.contentLocationType = Constants.CONTENT_LOCATION_SAME;
	}

	public WebInfoOption(String id, Integer requestType, Integer contentLocationType, Integer pagingType, Integer loginRequired, String name) {
		this.id = id;
		this.requestType = requestType;
		this.pagingType = pagingType;
		this.loginRequired = loginRequired;
		this.name = name;
		this.contentLocationType = contentLocationType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public void setContentLocationType(Integer contentLocationType) {
		this.contentLocationType = contentLocationType;
	}

	public Integer getContentLocationType() {
		return contentLocationType;
	}

	public Integer getPagingType() {
		return pagingType;
	}

	public void setPagingType(Integer pagingType) {
		this.pagingType = pagingType;
	}

	public Integer getLoginRequired() {
		return loginRequired;
	}

	public void setLoginRequired(Integer loginRequired) {
		this.loginRequired = loginRequired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WebInfoOption{" +
				"id='" + id + '\'' +
				", requestType=" + requestType +
				", contentLocationType=" + contentLocationType +
				", pagingType=" + pagingType +
				", loginRequired=" + loginRequired +
				", name='" + name + '\'' +
				'}';
	}
}
