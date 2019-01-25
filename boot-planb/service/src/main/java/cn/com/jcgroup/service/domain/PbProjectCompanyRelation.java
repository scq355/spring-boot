package cn.com.jcgroup.service.domain;


import javax.persistence.*;

/**
 * 项目-合营公司关系
 */
@Entity
@Table(name = "pb_project_company_relation", uniqueConstraints = {@UniqueConstraint(columnNames = {"proCode", "companyCode"}, name = "u_code")})
public class PbProjectCompanyRelation {
    @Id
    @SequenceGenerator(name = "projectCompanyRelationId",sequenceName = "seq_project_company_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "projectCompanyRelationId")
    private int id;
    private String proCode;
    private String companyCode;
    private String type;

    public PbProjectCompanyRelation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
