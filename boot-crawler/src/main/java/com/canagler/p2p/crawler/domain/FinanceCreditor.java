package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 债权转让
 * Created by scq on 2018-07-12 10:15:25
 */
@Document
public class FinanceCreditor {
	@Id
	private String id;
	// 项目名称
	private String name;
	// 项目期限
	private Integer deadline;
	// 项目价格
	private BigDecimal price;
	// 调整金额
	private BigDecimal adjustment;
	// 转让金额
	private Double transferAmount;
	// 年化利率
	private BigDecimal annualRate;
	private String webName;

	public FinanceCreditor() {}

	public FinanceCreditor(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public FinanceCreditor(String id, String name, Integer deadline, BigDecimal price, BigDecimal adjustment, Double transferAmount, BigDecimal annualRate, String webName) {
		this.id = id;
		this.name = name;
		this.deadline = deadline;
		this.price = price;
		this.adjustment = adjustment;
		this.transferAmount = transferAmount;
		this.annualRate = annualRate;
		this.webName = webName;
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

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(BigDecimal adjustment) {
		this.adjustment = adjustment;
	}


	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	@Override
	public String toString() {
		return "FinanceCreditor{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", deadline='" + deadline + '\'' +
				", price=" + price +
				", adjustment=" + adjustment +
				", transferAmount=" + transferAmount +
				", annualRate=" + annualRate +
				", webName='" + webName + '\'' +
				'}';
	}
}
