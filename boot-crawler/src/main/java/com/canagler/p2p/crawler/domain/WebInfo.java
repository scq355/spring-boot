package com.canagler.p2p.crawler.domain;

import com.canagler.p2p.crawler.common.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Description ： 待检测网址信息总览
 * Created by scq on 2018-04-08 10:34:04
 */
@Document
public class WebInfo {
	@Id
	private String id;
	// URL
	private String url;
	// 网站名称
	private String name;
	// 待匹配关键字
	private String params;
	// 是否展示
	private Integer isShow;
	// 创建时间
	private Date createAt;
	// 更新时间
	private Date updateAt;
	// 网页配置信息（保留）
	private WebConfig webConfig;
	/**
	 * 0：初始可修改，
	 * 1：修改冻结，
	 * 2：分页配置完成，
	 * 3：登录配置完成，
	 * 4：可爬取状态（爬取等待），
	 * 5：爬取中...，
	 * 6：爬取成功-结束，
	 * 7：爬取失败-结束
	 * 8：爬取失败-不会继续重新爬取
	 */
	private Integer processStatus;
	/**
	 * 请求次数
	 */
	private Integer requestNum;
	// 消息类型
	private Integer infoType;

	public WebInfo() {}

	public WebInfo(String name) {
		this.name = name;
	}

	public WebInfo(String id, String name, String url, Integer infoType, Date createAt, Date updateAt) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.isShow = 1;
		this.processStatus = Constants.PROCESS_STATUS_NEW;
		this.requestNum = 0;
		this.infoType = infoType;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public WebInfo(WebConfig webConfig, Date updateAt) {
		this.webConfig = webConfig;
		this.updateAt = updateAt;
	}

	public WebInfo(String id, Integer isShow, Date updateAt) {
		this.id = id;
		this.isShow = isShow;
		this.updateAt = updateAt;
	}

	public WebInfo(String params, Date updateAt) {
		this.params = params;
		this.updateAt = updateAt;
	}

	public WebInfo(String id, String url, String name, String params, Date createAt, Date updateAt, WebConfig webConfig, Integer infoType) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.params = params;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.webConfig = webConfig;
		this.isShow = 1;
		this.processStatus = Constants.PROCESS_STATUS_NEW;
		this.requestNum = 0;
		this.infoType = infoType;
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

	public void setWebConfig(WebConfig webConfig) {
		this.webConfig = webConfig;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public Integer getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	@Override
	public String toString() {
		return "WebInfo{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				", name='" + name + '\'' +
				", params='" + params + '\'' +
				", isShow=" + isShow +
				", createAt=" + createAt +
				", updateAt=" + updateAt +
				", webConfig=" + webConfig +
				", processStatus=" + processStatus +
				", requestNum=" + requestNum +
				", infoType=" + infoType +
				'}';
	}
}
