package cn.com.jcgroup.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 项目激励
 *
 * @author LiuYong on 17/5/27 下午11:16.
 */
@Entity
@Table(name = "pb_encourage")
public class PbEncourage {

    @Id
    @SequenceGenerator(name = "encourageId", sequenceName = "seq_encourage_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "encourageId")
    private int id;
    private String proCode;     //项目编码
    private String stage;       //所处阶段
    private String prePercent;     //预计发放比例
    @Temporal(TemporalType.DATE)
    private Date preDate;       //预计发放时间
    private String realPercent;    //实际发放比例
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long realMoney;     //已融资金额

    private Integer moneyFlag;   //实际发放情况  （0，1）

    private Integer realStatus;  //费用到位情况（0，1）


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPrePercent() {
        return prePercent;
    }

    public void setPrePercent(String prePercent) {
        this.prePercent = prePercent;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate;
    }

    public String getRealPercent() {
        return realPercent;
    }

    public void setRealPercent(String realPercent) {
        this.realPercent = realPercent;
    }

    public Long getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Long realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getMoneyFlag() {
        return moneyFlag;
    }

    public void setMoneyFlag(Integer moneyFlag) {
        this.moneyFlag = moneyFlag;
    }

    public Integer getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(Integer realStatus) {
        this.realStatus = realStatus;
    }
}
