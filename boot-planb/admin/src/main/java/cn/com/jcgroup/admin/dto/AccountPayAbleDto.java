package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description: 应收台账
 * User: sunchangqing
 * Date: 2017-06-20
 * Time: 下午4:32
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountPayAbleDto {

    private int id;                     //主键ID
    private String sub_pro_code;
    private String contract_code;        //合同编码
    private String contract_name;        //合同名称
    private String cooper_a;             //甲方
    private String cooper_b;             //乙方
    private String sign_time;              //签约时间
    private String cooperation_period;   //合作期限
    private String payable_item;         //应收事项
    private String payable_details;      //应收明细
    private String payable_way;          //应收方式
    private String payable_node;         //应收节点
    private String pay_time;               //应收时间
    private String real_amount_paid;        //实收时间
    private boolean invoice_provide;      //提供发票
    private String other_info;           //其他信息（备注）
    private boolean is_show;              //是否显示

    private String contract_money;
    private String summary_contract;
    private String real_pay_time;

    private String payable_time;

    private String agreement_money;           //签约金额
    private String amount_paid;            //实收金额
    private String pay_money;            //应收金额


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

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getCooper_a() {
        return cooper_a;
    }

    public void setCooper_a(String cooper_a) {
        this.cooper_a = cooper_a;
    }

    public String getCooper_b() {
        return cooper_b;
    }

    public void setCooper_b(String cooper_b) {
        this.cooper_b = cooper_b;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getCooperation_period() {
        return cooperation_period;
    }

    public void setCooperation_period(String cooperation_period) {
        this.cooperation_period = cooperation_period;
    }

    public String getPayable_item() {
        return payable_item;
    }

    public void setPayable_item(String payable_item) {
        this.payable_item = payable_item;
    }

    public String getPayable_details() {
        return payable_details;
    }

    public void setPayable_details(String payable_details) {
        this.payable_details = payable_details;
    }

    public String getPayable_way() {
        return payable_way;
    }

    public void setPayable_way(String payable_way) {
        this.payable_way = payable_way;
    }

    public String getPayable_node() {
        return payable_node;
    }

    public void setPayable_node(String payable_node) {
        this.payable_node = payable_node;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getReal_amount_paid() {
        return real_amount_paid;
    }

    public void setReal_amount_paid(String real_amount_paid) {
        this.real_amount_paid = real_amount_paid;
    }


    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getContract_money() {
        return contract_money;
    }

    public void setContract_money(String contract_money) {
        this.contract_money = contract_money;
    }

    public String getSummary_contract() {
        return summary_contract;
    }

    public void setSummary_contract(String summary_contract) {
        this.summary_contract = summary_contract;
    }

    public String getReal_pay_time() {
        return real_pay_time;
    }

    public void setReal_pay_time(String real_pay_time) {
        this.real_pay_time = real_pay_time;
    }

    public String getAgreement_money() {
        return agreement_money;
    }

    public void setAgreement_money(String agreement_money) {
        this.agreement_money = agreement_money;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public boolean isInvoice_provide() {
        return invoice_provide;
    }

    public void setInvoice_provide(boolean invoice_provide) {
        this.invoice_provide = invoice_provide;
    }

    public String getPayable_time() {
        return payable_time;
    }

    public void setPayable_time(String payable_time) {
        this.payable_time = payable_time;
    }
}
