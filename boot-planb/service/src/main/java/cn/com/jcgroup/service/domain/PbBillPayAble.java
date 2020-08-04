package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 应收台账
 */
@Entity
@Table(name = "pb_bill_pay_able")
public class PbBillPayAble {

    @Id
    @SequenceGenerator(name = "billPayAbleId", sequenceName = "seq_bill_pay_able_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billPayAbleId")
    private int id;                     //主键ID
    private String subProCode;          //工程编码
    private String contractCode;        //合同编码
    private String contractName;        //合同名称
    private String cooperA;             //甲方
    private String cooperB;             //乙方
    private Date signTime;              //签约时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long agreementMoney;        //签约金额
    private String cooperationPeriod;   //合作期限
    private String payAbleItem;         //应收事项
    private String payableDetails;      //应收明细
    private String payMoney;              //应收金额
    private String payableWay;          //应收方式
    private String payableNode;         //应收节点
    private Date payTime;               //应收时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long amountPaid;            //实收金额
    private Date realAmountPaid;        //实收时间
    private String invoiceProvide;      //提供发票
    @Column(length = 1024)
    private String otherInfo;           //其他信息（备注）
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long contractMoney;         //合同金额
    private String isShow;              //是否显示

    public PbBillPayAble() {
    }

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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Long getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(Long agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public String getCooperationPeriod() {
        return cooperationPeriod;
    }

    public void setCooperationPeriod(String cooperationPeriod) {
        this.cooperationPeriod = cooperationPeriod;
    }

    public String getPayAbleItem() {
        return payAbleItem;
    }

    public void setPayAbleItem(String payAbleItem) {
        this.payAbleItem = payAbleItem;
    }

    public String getPayableDetails() {
        return payableDetails;
    }

    public void setPayableDetails(String payableDetails) {
        this.payableDetails = payableDetails;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayableWay() {
        return payableWay;
    }

    public void setPayableWay(String payableWay) {
        this.payableWay = payableWay;
    }

    public String getPayableNode() {
        return payableNode;
    }

    public void setPayableNode(String payableNode) {
        this.payableNode = payableNode;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getRealAmountPaid() {
        return realAmountPaid;
    }

    public void setRealAmountPaid(Date realAmountPaid) {
        this.realAmountPaid = realAmountPaid;
    }

    public String getInvoiceProvide() {
        return invoiceProvide;
    }

    public void setInvoiceProvide(String invoiceProvide) {
        this.invoiceProvide = invoiceProvide;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public Long getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Long contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
