package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 项目列表-项目修改
 * User: sunchangqing
 * Date: 2017-06-18
 * Time: 上午11:06
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectListProjectDto {

    private String pro_abbr;
    private String pro_code;
    private String pro_name;
    private boolean is_show;

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getPro_abbr() {
        return pro_abbr;
    }

    public void setPro_abbr(String pro_abbr) {
        this.pro_abbr = pro_abbr;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }
}
