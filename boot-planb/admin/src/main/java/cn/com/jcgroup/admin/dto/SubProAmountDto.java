package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 工程量
 * User: sunchangqing
 * Date: 2017-06-25
 * Time: 上午1:58
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubProAmountDto {

    private int id;
    private String sub_pro_code;
    private String report_time;
    private String project_progress;
    private String total_real_paid_money;
    private String total_checked_money;
    private String total_money;
    private String real_paid_money;
    private String checked_money;
    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_pro_code() {
        return sub_pro_code;
    }

    public void setSub_pro_code(String sub_pro_code) {
        this.sub_pro_code = sub_pro_code;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getProject_progress() {
        return project_progress;
    }

    public void setProject_progress(String project_progress) {
        this.project_progress = project_progress;
    }

    public String getTotal_real_paid_money() {
        return total_real_paid_money;
    }

    public void setTotal_real_paid_money(String total_real_paid_money) {
        this.total_real_paid_money = total_real_paid_money;
    }

    public String getTotal_checked_money() {
        return total_checked_money;
    }

    public void setTotal_checked_money(String total_checked_money) {
        this.total_checked_money = total_checked_money;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getReal_paid_money() {
        return real_paid_money;
    }

    public void setReal_paid_money(String real_paid_money) {
        this.real_paid_money = real_paid_money;
    }

    public String getChecked_money() {
        return checked_money;
    }

    public void setChecked_money(String checked_money) {
        this.checked_money = checked_money;
    }
}
