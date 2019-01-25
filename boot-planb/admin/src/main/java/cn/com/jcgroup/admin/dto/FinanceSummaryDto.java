package cn.com.jcgroup.admin.dto;

/**
 * @author LiuYong on 17/6/26 下午12:36.
 */
public class FinanceSummaryDto {

    private int id;
    private String companyCode;
    private String type;
    private String privateFund;
    private String agency;
    private String financialAgency;
    private String remain;
    private String financialCostAvg;
    private String amountRaised;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrivateFund() {
        return privateFund;
    }

    public void setPrivateFund(String privateFund) {
        this.privateFund = privateFund;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getFinancialAgency() {
        return financialAgency;
    }

    public void setFinancialAgency(String financialAgency) {
        this.financialAgency = financialAgency;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getFinancialCostAvg() {
        return financialCostAvg;
    }

    public void setFinancialCostAvg(String financialCostAvg) {
        this.financialCostAvg = financialCostAvg;
    }

    public String getAmountRaised() {
        return amountRaised;
    }

    public void setAmountRaised(String amountRaised) {
        this.amountRaised = amountRaised;
    }
    
}
