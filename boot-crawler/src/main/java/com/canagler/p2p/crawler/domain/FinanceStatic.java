package com.canagler.p2p.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

@Document
public class FinanceStatic {

    @Id
    private String id;
    @Field(value = "webName")
    private String webName;
    private Integer deadline;
    @Field(value = "totalTransfer")
    private BigDecimal totalTransfer;
	private BigDecimal totalTransferPrevious;
	private Date previous;
    private Date now;

    public FinanceStatic() {}

    public FinanceStatic(String webName, Integer deadline, BigDecimal totalTransfer, Date now) {
    	this.webName = webName;
    	this.deadline = deadline;
		this.totalTransfer = totalTransfer;
		this.now = now;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public BigDecimal getTotalTransfer() {
        return totalTransfer;
    }

    public void setTotalTransfer(BigDecimal totalTransfer) {
        this.totalTransfer = totalTransfer;
    }

	public BigDecimal getTotalTransferPrevious() {
		return totalTransferPrevious;
	}

	public void setTotalTransferPrevious(BigDecimal totalTransferPrevious) {
		this.totalTransferPrevious = totalTransferPrevious;
	}

	public Date getPrevious() {
		return previous;
	}

	public void setPrevious(Date previous) {
		this.previous = previous;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	@Override
	public String toString() {
		return "FinanceStatic{" +
				"id='" + id + '\'' +
				", webName='" + webName + '\'' +
				", deadline=" + deadline +
				", totalTransfer=" + totalTransfer +
				", totalTransferPrevious=" + totalTransferPrevious +
				", previous=" + previous +
				", now=" + now +
				'}';
	}
}
