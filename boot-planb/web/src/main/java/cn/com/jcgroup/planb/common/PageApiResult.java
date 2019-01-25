package cn.com.jcgroup.planb.common;


import cn.com.jcgroup.planb.constant.ResCodeEnum;

/**
 * 带分页效果
 *
 * @author LiuYong on 17/5/26 下午1:40.
 */
public class PageApiResult extends ApiResult {

    //分页对象
    private PageInfo pageinfo;

    /**
     * response correct result with pageInfo
     *
     * @author LiuYong
     */
    public PageApiResult(PageInfo pageInfo,Object data) {
        this.setCode(ResCodeEnum.SERVER_SUCCESS.getCode());
        this.setData(data);
        this.pageinfo = pageInfo;
    }

    public PageInfo getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(PageInfo pageinfo) {
        this.pageinfo = pageinfo;
    }
}
