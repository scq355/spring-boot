package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.AGENCY_AND_OTHER;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.AGENCY_LIST;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.FINANCE_AGENCY;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.FINANCE_PRIVATE_FUND;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.FINANCE_SUMMARY;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT;
import cn.com.jcgroup.planb.dto.AgencyDto;
import cn.com.jcgroup.planb.dto.AgencyListDto;
import cn.com.jcgroup.planb.dto.CompanyDto;
import cn.com.jcgroup.planb.dto.FinanceAgencyDto;
import cn.com.jcgroup.planb.dto.FundDto;
import cn.com.jcgroup.planb.manage.FinanceDetailManage;
import cn.com.jcgroup.service.service.FinanceDetailService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(PROJECT)
public class FinanceDetailController {

    @Autowired
    private FinanceDetailService financeDetailService;
    @Autowired
    private FinanceDetailManage financeDetailManage;

    /**
     * 私募数据
     * @param fundDto
     * @return
     */
    @RequestMapping(FINANCE_PRIVATE_FUND)
    public ApiResult privateFund(@RequestBody FundDto fundDto) {
        String fundCode = fundDto.getFund_code();
        JSONObject jsonObject = financeDetailManage.buildPrivateFunfByFundCode(fundCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 金融机构
     * @param financeAgencyDto
     * @return
     */
    @RequestMapping(FINANCE_AGENCY)
    public ApiResult financeAgency(@RequestBody FinanceAgencyDto financeAgencyDto) {
        String financeAgencyCode = financeAgencyDto.getFinance_agency_code();
        JSONObject jsonObject = financeDetailManage.buildFinanceAgency(financeAgencyCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 汇总
     * @param companyDto
     * @return
     */
    @RequestMapping(FINANCE_SUMMARY)
    public ApiResult financeSummary(@RequestBody CompanyDto companyDto) {
        String companyCode = companyDto.getCompany_code();
        JSONArray jsonArray = financeDetailService.findFinanceSummary(companyCode);
        return new ApiResult(jsonArray);
    }

    /**
     * 机构列表
     */
    @RequestMapping(AGENCY_LIST)
    public ApiResult agencyList(@RequestBody AgencyListDto agencyDto) {
        String companyCode = agencyDto.getCompany_code();
        String[] typeArray = agencyDto.getType();
        JSONArray jsonArray = financeDetailManage.buildAgencyList(companyCode, typeArray);
        return new ApiResult(jsonArray);
    }

    /**
     * 机构&&其它
     */
    @RequestMapping(AGENCY_AND_OTHER)
    public ApiResult otherAndAgency(@RequestBody AgencyDto agencyDto) {
        String agencyCode = agencyDto.getAgency_code();
        String type = agencyDto.getType();
        JSONObject jsonObject = financeDetailManage.buildAgencyByAgencyCodeAndType(agencyCode, type);
        return new ApiResult(jsonObject);
    }
}
