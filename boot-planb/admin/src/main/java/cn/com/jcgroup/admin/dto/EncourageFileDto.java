package cn.com.jcgroup.admin.dto;

/**
 * Description:激励文件
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 上午11:08
 */
public class EncourageFileDto {

    private String file_code;
    private String file_name;
    private boolean is_show;
    private String pro_code;
    private String file_ext;

    public String getFile_ext() {
        return file_ext;
    }

    public void setFile_ext(String file_ext) {
        this.file_ext = file_ext;
    }

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }
}
