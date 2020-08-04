package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.AgencyCodeDto;
import cn.com.jcgroup.admin.dto.CompanyInfoDto;
import cn.com.jcgroup.admin.dto.FinanceAgencyCodeDto;
import cn.com.jcgroup.admin.manage.AgencyManage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.*;

/**
 * Description:机构/金融机构/其他
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午3:50
 */
@RestController
@RequestMapping(AdminIdentifier.Url.COMPANY)
public class AgencyController {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private AgencyManage agencyManage;

    /**
     * 其他-列表
     */
    @RequestMapping(COMPANY_OTHER_LIST)
    public ApiResult otherList(@RequestBody CompanyInfoDto companyInfoDto) {
        try {
            JSONArray jsonArray = agencyManage.buildOtherListByCompanyCode(companyInfoDto.getCompany_code());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "其他列表显示异常");
    }

    /**
     * 其他-编辑
     */
    @RequestMapping(COMPANY_OTHER_EDIT)
    public ApiResult otherEdit(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.otherEdit(agencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "其他机构更新成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "其他机构更新失败");
    }

    /**
     * 其他-添加
     */
    @RequestMapping(COMPANY_OTHER_ADD)
    public ApiResult otherAdd(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.otherAdd(agencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "其他机构添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "其他机构添加失败");
    }

    /**
     * 其他-删除
     */
    @RequestMapping(COMPANY_OTHER_DELETE)
    public ApiResult otherDelete(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.deleteOther(agencyCodeDto.getAgency_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "吉他机构删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "其他机构删除失败");
    }


    /**
     * 其他-单条-显示
     */
    @RequestMapping(COMPANY_OTHER_SHOW)
    public ApiResult otherInfoShow(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            JSONObject jsonObject = agencyManage.buildAgencyInfoShow(agencyCodeDto.getAgency_code());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "其他机构信息显示异常");
    }

    /**
     * 机构-单条-显示
     */
    @RequestMapping(COMPANY_AGENCY_SHOW)
    public ApiResult agencyInfoShow(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            JSONObject jsonObject = agencyManage.buildAgencyInfoShow(agencyCodeDto.getAgency_code());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "机构信息显示异常");
    }

    /**
     * 金融机构-单条-显示
     */
    @RequestMapping(COMPANY_FINANCE_AGENCY_SHOW)
    public ApiResult financeAgencyInfoShow(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            JSONObject jsonObject = agencyManage.buildFinanceAgencyInfoShow(agencyCodeDto.getAgency_code());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "金融机构信息显示异常");
    }


    /**
     * 机构编辑
     */
    @RequestMapping(COMPANY_AGENCY_EDIT)
    public ApiResult agencyEdit(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.agencyUpdate(agencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "机构更新成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "机构更新失败");
    }

    /**
     * 金融机构编辑
     */
    @RequestMapping(COMPANY_FINANCE_AGENCY_EDIT)
    public ApiResult financeAgencyEdit(@RequestBody FinanceAgencyCodeDto financeAgencyCodeDto) {
        try {
            agencyManage.financeAgencyEdit(financeAgencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "金融机构更新成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "金融机构更新失败");
    }

    /**
     * 机构添加
     */
    @RequestMapping(COMPANY_AGENCY_ADD)
    public ApiResult agencyAdd(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.agencyAdd(agencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "机构添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "机构添加失败");
    }

    /**
     * 金融机构添加
     */
    @RequestMapping(COMPANY_FINANCE_AGENCY_ADD)
    public ApiResult financeAgencyAdd(@RequestBody FinanceAgencyCodeDto financeAgencyCodeDto) {
        try {
            agencyManage.financeAgencyAdd(financeAgencyCodeDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "金融机构添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "金融机构添加失败");
    }

    /**
     * 机构列表
     */
    @RequestMapping(COMPANY_AGENCY_LIST)
    public ApiResult agencyList(@RequestBody CompanyInfoDto companyInfoDto) {
        try {
            JSONArray resArray = agencyManage.buildAgencyListByCompanyCode(companyInfoDto.getCompany_code());
            return new ApiResult(resArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "机构列表显示异常");
    }

    /**
     * 金融机构列表
     */
    @RequestMapping(COMPANY_FINANCE_AGENCY_LIST)
    public ApiResult financeAgencyList(@RequestBody CompanyInfoDto companyInfoDto) {
        try {
            JSONArray resArray = agencyManage.buildFinanceAgencyListByCompanyCode(companyInfoDto.getCompany_code());
            return new ApiResult(resArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "金融机构列表显示异常");
    }

    /**
     * 金融机构删除
     */
    @RequestMapping(COMPANY_FINANCE_AGENCY_DELETE)
    public ApiResult financeAgencyDelete(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.deleteFinanceAgency(agencyCodeDto.getAgency_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "金融机构删除失败");
    }

    /**
     * 机构删除
     */
    @RequestMapping(COMPANY_AGENCY_DELETE)
    public ApiResult agencyDelete(@RequestBody AgencyCodeDto agencyCodeDto) {
        try {
            agencyManage.deleteAgency(agencyCodeDto.getAgency_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, " 机构删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "机构删除失败");
    }
}
