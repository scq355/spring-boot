package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 合营公司
 * @author LiuYong on 17/6/9 上午9:54.
 */
public class CompanyDto {
    
    @NotBlank
    private String company_code;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }
}
