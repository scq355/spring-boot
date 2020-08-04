package cn.com.jcgroup.admin.dto;

/**
 * 员工列表返回dto
 * @author LiuYong on 17/6/25 下午4:20.
 */
public class StuffInfoListDto {

    private String positionLevel;
    private String sex;
    private String department;
    private String stuffCode;
    private String staffName;
    private String academic;
    private String entryTime;
    private String positions;
    private String workSite;
    private long annualSalary;
    private String[] label;

    private String birthday;
    private String firstWorkTime;
    
    //额外字断
    private int age;
    private int currentWorkYears;
    private int workYears;

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStuffCode() {
        return stuffCode;
    }

    public void setStuffCode(String stuffCode) {
        this.stuffCode = stuffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public long getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(long annualSalary) {
        this.annualSalary = annualSalary;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCurrentWorkYears() {
        return currentWorkYears;
    }

    public void setCurrentWorkYears(int currentWorkYears) {
        this.currentWorkYears = currentWorkYears;
    }

    public int getWorkYears() {
        return workYears;
    }

    public void setWorkYears(int workYears) {
        this.workYears = workYears;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstWorkTime() {
        return firstWorkTime;
    }

    public void setFirstWorkTime(String firstWorkTime) {
        this.firstWorkTime = firstWorkTime;
    }
}
