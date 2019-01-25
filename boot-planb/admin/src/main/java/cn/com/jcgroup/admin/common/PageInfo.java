package cn.com.jcgroup.admin.common;

import java.io.Serializable;

/**
 * 分页
 * @author LiuYong  
 */
public class PageInfo implements Serializable{
    
    private int pageNo;
    private int pageSize;
    private boolean hasNext;
    
    public PageInfo(){}

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
