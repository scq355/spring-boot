package cn.com.jcgroup.planb.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class PersonnelDto {

    @NotNull
    private String depart_code;

    private String start_time;

    private String end_time;

    @DecimalMin(value = "1",message = "请求页面数值需大于0")
    private int page;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDepart_code() {
        return depart_code;
    }

    public void setDepart_code(String depart_code) {
        this.depart_code = depart_code;
    }
}
