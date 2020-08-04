package cn.com.jcgroup.service.enums;

/**
 * 学历类型
 *
 * @author LiuYong on 17/6/25 下午6:41.
 */
public enum DegreeTypeEnum {

    COLLEGE("1", "大专"),
    UNDERGRADUATE("2", "本科"),
    MASTER("3", "硕士"),
    DOCTOR("4", "博士"),
    OTHER("5", "其它");

    private String type;
    private String info;

    DegreeTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static DegreeTypeEnum convertToEnum(String type) {
        for (DegreeTypeEnum degreeTypeEnum : values()) {
            if (degreeTypeEnum.getType().equalsIgnoreCase(type)) {
                return degreeTypeEnum;
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
