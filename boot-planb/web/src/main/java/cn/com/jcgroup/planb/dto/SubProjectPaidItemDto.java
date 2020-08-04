package cn.com.jcgroup.planb.dto;

import javax.validation.constraints.NotNull;

/**
 * 工程资金流水
 */
public class SubProjectPaidItemDto {

    @NotNull
    private String end_time;
    private String start_time;
    private String sub_project_code;

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSub_project_code() {
        return sub_project_code;
    }

    public void setSub_project_code(String sub_project_code) {
        this.sub_project_code = sub_project_code;
    }
}
