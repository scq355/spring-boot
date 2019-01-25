package cn.com.jcgroup.service.domain;

import javax.persistence.*;

/**
 * 文件字段表
 *
 * @author LiuYong on 17/6/5 下午7:09.
 */
@Entity
@Table(name = "pb_file_field", uniqueConstraints = {@UniqueConstraint(columnNames = "fileCode", name = "u_fileCode")})
public class PbFileField {

    @Id
    @SequenceGenerator(name = "fileFieldId", sequenceName = "seq_file_field_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileFieldId")
    private int id;
    private String fileCode;
    private String fieldName;
    @Column(columnDefinition = "clob")
    private String fieldValue;
    private String isShow;
    @Column(columnDefinition = "NUMBER(10) default 0 ")
    private int showOrder;

    public PbFileField() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}
