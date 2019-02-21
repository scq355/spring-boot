package com.wowjoy.boot.entity;

import java.util.List;

/**
 * @author scq
 */
public class TableEntity {
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表备注
     */
    private String comments;
    /**
     * 表主键
     */
    private ColumnEntity pk;
    /**
     * 表列名（不包含主键）
     */
    private List<ColumnEntity> columns;
    /**
     * 类名（首字母大写），sys_user => SysUser
     */
    private String className;
    /**
     * 类名（首字母小写），sys_user => sysUser
     */
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
