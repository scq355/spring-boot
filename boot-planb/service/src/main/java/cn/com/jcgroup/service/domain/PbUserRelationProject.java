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
 * 用户项目权限表
 *
 * @author LiuYong on 17/5/25 下午5:46.
 */
@Entity
@Table(name = "pb_user_relation_project",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userName", "proCode"}, name = "u_userName_proCode")})
public class PbUserRelationProject {

    @Id
    @SequenceGenerator(name = "userRelationProjectId", sequenceName = "seq_user_relation_project_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRelationProjectId")
    private int id;
    private String userName;
    private String proCode;
    private int roleId;
    private Date createTime;
    private Date updateTime;

    public PbUserRelationProject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
