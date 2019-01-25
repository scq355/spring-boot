package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 融资款汇总表
 *
 * @author LiuYong on 17/5/28 下午3:40.
 */
@Entity
@Table(name = "pb_finance_summary")
public class PbFinanceSummary {

    @Id
    @SequenceGenerator(name = "financeSummaryId", sequenceName = "seq_finance_summary_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financeSummaryId")
    private Integer id;
    private String companyCode;
    private String type;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long privateFund;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long agency;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long financialAgency;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long remain;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long financialCostAvg;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long amountRaised;
    private Date createTime;
    private Date updateTime;

    public PbFinanceSummary() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrivateFund() {
        return privateFund;
    }

    public void setPrivateFund(Long privateFund) {
        this.privateFund = privateFund;
    }

    public Long getAgency() {
        return agency;
    }

    public void setAgency(Long agency) {
        this.agency = agency;
    }

    public Long getFinancialAgency() {
        return financialAgency;
    }

    public void setFinancialAgency(Long financialAgency) {
        this.financialAgency = financialAgency;
    }

    public Long getRemain() {
        return remain;
    }

    public void setRemain(Long remain) {
        this.remain = remain;
    }

    public Long getFinancialCostAvg() {
        return financialCostAvg;
    }

    public void setFinancialCostAvg(Long financialCostAvg) {
        this.financialCostAvg = financialCostAvg;
    }

    public Long getAmountRaised() {
        return amountRaised;
    }

    public void setAmountRaised(Long amountRaised) {
        this.amountRaised = amountRaised;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
