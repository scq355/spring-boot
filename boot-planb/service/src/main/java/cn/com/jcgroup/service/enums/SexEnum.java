package cn.com.jcgroup.service.enums;

/**
 * @author LiuYong on 17/6/26 上午10:43.
 */
public enum SexEnum {

    MAN("0","男"),
    WOMAN("1", "女");

    private String type;
    private String info;

    SexEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static SexEnum convertToEnum(String type) {
        for (SexEnum sexEnum : values()) {
            if (sexEnum.getType().equalsIgnoreCase(type)) {
                return sexEnum;
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
