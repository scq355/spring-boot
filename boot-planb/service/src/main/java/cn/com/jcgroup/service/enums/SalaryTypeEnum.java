package cn.com.jcgroup.service.enums;

/**
 * @author LiuYong on 17/6/27 下午8:29.
 */
public enum SalaryTypeEnum {

    FIRST_LEVEL("1","5万以下"),
    SECOND_LEVEL("2","5～10万"),
    THIRD_LEVEL("3","10～30万"),
    FOURTH_LEVEL("4","30～60万"),
    FIFTH_LEVEL("5","60万以上");

    private String type;
    private String info;

    SalaryTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static SalaryTypeEnum convertToEnum(String type) {
        for (SalaryTypeEnum salaryTypeEnum : values()) {
            if (salaryTypeEnum.getType().equalsIgnoreCase(type)) {
                return salaryTypeEnum;
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
