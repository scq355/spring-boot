package cn.com.jcgroup.planb.dto;

/**
 * Description: 出差DTO
 * User: sunchangqing
 * Date: 2017-06-16
 * Time: 下午4:52
 */
public class TravelDto {

    private String depart_code;
    private String start_time;
    private String end_time;
    private int page;


    public String getDepart_code() {
        return depart_code;
    }

    public void setDepart_code(String depart_code) {
        this.depart_code = depart_code;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
