package cn.com.jcgroup.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * 合营公司人员信息
 *
 * @author LiuYong on 17/6/26 上午1:39.
 */
@Entity
@Table(name = "pb_company_staff")
public class PbCompanyStaff {
    
    @Id
    @SequenceGenerator(name = "companyStaffId", sequenceName = "seq_company_staff_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyStaffId")
    private Integer id;
    
    private String name;
    
    private Date birthday;
    @Column(columnDefinition = "NUMBER(19) default 0 ")
    private Long annualSalary;
    
    private String sex;
    
    private  String birthplace;
    
    private String company; //所属局
    
    private String degree;
    
    private String department;
    @Column(columnDefinition = "clob")
    private String laborRelations;
    
    private String post;
    
    private String qualification;
    @Column(columnDefinition = "clob")
    private String workExperience;
    
    private String telephone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(Long annualSalary) {
        this.annualSalary = annualSalary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLaborRelations() {
        return laborRelations;
    }

    public void setLaborRelations(String laborRelations) {
        this.laborRelations = laborRelations;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
