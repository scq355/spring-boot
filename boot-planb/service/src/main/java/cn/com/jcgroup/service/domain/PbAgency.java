package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 机构融资款
 *
 * @author LiuYong on 17/5/28 下午5:02.
 */
@Entity
@Table(name = "pb_agency",uniqueConstraints = {@UniqueConstraint(columnNames = "agencyCode", name = "u_agencyCode")})
public class PbAgency {

    @Id
    @SequenceGenerator(name = "agencyId", sequenceName = "seq_agency_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencyId")
    private int id;
    private String agencyCode;          //机构编码
    private String agencyName;          //机构名称
    private long amount;                //资金量
    private String capitalCost;         //资金成本
    private String timeLimit;           //期限
    private String capitalUse;          //资金用途
    @Column(columnDefinition = "clob")
    private String businessItem;        //商务条款
    private Date createTime;            //创建时间
    private Date updateTime;            //更新时间
    private String isShow;              //是否展示
    private String type;                //所属类型

    public PbAgency() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCapitalCost() {
        return capitalCost;
    }

    public void setCapitalCost(String capitalCost) {
        this.capitalCost = capitalCost;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getCapitalUse() {
        return capitalUse;
    }

    public void setCapitalUse(String capitalUse) {
        this.capitalUse = capitalUse;
    }

    public String getBusinessItem() {
        return businessItem;
    }

    public void setBusinessItem(String businessItem) {
        this.businessItem = businessItem;
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

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
