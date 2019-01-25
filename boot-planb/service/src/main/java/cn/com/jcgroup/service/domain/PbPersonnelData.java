package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 人才库
 */
@Entity
@Table(name = "pb_personnel_data",uniqueConstraints = {@UniqueConstraint(columnNames = "stuffCode", name = "u_stuffCode")})
public class PbPersonnelData {

    @Id
    @SequenceGenerator(name = "personnelDataId", sequenceName = "seq_personnel_data_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personnelDataId")
    private int id;
    private String positionLevel;
    private String sex;
    private String department;
    private String stuffCode;
    private String staffName;
    private String academic;
    private Date birthday;
    private Date entryTime;
    private Date firstWorkTime;
    private String positions;
    private String status;      //是否在职状态
    private String workSite;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long annualSalary;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Integer relationId;
    private String companyName;
    private String label;

    public PbPersonnelData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public Long getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(Long annualSalary) {
        this.annualSalary = annualSalary;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
