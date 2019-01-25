package cn.com.jcgroup.service.enums;

/**
 * 业务类型
 *
 * @author LiuYong on 17/6/22 下午11:48.
 */
public enum BusinessTypeEnum {

    TRAVEL("1", "出差"),
    RECEPTION("2", "接待");

    private String type;
    private String info;

    BusinessTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static BusinessTypeEnum convertToEnum(String type) {
        for (BusinessTypeEnum businessTypeEnum : values()) {
            if (businessTypeEnum.getType().equalsIgnoreCase(type)) {
                return businessTypeEnum;
            }
        }
        return null;
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


