package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.common.PageApiResult;
import cn.com.jcgroup.planb.common.PageInfo;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.ANNUAL_SALARY;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.DEPARTMENT_LIST;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.STAFF_BASIC_INFO;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.STAFF_FLOW;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.dto.PersonnelDto;
import cn.com.jcgroup.planb.manage.PersonnelManage;
import cn.com.jcgroup.service.service.PersonnelFlowService;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description 人员流动
 * @author sunchangqing
 * @date 2017-06-12 19:13:20
 */
@Validated
@RestController
public class PersonnelFlowController {

    private static Logger LOG = LoggerFactory.getLogger(PersonnelFlowController.class);

    @Autowired
    PersonnelFlowService personnelFlowService;
    @Autowired
    PersonnelManage personnelManage;

    /**
     * 人员流动信息
     *
     * @author LiuYong
     */
    @RequestMapping(STAFF_FLOW)
    public ApiResult stuffFlow(@RequestBody PersonnelDto personnelDto) {
        Date start = DateUtil.parseDate(personnelDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(personnelDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        JSONObject jsonObject = personnelManage.buildStaffFlow(start, end, personnelDto.getPage(), personnelDto.getDepart_code());
        PageInfo pageInfo = (PageInfo) jsonObject.get("pageInfo");
        return new PageApiResult(pageInfo,jsonObject.getJSONObject("data"));
    }

    /**
     * 人员流动-薪资年限
     */
    @RequestMapping(ANNUAL_SALARY)
    public ApiResult annualSalary(@RequestBody PersonnelDto personnelDto) {
        String departCode = personnelDto.getDepart_code();
        JSONObject jsonObject = personnelManage.buildTimeSalary(departCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 人员流动-基本信息
     */
    @RequestMapping(STAFF_BASIC_INFO)
    public ApiResult basicInfo(@RequestBody PersonnelDto personnelDto) {
        try {
            String departCode = personnelDto.getDepart_code();
            JSONObject jsonObject = personnelManage.buildBasicInfo(departCode);
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR);
    }

    /**
     * 部门列表
     */
    @RequestMapping(DEPARTMENT_LIST)
    public ApiResult departmentList() {
        JSONArray jsonArray = personnelFlowService.queryDepartments();
        return new ApiResult(jsonArray);
    }
}
