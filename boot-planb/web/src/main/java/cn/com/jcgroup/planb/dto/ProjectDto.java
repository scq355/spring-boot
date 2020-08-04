package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 项目
 * @author LiuYong on 17/6/9 上午9:54.
 */
public class ProjectDto {
    
    @NotBlank
    private String project_code;

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }
}
