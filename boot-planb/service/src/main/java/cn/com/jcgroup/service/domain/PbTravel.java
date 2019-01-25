package cn.com.jcgroup.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * 出差
 *
 * @author LiuYong on 17/6/16 上午12:21.
 */
@Entity
@Table(name = "pb_travel",uniqueConstraints = {@UniqueConstraint(columnNames = "relationId", name = "u_relationId")})
public class PbTravel {
    
    @Id
    @SequenceGenerator(name = "travelId", sequenceName = "seq_travel_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelId")
    private int id;
    private String company;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer personNumber;       //出差人数
    private Date startTime;         //出差起始时间
    private Date endTime;           //出差结束时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long fee;               //出差费用
    @Column(columnDefinition = "clob")
    private String reason;          //出差事由
    @Column(columnDefinition = "clob")
    private String keyWords;        //关键字
    private String relationId;
    
    private String applyStaffNo;  //申请人员工编号
    private String applyStaffName;  //申请人姓名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getApplyStaffNo() {
        return applyStaffNo;
    }

    public void setApplyStaffNo(String applyStaffNo) {
        this.applyStaffNo = applyStaffNo;
    }

    public String getApplyStaffName() {
        return applyStaffName;
    }

    public void setApplyStaffName(String applyStaffName) {
        this.applyStaffName = applyStaffName;
    }
}
