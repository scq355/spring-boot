package cn.com.jcgroup.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 合营公司－员工关系表
 * 
 * @author LiuYong on 17/6/26 上午1:50.
 */
@Entity
@Table(name = "pb_company_staff_relation", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyCode","companyStaffId"}, name = "u_companyStaffRelation")})
public class PbCompanyStaffRelation {
    
    @Id
    @SequenceGenerator(name = "companyStaffRelationId", sequenceName = "seq_company_staff_relation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyStaffRelationId")
    private int id;
    
    private String companyCode;
    
    private int companyStaffId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getCompanyStaffId() {
        return companyStaffId;
    }

    public void setCompanyStaffId(int companyStaffId) {
        this.companyStaffId = companyStaffId;
    }
}
