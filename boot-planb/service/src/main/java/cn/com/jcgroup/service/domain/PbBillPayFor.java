package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 应付台账
 */
@Entity
@Table(name = "pb_bill_pay_for")
public class PbBillPayFor {

    @Id
    @SequenceGenerator(name = "billPayForId", sequenceName = "seq_bill_pay_for_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billPayForId")
    private int id;                     //主键ID
    private String subProCode;          //工程编码
    private String contractCode;        //合同编码
    private String contractName;        //合同名称
    private String cooperA;             //甲方
    private String cooperB;             //乙方
    private String cooperC;             //丙方
    private Date signTime;              //签约时间
    private String expectedEndTime;       //工程预计结束时间
    private String pricingType;         //计价类型
    @Column(length = 1024)
    private String summaryContract;     //合同支付条款
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long contractMoney;         //合同金额
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long settleAccount;         //结算金额
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long accumulatedPayable;    //累计应付金额
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long payMoney;              //付款金额（累计应付金额）
    private Date payTime;               //付款时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long invoiceAmount;         //开票金额
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long unpaidAmount;          //未付金额
    private String isShow;              //是否展示（备注）
    @Column(length = 1024)
    private String otherInfo;           //其他信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubProCode() {
        return subProCode;
    }

    public void setSubProCode(String subProCode) {
        this.subProCode = subProCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCooperA() {
        return cooperA;
    }

    public void setCooperA(String cooperA) {
        this.cooperA = cooperA;
    }

    public String getCooperB() {
        return cooperB;
    }

    public void setCooperB(String cooperB) {
        this.cooperB = cooperB;
    }

    public String getCooperC() {
        return cooperC;
    }

    public void setCooperC(String cooperC) {
        this.cooperC = cooperC;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(String expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public String getPricingType() {
        return pricingType;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    public String getSummaryContract() {
        return summaryContract;
    }

    public void setSummaryContract(String summaryContract) {
        this.summaryContract = summaryContract;
    }

    public Long getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Long contractMoney) {
        this.contractMoney = contractMoney;
    }

    public Long getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(Long settleAccount) {
        this.settleAccount = settleAccount;
    }

    public Long getAccumulatedPayable() {
        return accumulatedPayable;
    }

    public void setAccumulatedPayable(Long accumulatedPayable) {
        this.accumulatedPayable = accumulatedPayable;
    }

    public Long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Long getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(Long unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
