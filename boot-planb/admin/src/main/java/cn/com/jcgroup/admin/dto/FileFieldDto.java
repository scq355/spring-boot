package cn.com.jcgroup.admin.dto;

/**
 * Description:文档详情
 * User: sunchangqing
 * Date: 2017-06-15
 * Time: 下午1:37
 */
public class FileFieldDto {

    private String field_name;
    private String field_value;
    private int id;
    private boolean isShow;
    private String pro_code;

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }
}
