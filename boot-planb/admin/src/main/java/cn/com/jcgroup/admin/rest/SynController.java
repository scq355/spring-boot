package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.SYN_FUND;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.SYN_STAFF;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.SYN_STAFF_FLOW;
import cn.com.jcgroup.admin.manage.HumanResourceManage;
import cn.com.jcgroup.admin.manage.PrivateFundManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据同步
 *
 * @author LiuYong on 17/6/16 下午9:01.
 */
@RestController
public class SynController {

    private static final Logger LOG = LoggerFactory.getLogger(SynController.class);

    @Autowired
    private PrivateFundManage privateFundManage;
    @Autowired
    private HumanResourceManage humanResourceManage;
    
    /**
     * 基金同步
     * @author LiuYong  
     */
    @RequestMapping(SYN_FUND)
    public ApiResult synFund() {
        privateFundManage.synPrivateFund();
        return new ApiResult(null);
    }

    /**
     * 员工同步
     * @author LiuYong
     */
    @RequestMapping(SYN_STAFF)
    public ApiResult synStaff() {
        humanResourceManage.synHumanResource();
        return new ApiResult(null);
    }

    /**
     * 员工异动流水同步
     * @author LiuYong
     */
    @RequestMapping(SYN_STAFF_FLOW)
    public ApiResult synStaffFlowInfo() {
        humanResourceManage.synPersonFlowInfo();
        return new ApiResult(null);
    }
    
    
}
