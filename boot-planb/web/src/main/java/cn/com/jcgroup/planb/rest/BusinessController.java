package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.common.PageApiResult;
import cn.com.jcgroup.planb.common.PageInfo;
import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.BUSINESS;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.MAP_LINE;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.MAP_POINT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.MAP_TOPTEN;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.RECEPTION;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.REPORT_RECEPTION;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.REPORT_TRAVEL;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.TRAVEL;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.dto.BusinessMapDto;
import cn.com.jcgroup.planb.dto.TravelDto;
import cn.com.jcgroup.planb.manage.BusinessManage;
import cn.com.jcgroup.service.enums.BusinessTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.service.BusinessService;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 业务相关
 *
 * @author LiuYong on 17/6/15 下午7:19.
 */
@RestController
@Validated
@RequestMapping(BUSINESS)
public class BusinessController {
    
    private static final Logger LOG = LoggerFactory.getLogger(BusinessController.class);

    @Value("${server.dev.mode}")
    private boolean serverDevMode;

    @Autowired
    private BusinessManage businessManage;
    @Autowired
    private BusinessService businessService;

    /**
     * 数据报表（出差）
     *
     * @author LiuYong
     */
    @RequestMapping(REPORT_TRAVEL)
    public ApiResult tripData(@RequestBody TravelDto travelDto) {
        Date start = DateUtil.parseDate(travelDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(travelDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        JSONObject jsonObject = businessManage.buildTravelData(travelDto.getDepart_code(),travelDto.getPage(),start, end);
        PageInfo pageInfo = (PageInfo) jsonObject.get("pageInfo");
        return new PageApiResult(pageInfo,jsonObject.get("data"));
    }

    /**
     * 数据报表（接待）
     *
     * @author LiuYong
     */
    @RequestMapping(REPORT_RECEPTION)
    public ApiResult receptionData(@RequestBody TravelDto travelDto) {
        Date start = DateUtil.parseDate(travelDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(travelDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        JSONObject jsonObject = businessManage.buildReceptionData(travelDto.getDepart_code(),travelDto.getPage(),start, end);
        PageInfo pageInfo = (PageInfo) jsonObject.get("pageInfo");
        return new PageApiResult(pageInfo,jsonObject.get("data"));
    }

    /**
     * 根据城市取前10条费用最高的数据
     *
     * @author LiuYong
     */
    @RequestMapping(MAP_TOPTEN)
    public ApiResult topTen(@RequestBody BusinessMapDto businessMapDto){
        Date start = DateUtil.parseDate(businessMapDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(businessMapDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        JSONObject jsonObject = businessManage.buildTopTen(businessMapDto.getCity_name(),start,end,businessMapDto.getDepart_code());
        return new ApiResult(jsonObject);
    }

    /**
     * 根据时间段和部门查询飞行线路
     *
     * @author LiuYong
     */
    @RequestMapping(MAP_LINE)
    public ApiResult mapLine(@RequestBody BusinessMapDto businessMapDto){
        Date start = DateUtil.parseDate(businessMapDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(businessMapDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        List<String> companyList = CompanyEnum.covertToList(businessMapDto.getDepart_code());
        if (companyList != null && !companyList.isEmpty()){
            return new ApiResult(businessService.findTravelRecepionLineByTime(start,end,companyList));
        }else{
            return new ApiResult(ResCodeEnum.INVALID_PARAM,"部门参数有误");
        }
    }

    /**
     * 按城市分组统计出差接待次数
     *
     * @author LiuYong
     */
    @RequestMapping(MAP_POINT)
    public ApiResult mapPoint(@RequestBody BusinessMapDto businessMapDto){
        Date start = DateUtil.parseDate(businessMapDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(businessMapDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        return new ApiResult(businessManage.buildTravelTotalTimes(start,end,businessMapDto.getDepart_code()));
    }

    /**
     * 出差分析
     *
     * @author LiuYong
     */
    @RequestMapping(TRAVEL)
    public ApiResult travel(@RequestBody BusinessMapDto businessMapDto,HttpSession session) {
        Date start = DateUtil.parseDate(businessMapDto.getStart_time(), "yyyy.MM");
        Date end = DateUtil.parseDate(businessMapDto.getEnd_time(), "yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        //获取用户所属部门
        String company = "0";
        if (!serverDevMode) {
            JSONObject jsonObject = (JSONObject) session.getAttribute(PlanbIdentifier.SESSION_USER);
            if (jsonObject == null) {
                LOG.error("[出差接待分析]用户未登录");
                return new ApiResult(ResCodeEnum.SESSION_EXPIRED);
            }
            company = jsonObject.getString("company");
            if (StringUtils.isBlank(company)) {
                LOG.error("[出差接待分析]用户所属部门为空");
                return new ApiResult(null, "用户所属部门为空");
            }
        }
        return new ApiResult(businessManage.buildTravelAndReception(start, end, company, BusinessTypeEnum.TRAVEL.getType()));
    }

    /**
     * 接待分析
     *
     * @author LiuYong
     */
    @RequestMapping(RECEPTION)
    public ApiResult reception(@RequestBody BusinessMapDto businessMapDto, HttpSession session) {
        Date start = DateUtil.parseDate(businessMapDto.getStart_time(), "yyyy.MM");
        Date end = DateUtil.parseDate(businessMapDto.getEnd_time(), "yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        //获取用户所属部门
        String company = "0";
        if(!serverDevMode){
            JSONObject jsonObject = (JSONObject) session.getAttribute(PlanbIdentifier.SESSION_USER);
            if(jsonObject == null){
                LOG.error("[出差接待分析]用户未登录");
                return new ApiResult(ResCodeEnum.SESSION_EXPIRED);
            }
            company = jsonObject.getString("company");
            if(StringUtils.isBlank(company)){
                LOG.error("[出差接待分析]用户所属部门为空");
                return new ApiResult(null,"用户所属部门为空");
            }
        }
        return new ApiResult(businessManage.buildTravelAndReception(start, end, company, BusinessTypeEnum.RECEPTION.getType()));
    }

}
