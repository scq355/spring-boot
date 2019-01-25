package cn.com.jcgroup.admin.dto;

/**
 * @author LiuYong on 17/6/26 上午1:10.
 */
public class ReceptionListDto {

    private String applyStaffName;  //申请人姓名
    private String company;
    private String fee;               //出差费用
    private String guestCompany;
    private String[] keyWords;        //关键字
    private int personNumber;       //出差人数
    private String reason;          //出差事由
    private String relationId;
    private String startTime;         //出差起始时间
    private String workPlan;           //工作安排

    public String getApplyStaffName() {
        return applyStaffName;
    }

    public void setApplyStaffName(String applyStaffName) {
        this.applyStaffName = applyStaffName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getGuestCompany() {
        return guestCompany;
    }

    public void setGuestCompany(String guestCompany) {
        this.guestCompany = guestCompany;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }
}
