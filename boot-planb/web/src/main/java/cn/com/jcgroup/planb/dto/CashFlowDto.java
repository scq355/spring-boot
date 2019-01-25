package cn.com.jcgroup.planb.dto;

import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 资金流
 * @author LiuYong on 17/6/8 下午10:39.
 */
public class CashFlowDto {
    
    @NotBlank
    private String company_code;
    @Pattern(regexp = PlanbIdentifier.Regex.ONE_OR_TWO, message = "只能为字符1或2")
    private String type;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
