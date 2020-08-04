package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.ASSET_COMPOSTION;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.CAPITAL_PROGRESS;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.CASH_FLOW;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.CASH_FLOW_HOVER;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.FINANCE_REPORT;
import cn.com.jcgroup.planb.dto.CashFlowDto;
import cn.com.jcgroup.planb.dto.FinanceReportDto;
import cn.com.jcgroup.planb.dto.ProjectDto;
import cn.com.jcgroup.planb.manage.FinanceProgressManage;
import cn.com.jcgroup.planb.manage.ProjectManage;
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

import javax.validation.Valid;
import java.util.Date;

/**
 * 资金进度相关接口
 *
 * @author LiuYong on 17/6/8 下午9:52.
 */
@RestController
@Validated
@RequestMapping(CAPITAL_PROGRESS)
public class FinanceProgressController {

    private static final Logger LOG = LoggerFactory.getLogger(FinanceProgressController.class);
    @Autowired
    private FinanceProgressManage financeProgressManage;

    @Autowired
    private ProjectManage projectManage;

    /**
     * 财务报表
     * @author LiuYong
     */
    @RequestMapping(FINANCE_REPORT)
    public ApiResult report(@RequestBody @Valid FinanceReportDto financeReportDto) {
        String reportTime = financeReportDto.getDate();
        Date startTime = DateUtil.parseDate(reportTime, "yyyy.MM");
        Date end = (Date) startTime.clone();
        end = DateUtil.getLastDay(end);
        JSONObject jsonObject = financeProgressManage.buildCashFlowReportByCompanyCodeAndType(financeReportDto.getCompany_code(),
                startTime,end, financeReportDto.getType());
        return new ApiResult(jsonObject);
    }

    /**
     * 资产形成
     *
     * @author LiuYong
     */
    @RequestMapping(ASSET_COMPOSTION)
    public ApiResult assetCompostion(@RequestBody @Valid ProjectDto projectDto) {
        JSONObject jsonObject = projectManage.buildAssetCompositionByProCode(projectDto.getProject_code());
        return new ApiResult(jsonObject);
    }

    /**
     * 资金流图表
     * @author LiuYong
     */
    @RequestMapping(CASH_FLOW)
    public ApiResult cashFlow(@RequestBody @Valid CashFlowDto cashFlowDto) {
        JSONObject jsonObject = financeProgressManage.buildMoneyByCompanyCodeAndReportTimeBetween(cashFlowDto.getCompany_code(), cashFlowDto.getType());
        return new ApiResult(jsonObject);
    }
    
    /**
     * 资金流图表明细
     * @author LiuYong  
     */
    @RequestMapping(CASH_FLOW_HOVER)
    public ApiResult cashFlowHover(@RequestBody @Valid CashFlowDto cashFlowDto) {
        JSONArray jsonArray = financeProgressManage.buildCashFlowHoverByCompanyCodeAndType(cashFlowDto.getCompany_code(),cashFlowDto.getType());
        return new ApiResult(jsonArray);
    }
}
