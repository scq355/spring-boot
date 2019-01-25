package cn.com.jcgroup.admin.enums;

/**
 * 增删改查
 * @author LiuYong on 17/6/11 下午8:11.
 */
public enum CrudEnum {
    
    C("add","增"),
    R("delete","删"),
    U("update","改"),
    D("query","查");
    
    private String type;
    private String info;
    
    CrudEnum(String type,String info){
        this.type = type;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
