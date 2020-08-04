package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 工程
 */
@Entity
@Table(name = "pb_sub_project", uniqueConstraints = {@UniqueConstraint(columnNames = "subProCode", name = "u_subProCode")})
public class PbSubProject {

    @Id
    @SequenceGenerator(name = "subProjectId", sequenceName = "seq_sub_project_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subProjectId")
    private int id;
    private String subProCode;  //工程编码
    private String proCode;     //项目编码
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long totalMoney;  //合同总金额
    private String isShow;      //是否展示

    private String subProjectName;  //工程名称
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间

    private String fileCode;     //关联合同文件编码

    public PbSubProject() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubProCode() {
        return subProCode;
    }

    public void setSubProCode(String subProCode) {
        this.subProCode = subProCode;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
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

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
