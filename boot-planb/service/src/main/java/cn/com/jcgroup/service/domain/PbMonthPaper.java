package cn.com.jcgroup.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * 月度台账
 */
@Entity
@Table(name = "pb_month_paper")
public class PbMonthPaper {

    @Id
    @SequenceGenerator(name = "monthPaperId", sequenceName = "seq_month_paper_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthPaperId")
    private int id;
    private String subProCode;  //工程编号
    private String paperName;   //月报名称
    private String paperLink;   //链接
    private String paperExt;    //后缀名
    private Date createTime;    //创建时间
    private String isShow;      //是否显示

    public PbMonthPaper() {
    }

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

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperLink() {
        return paperLink;
    }

    public void setPaperLink(String paperLink) {
        this.paperLink = paperLink;
    }

    public String getPaperExt() {
        return paperExt;
    }

    public void setPaperExt(String paperExt) {
        this.paperExt = paperExt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
