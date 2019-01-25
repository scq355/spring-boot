package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.common.PageApiResult;
import cn.com.jcgroup.planb.common.PageInfo;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.ACCOUNT_PAYABLE;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.ACCOUNT_PAYFOR;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.MONTHLY_ACCOUNT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PAID_FLOW;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.SUB_PROJECT_LIST;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.SUB_PROJECT_PROCESS;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.TOTAL_PAID;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.dto.ProjectDto;
import cn.com.jcgroup.planb.dto.SubProjectDto;
import cn.com.jcgroup.planb.dto.SubProjectPaidItemDto;
import cn.com.jcgroup.planb.dto.SubProjectProcessDto;
import cn.com.jcgroup.planb.manage.SubProjectManage;
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
 * 工程进度
 */
@RestController
@Validated
@RequestMapping(PROJECT)
public class SubProjectProcessController {

    private static final Logger LOG = LoggerFactory.getLogger(SubProjectProcessController.class);

    @Autowired
    private SubProjectManage subProjectManage;

    /**
     * 已付资金流水
     * @return
     */
    @RequestMapping(PAID_FLOW)
    public ApiResult subProjectPaidItem(@RequestBody SubProjectPaidItemDto paidItemDto) {
        Date start = DateUtil.parseDate(paidItemDto.getStart_time(),"yyyy.MM");
        Date end = DateUtil.parseDate(paidItemDto.getEnd_time(),"yyyy.MM");
        if (start == null || end == null) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "日期格式必须为yyyy.MM");
        }
        //将结束日期设置为最后一天
        end = DateUtil.getLastDay(end);
        if (end.compareTo(start) == -1) {
            return new ApiResult(ResCodeEnum.INVALID_PARAM, "开始日期必须小于结束日期");
        }
        String subProCode = paidItemDto.getSub_project_code();
        JSONArray jsonArray = subProjectManage.buildPaidItemBySubProCode(subProCode, start, end);
        return new ApiResult(jsonArray);
    }

    /**
     * 工程列表
     * @param projectDto
     * @return
     */
    @RequestMapping(SUB_PROJECT_LIST)
    public ApiResult subProjectist(@RequestBody ProjectDto projectDto) {
        return new ApiResult(subProjectManage.buildSubProjectByCompanyCode(projectDto.getProject_code()));
    }

    /**
     * 月度台账
     * @param subProjectDto
     * @return
     */
    @RequestMapping(MONTHLY_ACCOUNT)
    public ApiResult monthPaper(@RequestBody SubProjectDto subProjectDto) {
        String subProCode = subProjectDto.getSub_project_code();
        JSONArray jsonArray = subProjectManage.buidMonthPaperBySubProCode(subProCode);
        return new ApiResult(jsonArray);
    }

    /**
     * 应收台账
     * @param subProjectDto
     * @return
     */
    @RequestMapping(ACCOUNT_PAYABLE)
    public ApiResult billPayAble(@RequestBody SubProjectDto subProjectDto) {
        String subProCode = subProjectDto.getSub_project_code();
        JSONObject jsonObject = subProjectManage.buildPayableBySubProCode(subProCode,subProjectDto.getPage());
        if(jsonObject != null && !jsonObject.isEmpty()){
            return new PageApiResult((PageInfo) jsonObject.get("pageInfo"),jsonObject.get("data"));
        }
        return new ApiResult(null);
    }

    /**
     * 应付台账
     * @pram subProjectDto
     * @return
     */
    @RequestMapping(ACCOUNT_PAYFOR)
    public ApiResult billPayFor(@RequestBody SubProjectDto subProjectDto) {
        String code = subProjectDto.getSub_project_code();
        JSONObject jsonObject = subProjectManage.buildPayforBySubProCode(code,subProjectDto.getPage());
        if(jsonObject != null && !jsonObject.isEmpty()){
            return new PageApiResult((PageInfo) jsonObject.get("pageInfo"),jsonObject.get("data"));
        }
        return new ApiResult(null);
    }

    /**
     * 完成情况
     * @return
     */
    @RequestMapping(SUB_PROJECT_PROCESS)
    public ApiResult subProjectProcess(@RequestBody SubProjectProcessDto subProjectDto) {
        JSONObject jsonObject = subProjectManage.buildSubProjectMoneySum(subProjectDto.getSub_project_code(),subProjectDto.getDate());
        return new ApiResult(jsonObject);
    }

    /**
     * 已付资金统计
     * @return
     */
    @RequestMapping(TOTAL_PAID)
    public ApiResult totalPaid(@RequestBody SubProjectDto subProjectDto) {
        JSONObject jsonObject = subProjectManage.buildTotalMoneyByCodeAndTime(subProjectDto.getSub_project_code());
        return new ApiResult(jsonObject);
    }
    
}
