package cn.com.jcgroup.service.enums;

/**
 * @author LiuYong on 17/6/22 下午9:32.
 */
public enum LoginTypeEnum {

    FORBID("-1","禁止登录"),
    WEB("0", "登录前台"),
    BACKEND("1", "登录后台"),
    BOTH("2", "前后台均可登录");

    private String type;
    private String info;

    LoginTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static LoginTypeEnum convertToEnum(String type) {
        for (LoginTypeEnum loginTypeEnum : values()) {
            if (loginTypeEnum.getType().equalsIgnoreCase(type)) {
                return loginTypeEnum;
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
