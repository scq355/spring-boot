package cn.com.jcgroup.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * 员工异动流水
 */
@Entity
@Table(name = "pb_person_flow_info",uniqueConstraints = {@UniqueConstraint(columnNames = "relationId", name = "u_personFlowRelationId")})
public class PbPersonFlowInfo {

    @Id
    @SequenceGenerator(name = "personFlowInfoId", sequenceName = "seq_person_flow_info_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personFlowInfoId")
    private int id;
    private String stuffCode;
    private Date beginTime;
    private Date endTime;
    private String type;
    private String isEnd;
    private String relationId;
    private Date modifyTime;

    public PbPersonFlowInfo() {}

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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
