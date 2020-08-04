package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 项目概况编辑
 * User: sunchangqing
 * Date: 2017-06-21
 * Time: 下午3:06
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectEditInfoDto {
    private String bank_percent;
    private String building_area;
    private String collect_capital;
    private String constr_point_down;
    private String construction_period;
    private String cooper_content;
    private String department;
    private String expect_profit;
    private String fund_capital;
    private String gover_point_down;
    private String invest_percent;
    private boolean is_core;
    private String land_area;

    private String storage_level;
    private String operation_period;
    private String plan_area;
    private String pro_code;
    private String project_address;
    private String affi_city;
    private String project_manager;
    private String project_nature;
    private String affi_province;
    private String region;
    private String repurchase_base;

    private String repurchase_method;
    private String repurchase_percent;
    private String repurchase_period;
    private String scales;
    private String project_period;
    
    //项目状态
    private String project_status;
    
    private String inner_level;

    public String getBank_percent() {
        return bank_percent;
    }

    public void setBank_percent(String bank_percent) {
        this.bank_percent = bank_percent;
    }

    public String getBuilding_area() {
        return building_area;
    }

    public void setBuilding_area(String building_area) {
        this.building_area = building_area;
    }

    public String getCollect_capital() {
        return collect_capital;
    }

    public void setCollect_capital(String collect_capital) {
        this.collect_capital = collect_capital;
    }

    public String getConstr_point_down() {
        return constr_point_down;
    }

    public void setConstr_point_down(String constr_point_down) {
        this.constr_point_down = constr_point_down;
    }

    public String getConstruction_period() {
        return construction_period;
    }

    public void setConstruction_period(String construction_period) {
        this.construction_period = construction_period;
    }

    public String getCooper_content() {
        return cooper_content;
    }

    public void setCooper_content(String cooper_content) {
        this.cooper_content = cooper_content;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExpect_profit() {
        return expect_profit;
    }

    public void setExpect_profit(String expect_profit) {
        this.expect_profit = expect_profit;
    }

    public String getFund_capital() {
        return fund_capital;
    }

    public void setFund_capital(String fund_capital) {
        this.fund_capital = fund_capital;
    }

    public String getGover_point_down() {
        return gover_point_down;
    }

    public void setGover_point_down(String gover_point_down) {
        this.gover_point_down = gover_point_down;
    }

    public String getInvest_percent() {
        return invest_percent;
    }

    public void setInvest_percent(String invest_percent) {
        this.invest_percent = invest_percent;
    }

    public boolean isIs_core() {
        return is_core;
    }

    public void setIs_core(boolean is_core) {
        this.is_core = is_core;
    }

    public String getLand_area() {
        return land_area;
    }

    public void setLand_area(String land_area) {
        this.land_area = land_area;
    }

    public String getStorage_level() {
        return storage_level;
    }

    public void setStorage_level(String storage_level) {
        this.storage_level = storage_level;
    }

    public String getOperation_period() {
        return operation_period;
    }

    public void setOperation_period(String operation_period) {
        this.operation_period = operation_period;
    }

    public String getPlan_area() {
        return plan_area;
    }

    public void setPlan_area(String plan_area) {
        this.plan_area = plan_area;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getProject_address() {
        return project_address;
    }

    public void setProject_address(String project_address) {
        this.project_address = project_address;
    }

    public String getAffi_city() {
        return affi_city;
    }

    public void setAffi_city(String affi_city) {
        this.affi_city = affi_city;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getProject_nature() {
        return project_nature;
    }

    public void setProject_nature(String project_nature) {
        this.project_nature = project_nature;
    }

    public String getAffi_province() {
        return affi_province;
    }

    public void setAffi_province(String affi_province) {
        this.affi_province = affi_province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRepurchase_base() {
        return repurchase_base;
    }

    public void setRepurchase_base(String repurchase_base) {
        this.repurchase_base = repurchase_base;
    }

    public String getRepurchase_method() {
        return repurchase_method;
    }

    public void setRepurchase_method(String repurchase_method) {
        this.repurchase_method = repurchase_method;
    }

    public String getRepurchase_percent() {
        return repurchase_percent;
    }

    public void setRepurchase_percent(String repurchase_percent) {
        this.repurchase_percent = repurchase_percent;
    }

    public String getRepurchase_period() {
        return repurchase_period;
    }

    public void setRepurchase_period(String repurchase_period) {
        this.repurchase_period = repurchase_period;
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales;
    }

    public String getProject_period() {
        return project_period;
    }

    public void setProject_period(String project_period) {
        this.project_period = project_period;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public String getInner_level() {
        return inner_level;
    }

    public void setInner_level(String inner_level) {
        this.inner_level = inner_level;
    }
}

