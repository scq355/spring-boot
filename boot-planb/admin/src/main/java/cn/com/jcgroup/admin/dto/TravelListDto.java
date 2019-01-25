package cn.com.jcgroup.admin.dto;

/**
 * 出差列表返回dto
 *
 * @author LiuYong on 17/6/25 下午9:18.
 */
public class TravelListDto {

    private String company;
    private int personNumber;       //出差人数
    private String startTime;         //出差起始时间
    private String endTime;           //出差结束时间
    private String fee;               //出差费用
    private String reason;          //出差事由
    private String[] keyWords;        //关键字
    private String relationId;
    private String applyStaffName;  //申请人姓名


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getApplyStaffName() {
        return applyStaffName;
    }

    public void setApplyStaffName(String applyStaffName) {
        this.applyStaffName = applyStaffName;
    }
}
