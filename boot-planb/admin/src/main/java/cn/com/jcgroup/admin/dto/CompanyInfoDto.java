package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: sunchangqing
 * Date: 2017-06-18
 * Time: 下午4:18
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfoDto {

    private String company_code;
    int page;

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
}
