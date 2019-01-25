package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 机构列表
 * @author LiuYong on 17/6/21 下午12:07.
 */
public class AgencyListDto {

    @NotBlank
    private String company_code;
    private String[] type;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }
}
