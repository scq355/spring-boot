package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description ： 需要登录网站的账号凭证
 * Created by scq on 2018-04-10 15:05:31
 */
@Document
public class WebToken {
	@Id
	private String id;
	// 用户名
	private String userName;
	// 密码
	private String password;
	// 凭证
	private String certificate;
	// 网站名称
	private String name;

	public WebToken() {}

	public WebToken(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public WebToken(String name, String userName, String password, String certificate) {
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.certificate = certificate;
	}

	public WebToken(String id, String name, String userName, String password, String certificate) {
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.certificate = certificate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WebToken{" +
				"id='" + id + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", certificate='" + certificate + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
