package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.common.PageApiResult;
import cn.com.jcgroup.admin.common.PageInfo;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.TravelDetailUpdateDto;
import cn.com.jcgroup.admin.dto.TravelUpdateDto;
import cn.com.jcgroup.admin.manage.BusinessManage;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.service.BusinessService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 业务相关
 * 
 * @author LiuYong on 17/6/25 下午9:30.
 */
@RestController
@RequestMapping(AdminIdentifier.Url.BUSINESS)
public class BusinessController {
    
    private static final Logger LOG = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessManage businessManage;
    @Autowired
    private BusinessService businessService;
    
    /**
     * 出差列表
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_TRAVEL_LIST)
    public ApiResult travelList(@RequestBody Map<String,Integer> map){
        if(map != null && !map.isEmpty()){
            int page = map.get("page");
            //TODO 根据权限获取部门列表
            JSONObject jsonObject = businessManage.buildTravelInfoList(CompanyEnum.covertToList("0"),page);
            if(jsonObject != null && !jsonObject.isEmpty()){
                return new PageApiResult((PageInfo) jsonObject.get("pageInfo"),jsonObject.get("data"));
            }
        }
        return new ApiResult(null);
    }

    /**
     *出差或接待列表修改
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_LIST_UPDATE)
    public ApiResult listInfoUpdate(@RequestBody TravelUpdateDto travelUpdateDto){
        if(businessService.updateKeyWords(travelUpdateDto.getKeyWords(),travelUpdateDto.getRelationId(),travelUpdateDto.getType())){
            return new ApiResult(null);
        }
        LOG.info("[出差或接待列表修改]更新失败，travelUpdateDto={}", JSON.toJSONString(travelUpdateDto));
        return new ApiResult(null);
    }

    /**
     *出差或接待明细
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_DETAIL_QUERY)
    public ApiResult travelDetailQuery(@RequestBody Map<String,String> map){
        String relationId = map.get("relationId");
        if(StringUtils.isBlank(relationId)){
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"relationId不能为空");
        }
        JSONArray jsonArray = businessService.findByRelationId(relationId,map.get("type"));
        return new ApiResult(jsonArray);
    }

    /**
     *出差或接待明细修改
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_DETAIL_UPDATE)
    public ApiResult travelDetailUpdate(@RequestBody TravelDetailUpdateDto travelDetailUpdateDto){
        String id = travelDetailUpdateDto.getId();
        try{
            int tempId = Integer.parseInt(id);
            boolean bool = businessService.updateTravelLocation(travelDetailUpdateDto.getArriveMapLocation(),travelDetailUpdateDto.getDepartureMapLocation(),tempId);
            if(bool){
                return new ApiResult(null);
            }
            LOG.info("[出差或接待明细修改]更新失败,travelDetailUpdateDto={}",JSON.toJSONString(travelDetailUpdateDto));
        }catch (Exception e){
            LOG.error("[出差或接待明细修改]id类型转换错误,id={}",id);
            return new ApiResult(ResCodeEnum.INVALID_PARAM,"id非数字字符");
        }
        return new ApiResult(null);
    }

    /**
     * 出差接待同步
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_SYNCHRONIZE)
    public ApiResult businessSynchronize(){
        try{
            //TODO 待补充
            LOG.info("----------------------待补充出差接待同步----------------------------");
        }catch (Exception e){
            LOG.error("[出差接待同步]异常",e);
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"同步失败");
        }
        return new ApiResult("null");
    }

    /**
     * 接待列表
     *
     * @author LiuYong
     */
    @RequestMapping(AdminIdentifier.Url.BUSINESS_RECEPTION_LIST)
    public ApiResult receptionList(@RequestBody Map<String,Integer> map){
        if(map != null && !map.isEmpty()){
            int page = map.get("page");
            //TODO 根据权限获取部门列表
            JSONObject jsonObject = businessManage.buildReceptionInfoList(CompanyEnum.covertToList("0"),page);
            if(jsonObject != null && !jsonObject.isEmpty()){
                return new PageApiResult((PageInfo) jsonObject.get("pageInfo"),jsonObject.get("data"));
            }
        }
        return new ApiResult(null);
    }
}
