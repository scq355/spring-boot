package cn.com.jcgroup.admin.dto;

/**
 * Description: 项目
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午4:47
 */
public class ProjectCodeDto {

    private String pro_code;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_code() {
        return pro_code;
    }
}
