package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 私募基金数据
 *
 * @author LiuYong on 17/5/28 下午5:22.
 */
@Entity
@Table(name = "pb_private_fund", uniqueConstraints = {@UniqueConstraint(columnNames = "fundCode", name = "u_fundCode")})
public class PbPrivateFund {

    @Id
    @SequenceGenerator(name = "privateFundId", sequenceName = "seq_private_fund_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privateFundId")
    private int id;                 //主键ID
    private String fundCode;        //基金编码
    private String fundName;        //基金名称
    private String fundCompany;     //基金公司
    @Column(columnDefinition = "number(20) default 0")
    private Long raiseAmount;       //募集量
    private String fundDuration;    //基金期限
    private String riskLevel;       //风险等级
    private String apr;             //年华目标
    private String custodyFee;      //托管费
    private String investFee;       //投顾费
    private String manageFee;       //管理费
    private String consignFee;      //代销费
    @Column(columnDefinition = "varchar(1024)")
    private String financeSide;     //融资方/合作主题
    private String guarantor;       //担保方
    private String fundManager;     //基金管理人
    @Column(columnDefinition = "varchar(1024)")
    private String capitalUse;      //资金用途
    @Column(columnDefinition = "clob")
    private String riskControl;     //风控措施
    @Column(columnDefinition = "clob")
    private String periodInfo;      //成立金额及期限
    private String isShow;          //是否展示
    @Column(columnDefinition = "number(20) default 0")
    private Long realAmount;        //实际募集量
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间
    @Column(columnDefinition = "number(20) default 0")
    private Long currentTotalMoney; //

    public PbPrivateFund() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundCompany() {
        return fundCompany;
    }

    public void setFundCompany(String fundCompany) {
        this.fundCompany = fundCompany;
    }

    public Long getRaiseAmount() {
        return raiseAmount;
    }

    public void setRaiseAmount(Long raiseAmount) {
        this.raiseAmount = raiseAmount;
    }

    public String getFundDuration() {
        return fundDuration;
    }

    public void setFundDuration(String fundDuration) {
        this.fundDuration = fundDuration;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getCustodyFee() {
        return custodyFee;
    }

    public void setCustodyFee(String custodyFee) {
        this.custodyFee = custodyFee;
    }

    public String getInvestFee() {
        return investFee;
    }

    public void setInvestFee(String investFee) {
        this.investFee = investFee;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getConsignFee() {
        return consignFee;
    }

    public void setConsignFee(String consignFee) {
        this.consignFee = consignFee;
    }

    public String getFinanceSide() {
        return financeSide;
    }

    public void setFinanceSide(String financeSide) {
        this.financeSide = financeSide;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public String getFundManager() {
        return fundManager;
    }

    public void setFundManager(String fundManager) {
        this.fundManager = fundManager;
    }

    public String getCapitalUse() {
        return capitalUse;
    }

    public void setCapitalUse(String capitalUse) {
        this.capitalUse = capitalUse;
    }

    public String getRiskControl() {
        return riskControl;
    }

    public void setRiskControl(String riskControl) {
        this.riskControl = riskControl;
    }

    public String getPeriodInfo() {
        return periodInfo;
    }

    public void setPeriodInfo(String periodInfo) {
        this.periodInfo = periodInfo;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Long realAmount) {
        this.realAmount = realAmount;
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

    public Long getCurrentTotalMoney() {
        return currentTotalMoney;
    }

    public void setCurrentTotalMoney(Long currentTotalMoney) {
        this.currentTotalMoney = currentTotalMoney;
    }
}
