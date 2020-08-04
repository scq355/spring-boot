package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pb_encourage_file",uniqueConstraints = {@UniqueConstraint(columnNames = "fileCode",name = "u_fileCode")})
public class PbEncourageFile {
    @Id
    @SequenceGenerator(name = "encourageFileId",sequenceName = "seq_encourage_file_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "encourageFileId")
    private int id;
    private String fileName;    //文件名
    private String fileExt;     //文件后缀名
    private String filePath;    //文件路径
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间
    private String proCode;     //项目编码
    private String isShow;      //是否展示
    private String fileCode;    //文件编码

    public PbEncourageFile() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
