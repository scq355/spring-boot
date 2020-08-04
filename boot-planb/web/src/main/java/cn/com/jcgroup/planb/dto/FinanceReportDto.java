package cn.com.jcgroup.planb.dto;

import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 财务报表
 *
 * @author LiuYong on 17/6/8 下午9:57.
 */
public class FinanceReportDto {

    @NotBlank
    private String company_code;
    @Pattern(regexp = PlanbIdentifier.Regex.YEAR_DOT_MONTH, message = "日期格式不正确,eg:yyyy.MM")
    private String date;
    @Pattern(regexp = PlanbIdentifier.Regex.ONE_OR_TWO, message = "只能为字符1或2")
    private String type;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
