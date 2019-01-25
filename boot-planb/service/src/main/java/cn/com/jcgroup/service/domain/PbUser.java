package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author LiuYong on 17/5/24 上午10:36.
 */
@Entity
@Table(name = "pb_user", uniqueConstraints = {@UniqueConstraint(columnNames = "userName", name = "u_userName")})
public class PbUser implements Serializable {

    @Id
    @SequenceGenerator(name = "userId", sequenceName = "seq_user_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId")
    private int id;
    private String userName;
    private String company;
    private String realName;
    private Date createTime;
    private Date updateTime;
    private String loginType;

    protected PbUser() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
