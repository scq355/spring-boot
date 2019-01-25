package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 资金流
 * User: sunchangqing
 * Date: 2017-06-25
 * Time: 上午2:03
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubProPaidAmountDto {

    private int id;
    private String sub_pro_code;
    private String amount_of_sub_pro;
    private String pay_time;
    private String fund_info;
    private String paid_money;
    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_pro_code() {
        return sub_pro_code;
    }

    public void setSub_pro_code(String sub_pro_code) {
        this.sub_pro_code = sub_pro_code;
    }

    public String getAmount_of_sub_pro() {
        return amount_of_sub_pro;
    }

    public void setAmount_of_sub_pro(String amount_of_sub_pro) {
        this.amount_of_sub_pro = amount_of_sub_pro;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getFund_info() {
        return fund_info;
    }

    public void setFund_info(String fund_info) {
        this.fund_info = fund_info;
    }

    public String getPaid_money() {
        return paid_money;
    }

    public void setPaid_money(String paid_money) {
        this.paid_money = paid_money;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
