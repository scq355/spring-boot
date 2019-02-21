package com.wowjoy.boot.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author scq
 */
@Data
public class PageUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页尺寸
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页
     */
    private int currPage;
    /**
     * 列表数据
     */
    private List<?> list;

    public PageUtil(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }
}
