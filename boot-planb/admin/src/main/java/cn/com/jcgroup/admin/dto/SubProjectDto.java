package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 工程
 * User: sunchangqing
 * Date: 2017-06-14
 * Time: 上午10:04
 */
public class SubProjectDto {

    private String sub_pro_code;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String is_show;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String sub_pro_id;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String sub_project_name;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String total_money;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String pro_code;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private int page;

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getSub_pro_code() {
        return sub_pro_code;
    }

    public void setSub_pro_code(String sub_pro_code) {
        this.sub_pro_code = sub_pro_code;
    }

    public String getSub_pro_id() {
        return sub_pro_id;
    }

    public void setSub_pro_id(String sub_pro_id) {
        this.sub_pro_id = sub_pro_id;
    }

    public String getSub_project_name() {
        return sub_project_name;
    }

    public void setSub_project_name(String sub_project_name) {
        this.sub_project_name = sub_project_name;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
