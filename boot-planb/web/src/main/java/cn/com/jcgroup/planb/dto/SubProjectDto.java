package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 工程
 */
public class SubProjectDto {
    @NotBlank
    private String sub_project_code;
    
    private int page;

    public String getSub_project_code() {
        return sub_project_code;
    }

    public void setSub_project_code(String sub_project_code) {
        this.sub_project_code = sub_project_code;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}