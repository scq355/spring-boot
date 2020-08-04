package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: sunchangqing
 * Date: 2017-06-18
 * Time: 下午3:40
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubProEditDto {
    private String pro_code;
    private String sub_pro_code;
    private String sub_pro_name;
    private long total_money;
    private boolean is_show;
    private String file_code;

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public long getTotal_money() {
        return total_money;
    }

    public void setTotal_money(long total_money) {
        this.total_money = total_money;
    }

    public String getSub_pro_code() {
        return sub_pro_code;
    }

    public void setSub_pro_code(String sub_pro_code) {
        this.sub_pro_code = sub_pro_code;
    }

    public String getSub_pro_name() {
        return sub_pro_name;
    }

    public void setSub_pro_name(String sub_pro_name) {
        this.sub_pro_name = sub_pro_name;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }
}
