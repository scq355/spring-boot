package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

public class AgencyDto {

    @NotBlank
    private String company_code;
    private String type;
    private String[] typeArray;
    private String agency_code;

    public String getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(String agency_code) {
        this.agency_code = agency_code;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getTypeArray() {
        return typeArray;
    }

    public void setTypeArray(String[] typeArray) {
        this.typeArray = typeArray;
    }
}
