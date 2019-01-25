package cn.com.jcgroup.admin.dto;

/**
 * Description: 金融机构
 * User: sunchangqing
 * Date: 2017-06-23
 * Time: 上午3:49
 */
public class FinanceAgencyCodeDto {

    private String finance_agency_code;
    private String company_code;
    private String capital_cost;
    private String credit_amount;
    private String credit_date;
    private String finance_agency_name;
    private String residue_amount;
    private String used_amount;
    private String remaining_amount;

    public String getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(String remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getFinance_agency_code() {
        return finance_agency_code;
    }

    public void setFinance_agency_code(String finance_agency_code) {
        this.finance_agency_code = finance_agency_code;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCapital_cost() {
        return capital_cost;
    }

    public void setCapital_cost(String capital_cost) {
        this.capital_cost = capital_cost;
    }

    public String getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(String credit_amount) {
        this.credit_amount = credit_amount;
    }

    public String getCredit_date() {
        return credit_date;
    }

    public void setCredit_date(String credit_date) {
        this.credit_date = credit_date;
    }

    public String getFinance_agency_name() {
        return finance_agency_name;
    }

    public void setFinance_agency_name(String finance_agency_name) {
        this.finance_agency_name = finance_agency_name;
    }

    public String getResidue_amount() {
        return residue_amount;
    }

    public void setResidue_amount(String residue_amount) {
        this.residue_amount = residue_amount;
    }

    public String getUsed_amount() {
        return used_amount;
    }

    public void setUsed_amount(String used_amount) {
        this.used_amount = used_amount;
    }
}
