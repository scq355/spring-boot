package cn.com.jcgroup.planb.dto;

/**
 * 出差地图
 * @author LiuYong on 17/6/23 上午1:33.
 */
public class BusinessMapDto {

    private String depart_code;
    private String start_time;
    private String end_time;
    private String city_name;

    public String getDepart_code() {
        return depart_code;
    }

    public void setDepart_code(String depart_code) {
        this.depart_code = depart_code;
    }

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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
