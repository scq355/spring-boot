package cn.com.jcgroup.admin.dto;

/**
 * Description: 合营公司
 * User: sunchangqing
 * Date: 2017-06-15
 * Time: 下午4:52
 */
public class CompanyDto {
    private String company_code;
    private String company_name;
    private String is_show;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }
}
