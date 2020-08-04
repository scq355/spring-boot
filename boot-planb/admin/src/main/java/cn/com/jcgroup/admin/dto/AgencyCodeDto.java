package cn.com.jcgroup.admin.dto;

/**
 * Description: 机构
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午8:50
 */
public class AgencyCodeDto {

    private String agency_code;
    private String company_code;
    private String agency_name;
    private String amount;
    private String business_item;
    private String capital_cost;
    private String capital_use;
    private String create_time;
    private String time_limit;
    private String update_time;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBusiness_item() {
        return business_item;
    }

    public void setBusiness_item(String business_item) {
        this.business_item = business_item;
    }

    public String getCapital_cost() {
        return capital_cost;
    }

    public void setCapital_cost(String capital_cost) {
        this.capital_cost = capital_cost;
    }

    public String getCapital_use() {
        return capital_use;
    }

    public void setCapital_use(String capital_use) {
        this.capital_use = capital_use;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(String agency_code) {
        this.agency_code = agency_code;
    }
}
