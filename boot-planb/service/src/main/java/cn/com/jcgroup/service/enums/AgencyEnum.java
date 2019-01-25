package cn.com.jcgroup.service.enums;

/**
 * Description: 机构枚举
 * User: sunchangqing
 * Date: 2017-06-14
 * Time: 下午2:15
 */
public enum AgencyEnum {

    PRIVATE_FUND("1", "私募资金"),
    AGENCY("2", "机构"),
    FINANCE_AGENCY("3","金融机构"),
    OTHER("4", "其他");


    private String code;
    private String name;

    AgencyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AgencyEnum convertToEnum(String type) {
        for (AgencyEnum agencyEnum : values()) {
            if (agencyEnum.getCode().equalsIgnoreCase(type)) {
                return agencyEnum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
