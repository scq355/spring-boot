package cn.com.jcgroup.service.enums;

/**
 *  是否展示
 *  @author LiuYong on 17/6/3 下午2:51.
 */
public enum IsShowEnum {

    YES("1", "是"),
    NO("0", "否");

    private String code;
    private String info;

    IsShowEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
