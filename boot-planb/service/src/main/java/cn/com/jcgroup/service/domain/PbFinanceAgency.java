package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 金融机构
 *
 * @author LiuYong on 17/5/28 下午5:12.
 */
@Entity
@Table(name = "pb_finance_agency", uniqueConstraints = {@UniqueConstraint(columnNames = "financeAgencyCode", name = "u_financeAgencyCode")})
public class PbFinanceAgency {

    @Id
    @SequenceGenerator(name = "financeAgencyId", sequenceName = "seq_finance_agency_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financeAgencyId")
    private int id;
    private String financeAgencyCode;       //金融机构编码
    private String financeAgencyName;       //金融机构名称
    private String capitalCost;             //资金成本
    @Temporal(TemporalType.DATE)
    private Date creditDate;                //授信日期
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long creditAmount;              //授信额度
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long usedAmount;                //已用额度
    private String isShow;                  //是否显示
    private Date createTime;                //创建时间
    private Date updateTime;                //更新时间

    public PbFinanceAgency() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinanceAgencyCode() {
        return financeAgencyCode;
    }

    public void setFinanceAgencyCode(String financeAgencyCode) {
        this.financeAgencyCode = financeAgencyCode;
    }

    public String getFinanceAgencyName() {
        return financeAgencyName;
    }

    public void setFinanceAgencyName(String financeAgencyName) {
        this.financeAgencyName = financeAgencyName;
    }

    public String getCapitalCost() {
        return capitalCost;
    }

    public void setCapitalCost(String capitalCost) {
        this.capitalCost = capitalCost;
    }

    public Date getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(Date creditDate) {
        this.creditDate = creditDate;
    }

    public Long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
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
