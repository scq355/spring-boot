package cn.com.jcgroup.service.enums;

/**
 * @author LiuYong on 17/6/27 下午8:29.
 */
public enum  AgeTypeEnum {

    FIRST_LEVEL("1","20～30岁"),
    SECOND_LEVEL("2","31～40岁"),
    THIRD_LEVEL("3","41～50岁"),
    FOURTH_LEVEL("4","50岁以上");

    private String type;
    private String info;

    AgeTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static AgeTypeEnum convertToEnum(String type) {
        for (AgeTypeEnum ageTypeEnum : values()) {
            if (ageTypeEnum.getType().equalsIgnoreCase(type)) {
                return ageTypeEnum;
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
