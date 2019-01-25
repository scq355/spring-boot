package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 已付资金
 */
@Entity
@Table(name = "pb_sub_project_paid_item")
public class PbSubProjectPaidItem {

    @Id
    @SequenceGenerator(name = "subProjectPaidItemId", sequenceName = "seq_sub_project_pay_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subProjectPaidItemId")
    private int id;
    private String subProCode;  //工程编码
    private String fundInfo;    //资金描述
    private Date payTime;       //打款时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long paidMoney;     //已付资金
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long amountOfSubPro;    //工程产值

    public PbSubProjectPaidItem() {
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

    public String getFundInfo() {
        return fundInfo;
    }

    public void setFundInfo(String fundInfo) {
        this.fundInfo = fundInfo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Long paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Long getAmountOfSubPro() {
        return amountOfSubPro;
    }

    public void setAmountOfSubPro(Long amountOfSubPro) {
        this.amountOfSubPro = amountOfSubPro;
    }
}
