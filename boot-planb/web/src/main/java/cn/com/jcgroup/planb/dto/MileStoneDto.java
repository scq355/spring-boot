package cn.com.jcgroup.planb.dto;

import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

/**
 * @author LiuYong on 17/6/7 下午5:40.
 */
public class MileStoneDto {
    
    @NotEmpty
    private String project_code;
//    @Pattern(regexp = PlanbIdentifier.Regex.NONZERONUMBER,message = "请求页面数值需大于0")
    @DecimalMin(value = "1",message = "请求页面数值需大于0")
    private Integer page;

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
