package cn.com.jcgroup.service.enums;

/**
 * oa公司映射
 * @author LiuYong on 17/6/19 下午1:59.
 */
public enum OaCompanyEnum {

    FIRST(798, "1"),
    SECOND(796, "2"),
    THIRD(800, "3"),
    FOURTH(804, "4"),
    FIFTH(974, "5");

    private int code;
    private String department;

    OaCompanyEnum(int code, String department) {
        this.code = code;
        this.department = department;

    }

    public static OaCompanyEnum convertToEnum(int code) {
        for (OaCompanyEnum companyEnum : values()) {
            if (companyEnum.getCode() == code) {
                return companyEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
