package cn.com.jcgroup.service.enums;

/**
 * 员工异动类型
 * @author LiuYong on 17/6/21 上午12:41.
 */
public enum StaffFlowTypeEnum {

    ENTRY("1", "入职"),
    LEAVE("4", "离职");

    private String code;
    private String info;

    StaffFlowTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static StaffFlowTypeEnum convertToEnum(String code) {
        for (StaffFlowTypeEnum staffFlowTypeEnum : values()) {
            if (staffFlowTypeEnum.getCode().equalsIgnoreCase(code)) {
                return staffFlowTypeEnum;
            }
        }
        return null;
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
