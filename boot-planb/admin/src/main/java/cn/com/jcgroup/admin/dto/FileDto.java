package cn.com.jcgroup.admin.dto;

/**
 * Description: 文档库
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午7:43
 */
public class FileDto {

    private String file_code;
    private String field_name;
    private String field_value;
    private String id;
    private String is_show;
    private String pro_code;
    private int page;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }
}
