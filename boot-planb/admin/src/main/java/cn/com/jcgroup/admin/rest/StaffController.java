package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.common.PageApiResult;
import cn.com.jcgroup.admin.common.PageInfo;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.StaffInfoUpdateDto;
import cn.com.jcgroup.admin.manage.HumanResourceManage;
import cn.com.jcgroup.admin.manage.StaffManage;
import cn.com.jcgroup.service.enums.CompanyEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 员工相关
 * @author LiuYong on 17/6/25 下午3:03.
 */
@RestController
@RequestMapping(AdminIdentifier.Url.STAFF)
public class StaffController {
    
    private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);
    
    @Autowired
    private StaffManage staffManage;
    @Autowired
    private HumanResourceManage humanResourceManage;
    /**
     * 员工修改
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.STAFF_INFO_UPDATE)
    public ApiResult staffInfoUpdate(@RequestBody StaffInfoUpdateDto staffInfoUpdateDto){
        if(staffManage.buildStaffInfoUpdate(staffInfoUpdateDto)){
            return new ApiResult(null);
        }
        LOG.info("[员工修改]更新失败，staffInfo={}", JSON.toJSONString(staffInfoUpdateDto));
        return new ApiResult(null);
    }

    /**
     * 员工列表
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.STAFF_INFO_LIST)
    public ApiResult staffInfoList(@RequestBody Map<String,Integer> map){
        if(map != null && !map.isEmpty()){
            int page = map.get("page");
            //TODO 根据权限获取部门列表
            JSONObject jsonObject = staffManage.buildStaffInfoList(CompanyEnum.covertToList("0"),page);
            if(jsonObject != null && !jsonObject.isEmpty()){
                return new PageApiResult((PageInfo) jsonObject.get("pageInfo"),jsonObject.get("data"));
            }
        }
        return new ApiResult(null);
    }


    /**
     * 员工同步
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.STAFF_INFO_SYNCHRONIZE)
    public ApiResult staffInfoSynchronize(){
        try{
            humanResourceManage.synHumanResource();
        }catch (Exception e){
            LOG.error("[员工同步]异常",e);
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"同步失败");
        }
        return new ApiResult("null");
    }
}
