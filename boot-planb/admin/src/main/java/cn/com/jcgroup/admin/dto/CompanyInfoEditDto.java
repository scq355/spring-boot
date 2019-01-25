package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 合营公司编辑
 * User: sunchangqing
 * Date: 2017-06-18
 * Time: 下午4:26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfoEditDto {

    private String office_address;
    private String office_rent;
    private String office_size;
    private String project_manager;
    private String legal_person;
    private String register_capital;
    private String stock_holder;
    private String director;
    private String senior_manager;
    private String supervisors;
    private String equity_transfer_agree;
    private String rule_promise;
    private String profit_distr_plan;
    private String company_code;

    public String getOffice_address() {
        return office_address;
    }

    public void setOffice_address(String office_address) {
        this.office_address = office_address;
    }

    public String getOffice_rent() {
        return office_rent;
    }

    public void setOffice_rent(String office_rent) {
        this.office_rent = office_rent;
    }

    public String getOffice_size() {
        return office_size;
    }

    public void setOffice_size(String office_size) {
        this.office_size = office_size;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getRegister_capital() {
        return register_capital;
    }

    public void setRegister_capital(String register_capital) {
        this.register_capital = register_capital;
    }

    public String getStock_holder() {
        return stock_holder;
    }

    public void setStock_holder(String stock_holder) {
        this.stock_holder = stock_holder;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSenior_manager() {
        return senior_manager;
    }

    public void setSenior_manager(String senior_manager) {
        this.senior_manager = senior_manager;
    }

    public String getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(String supervisors) {
        this.supervisors = supervisors;
    }

    public String getEquity_transfer_agree() {
        return equity_transfer_agree;
    }

    public void setEquity_transfer_agree(String equity_transfer_agree) {
        this.equity_transfer_agree = equity_transfer_agree;
    }

    public String getRule_promise() {
        return rule_promise;
    }

    public void setRule_promise(String rule_promise) {
        this.rule_promise = rule_promise;
    }

    public String getProfit_distr_plan() {
        return profit_distr_plan;
    }

    public void setProfit_distr_plan(String profit_distr_plan) {
        this.profit_distr_plan = profit_distr_plan;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }
}
