package cn.com.jcgroup.service.enums;

/**
 * 项目状态
 * @author LiuYong on 17/6/4 下午11:28.
 */
public enum  ProjectStatusEnum {

    NEGOTIATE("1","洽谈"),
    SIGNED("2","签约"),
    START("3","开工"),
    OPERATION("4","运营");
    
    private String status;
    private String info;
    
    ProjectStatusEnum(String status,String info){
        this.status = status;
        this.info = info;
    }

    public static ProjectStatusEnum convertToEnum(String status) {
        for (ProjectStatusEnum projectStatusEnum : values()) {
            if (projectStatusEnum.getStatus().equalsIgnoreCase(status)) {
                return projectStatusEnum;
            }
        }
        return null;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
