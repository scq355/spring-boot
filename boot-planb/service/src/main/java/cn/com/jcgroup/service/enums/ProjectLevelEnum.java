package cn.com.jcgroup.service.enums;

/**
 * 项目级别
 *
 * @author LiuYong on 17/6/6 下午3:15.
 */
public enum ProjectLevelEnum {

    A("A", "级别A"),
    B("B", "级别B"),
    C("C", "级别C"),
    D("D", "级别D");

    private String level;
    private String info;

    ProjectLevelEnum(String level, String info) {
        this.level = level;
        this.info = info;
    }

    public static ProjectLevelEnum convertToEnum(String level) {
        for (ProjectLevelEnum projectLevelEnum : values()) {
            if (projectLevelEnum.getLevel().equalsIgnoreCase(level)) {
                return projectLevelEnum;
            }
        }
        return null;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
