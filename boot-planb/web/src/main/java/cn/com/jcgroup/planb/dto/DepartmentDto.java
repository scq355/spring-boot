package cn.com.jcgroup.planb.dto;

import javax.validation.constraints.NotNull;

/**
 * 部门
 */
public class DepartmentDto {

    @NotNull
    private String depart_code;

    public String getDepart_code() {
        return depart_code;
    }

    public void setDepart_code(String depart_code) {
        this.depart_code = depart_code;
    }
}
