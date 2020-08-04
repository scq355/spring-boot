package cn.com.jcgroup.admin.dto;

/**
 * Description:月度台账
 * User: sunchangqing
 * Date: 2017-06-20
 * Time: 下午7:31
 */
public class MonthlyAccountDto {

    private int id;
    private String year_month;
    private String paper_name;
    private String paper_ext;
    private String is_show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear_month() {
        return year_month;
    }

    public void setYear_month(String year_month) {
        this.year_month = year_month;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getPaper_ext() {
        return paper_ext;
    }

    public void setPaper_ext(String paper_ext) {
        this.paper_ext = paper_ext;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }
}
