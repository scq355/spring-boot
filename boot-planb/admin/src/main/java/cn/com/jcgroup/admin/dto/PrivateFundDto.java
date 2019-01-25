package cn.com.jcgroup.admin.dto;

/**
 * Description: 私募
 * User: sunchangqing
 * Date: 2017-06-26
 * Time: 下午8:22
 */
public class PrivateFundDto {

    private String fundCode;

    private String fundName;        //基金名称
    private String fundCompany;     //基金公司
    private Long raiseAmount;       //募集量
    private String fundDuration;    //基金期限
    private String riskLevel;       //风险等级
    private String apr;             //年华目标
    private String custodyFee;      //托管费
    private String investFee;       //投顾费
    private String manageFee;       //管理费
    private String consignFee;      //代销费
    private String financeSide;     //融资方/合作主题
    private String guarantor;       //担保方
    private String fundManager;     //基金管理人
    private String capitalUse;      //资金用途
    private String riskControl;     //风控措施
    private String periodInfo;      //成立金额及期限
    private Long realAmount;        //实际募集量
    private String createTime;        //创建时间
    private String updateTime;        //更新时间
    private Long currentTotalMoney; //实际募集量
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
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

    public Long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Long realAmount) {
        this.realAmount = realAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCurrentTotalMoney() {
        return currentTotalMoney;
    }

    public void setCurrentTotalMoney(Long currentTotalMoney) {
        this.currentTotalMoney = currentTotalMoney;
    }
}
