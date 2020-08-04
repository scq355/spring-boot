package cn.com.jcgroup.admin.dto;

/**
 * Created with IntelliJ IDEA.
 * Description: 应付台账
 * User: sunchangqing
 * Date: 2017-06-20
 * Time: 下午4:32
 */
public class AccountPayForDto {

    private int id;
    private String agreement_money;
    private String contract_code;
    private String contract_name;
    private String cooper_a;
    private String cooper_b;
    private String cooper_c;
    private String sign_time;
    private String expected_end_time;
    private String contract_payment_term;   //合同支付期限
    private String contract_money;
    private String settle_accounts;
    private String total_amount_payable;    //累计应付金额
    private String pay_money;
    private String pay_time;
    private String invoice_amount;
    private String unpaid_amount;
    private String remark;

    private String sub_pro_code;
    private String summary_contract;

    private String accumulated_payable;
    private String pricing_type;

    private boolean is_show;
    private String pay_able_item;
    private String valuation_type;

    public String getPricing_type() {
        return pricing_type;
    }

    public void setPricing_type(String pricing_type) {
        this.pricing_type = pricing_type;
    }

    public String getAccumulated_payable() {
        return accumulated_payable;
    }

    public void setAccumulated_payable(String accumulated_payable) {
        this.accumulated_payable = accumulated_payable;
    }

    public String getSummary_contract() {
        return summary_contract;
    }

    public void setSummary_contract(String summary_contract) {
        this.summary_contract = summary_contract;
    }

    public String getSub_pro_code() {
        return sub_pro_code;
    }

    public void setSub_pro_code(String sub_pro_code) {
        this.sub_pro_code = sub_pro_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCooper_c() {
        return cooper_c;
    }

    public void setCooper_c(String cooper_c) {
        this.cooper_c = cooper_c;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getExpected_end_time() {
        return expected_end_time;
    }

    public void setExpected_end_time(String expected_end_time) {
        this.expected_end_time = expected_end_time;
    }

    public String getAgreement_money() {
        return agreement_money;
    }

    public void setAgreement_money(String agreement_money) {
        this.agreement_money = agreement_money;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getContract_money() {
        return contract_money;
    }

    public void setContract_money(String contract_money) {
        this.contract_money = contract_money;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getContract_payment_term() {
        return contract_payment_term;
    }

    public void setContract_payment_term(String contract_payment_term) {
        this.contract_payment_term = contract_payment_term;
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

    public String getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(String invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getPay_able_item() {
        return pay_able_item;
    }

    public void setPay_able_item(String pay_able_item) {
        this.pay_able_item = pay_able_item;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSettle_accounts() {
        return settle_accounts;
    }

    public void setSettle_accounts(String settle_accounts) {
        this.settle_accounts = settle_accounts;
    }

    public String getTotal_amount_payable() {
        return total_amount_payable;
    }

    public void setTotal_amount_payable(String total_amount_payable) {
        this.total_amount_payable = total_amount_payable;
    }

    public String getUnpaid_amount() {
        return unpaid_amount;
    }

    public void setUnpaid_amount(String unpaid_amount) {
        this.unpaid_amount = unpaid_amount;
    }

    public String getValuation_type() {
        return valuation_type;
    }

    public void setValuation_type(String valuation_type) {
        this.valuation_type = valuation_type;
    }
}
