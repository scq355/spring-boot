package cn.com.jcgroup.service.domain;

import javax.persistence.*;

/**
 * Description:合营公司-机构-关系
 * User: sunchangqing
 * Date: 2017-06-15
 * Time: 下午4:31
 */
@Entity
@Table(name = "pb_company_agency_relation", uniqueConstraints = {@UniqueConstraint(columnNames = "comAgencyRelationCode", name = "u_companyAgencyCode")})
public class PbCompanyAgencyRelation {
    @Id
    @SequenceGenerator(name = "companyAgencyRelationId", sequenceName = "seq_company_agency_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyAgencyRelationId")
    private int id;
    private String companyCode;
    private String comAgencyRelationCode;
    private String type;

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

    public String getComAgencyRelationCode() {
        return comAgencyRelationCode;
    }

    public void setComAgencyRelationCode(String comAgencyRelationCode) {
        this.comAgencyRelationCode = comAgencyRelationCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
