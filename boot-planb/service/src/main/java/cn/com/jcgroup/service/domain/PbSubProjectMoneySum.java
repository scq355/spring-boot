package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 已付资金统计-工程量
 */
@Entity
@Table(name = "pb_sub_project_money_sum")
public class PbSubProjectMoneySum {
    @Id
    @SequenceGenerator(name = "subProjectMoneySumId", sequenceName = "seq_sub_project_money_sum_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subProjectMoneySumId")
    private int id;
    private Date reportTime;      //年月
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long checkedMoney;      //审定工程款
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long realPaidMoney;     //累计审定工程款
    private String subProCode;      //工程编码
    private String projectProgress; //工程形象进度
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long totalMoney;        //合同总金额
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long totalCheckedMoney; //本月实际支付工程款
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long totalRealPaidMoney;//实际支付工程款

    public PbSubProjectMoneySum() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Long getCheckedMoney() {
        return checkedMoney;
    }

    public void setCheckedMoney(Long checkedMoney) {
        this.checkedMoney = checkedMoney;
    }

    public Long getRealPaidMoney() {
        return realPaidMoney;
    }

    public void setRealPaidMoney(Long realPaidMoney) {
        this.realPaidMoney = realPaidMoney;
    }

    public String getSubProCode() {
        return subProCode;
    }

    public void setSubProCode(String subProCode) {
        this.subProCode = subProCode;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getTotalCheckedMoney() {
        return totalCheckedMoney;
    }

    public void setTotalCheckedMoney(Long totalCheckedMoney) {
        this.totalCheckedMoney = totalCheckedMoney;
    }

    public Long getTotalRealPaidMoney() {
        return totalRealPaidMoney;
    }

    public void setTotalRealPaidMoney(Long totalRealPaidMoney) {
        this.totalRealPaidMoney = totalRealPaidMoney;
    }
}
