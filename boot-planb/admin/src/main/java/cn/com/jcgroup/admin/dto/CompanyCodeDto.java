package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午4:51
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyCodeDto {


    private String company_code;
    private String company_name;
    private String pro_code;
    private boolean is_show;

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getCompany_code() {
        return company_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
