package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pb_business_trip_flow")
public class PbBusinessTripFlow {

    @Id
    @SequenceGenerator(name = "businessTripFlowId", sequenceName = "seq_business_pay_for_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "businessTripFlowId")
    private int id;
    private String startTime;
    private String endTime;
    private String tripReceive;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer personNum;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer fee;
    private String department;
    private String city;
    @Column(length = 1024)
    private String reason;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer totalDays;
    private String workPlan;
    private String gustDepartment;
    private Date createTime;
    private Date updateTime;
    private String rateOfKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTripReceive() {
        return tripReceive;
    }

    public void setTripReceive(String tripReceive) {
        this.tripReceive = tripReceive;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getGustDepartment() {
        return gustDepartment;
    }

    public void setGustDepartment(String gustDepartment) {
        this.gustDepartment = gustDepartment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRateOfKey() {
        return rateOfKey;
    }

    public void setRateOfKey(String rateOfKey) {
        this.rateOfKey = rateOfKey;
    }
}
