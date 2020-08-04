package cn.com.jcgroup.admin.dto;

/**
 * Description: 合营公司-修改
 * User: sunchangqing
 * Date: 2017-06-18
 * Time: 下午3:05
 */
public class CompanyEditDto {

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

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
