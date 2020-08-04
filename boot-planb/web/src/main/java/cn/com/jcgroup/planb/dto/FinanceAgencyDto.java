package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

public class FinanceAgencyDto {
    @NotBlank
    private String finance_agency_code;

    public String getFinance_agency_code() {
        return finance_agency_code;
    }

    public void setFinance_agency_code(String finance_agency_code) {
        this.finance_agency_code = finance_agency_code;
    }
}
