package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LiuYong on 17/6/16 上午12:26.
 */
@Entity
@Table(name = "pb_reception")
public class PbReception {

    @Id
    @SequenceGenerator(name = "receptionId", sequenceName = "seq_reception_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receptionId")
    private int id;
    private String company;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer personNumber;
    private Date startTime;
    private Date endTime;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long fee;
    @Column(columnDefinition = "clob")
    private String reason;
    @Column(columnDefinition = "clob")
    private String keyWords;
    private String workPlan;
    private String guestCompany;
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

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getGuestCompany() {
        return guestCompany;
    }

    public void setGuestCompany(String guestCompany) {
        this.guestCompany = guestCompany;
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
