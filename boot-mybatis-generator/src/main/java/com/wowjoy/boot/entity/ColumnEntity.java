package com.wowjoy.boot.entity;

import lombok.Data;

/**
 * @author scq
 */
@Data
public class ColumnEntity {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 数据类型(大写格式)
     */
    private String dataTypeUpperCase;
    /**
     * 列备注
     */
    private String comments;
    /**
     * 属性名称（首字母大写），user_name => UserName
     */
    private String attrName;
    /**
     * 属性名称（首字母小写），user_name => userName
     */
    private String attrname;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * auto_increment
     */
    private String extra;

}
