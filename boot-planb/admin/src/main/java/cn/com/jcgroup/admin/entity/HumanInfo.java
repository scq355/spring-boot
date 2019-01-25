package cn.com.jcgroup.admin.entity;

import java.util.Date;

/**
 * 人员信息
 *
 * @author LiuYong on 17/6/18 下午5:07.
 */
public class HumanInfo {
    
    private int id;
    
    private String stuffName;
    
    private String stuffCode;
    
    private String positionLevel;

    private String positions;
    
    private String sex;
    
    private String companyName;
    
    private String degree;
    
    private Date birthday;

    private String status;
    
    private Date entryTime;
    
    private Date firstWorkTime;
    
    private String workSite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuffCode() {
        return stuffCode;
    }

    public void setStuffCode(String stuffCode) {
        this.stuffCode = stuffCode;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getFirstWorkTime() {
        return firstWorkTime;
    }

    public void setFirstWorkTime(Date firstWorkTime) {
        this.firstWorkTime = firstWorkTime;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public String getStuffName() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName = stuffName;
    }
}
