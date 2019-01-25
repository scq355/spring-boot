package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 资金流
 *
 * @author LiuYong on 17/6/8 下午10:44.
 */
@Entity
@Table(name = "pb_cash_flow")
public class PbCashFlow {

    @Id
    @SequenceGenerator(name = "cashFlowId", sequenceName = "seq_cash_flow_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashFlowId")
    private int id;
    private String companyCode;     //合营公司编码
    private int itemId;             //
    @Temporal(TemporalType.DATE)
    private Date reportTime;        //
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long realMoney;         //
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long expectMoney;
    private Date createTime;
    private Date updateTime;
    private String type;

    public PbCashFlow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Long getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Long realMoney) {
        this.realMoney = realMoney;
    }

    public Long getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(Long expectMoney) {
        this.expectMoney = expectMoney;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
