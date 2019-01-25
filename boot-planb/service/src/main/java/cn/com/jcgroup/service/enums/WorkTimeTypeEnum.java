package cn.com.jcgroup.service.enums;

/**
 * @author LiuYong on 17/6/27 下午8:29.
 */
public enum WorkTimeTypeEnum {

    FIRST_LEVEL("1","1年以下"),
    SECOND_LEVEL("2","1~3年"),
    THIRD_LEVEL("3","3～5年"),
    FOURTH_LEVEL("4","5～10年"),
    FIFTH_LEVEL("5","10年以上");

    private String type;
    private String info;

    WorkTimeTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static WorkTimeTypeEnum convertToEnum(String type) {
        for (WorkTimeTypeEnum workTimeTypeEnum : values()) {
            if (workTimeTypeEnum.getType().equalsIgnoreCase(type)) {
                return workTimeTypeEnum;
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
