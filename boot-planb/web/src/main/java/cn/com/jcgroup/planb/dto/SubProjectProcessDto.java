package cn.com.jcgroup.planb.dto;

import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 工程完成情况dto
 * @author LiuYong on 17/6/14 下午8:29.
 */
public class SubProjectProcessDto {
    
    @NotBlank
    private String sub_project_code;
    @Pattern(regexp = PlanbIdentifier.Regex.YEAR_DOT_MONTH)
    private String date;

    public String getSub_project_code() {
        return sub_project_code;
    }

    public void setSub_project_code(String sub_project_code) {
        this.sub_project_code = sub_project_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
