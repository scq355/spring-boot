package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_CREATE;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_DELETE;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_EDIT;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_LIST;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_OVERVIEW_EDIT;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_OVERVIEW_LIST;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_RZK_ADD;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_RZK_DELETE;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_RZK_QUERY;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_RZK_UPDATE;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_SEARCH;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_STAFF_ADD;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_STAFF_DELETE;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_STAFF_EDIT;
import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.COMPANY_STAFF_LIST;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.CompanyCodeDto;
import cn.com.jcgroup.admin.dto.CompanyDto;
import cn.com.jcgroup.admin.dto.CompanyEditDto;
import cn.com.jcgroup.admin.dto.CompanyInfoDto;
import cn.com.jcgroup.admin.dto.CompanyInfoEditDto;
import cn.com.jcgroup.admin.dto.CompanyStaffDto;
import cn.com.jcgroup.admin.dto.FinanceSummaryDto;
import cn.com.jcgroup.admin.dto.ProjectCodeDto;
import cn.com.jcgroup.admin.manage.CompanyManage;
import cn.com.jcgroup.service.service.CompanyService;
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
 * Description:合营公司相关
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午12:56
 */
@RestController
@RequestMapping(AdminIdentifier.Url.COMPANY)
public class CompanyController {
    
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyManage companyManage;
    @Autowired
    private CompanyService companyService;

    /**
     * 合营公司概况-查看
     */
    @RequestMapping(COMPANY_OVERVIEW_LIST)
    public ApiResult getCompanyInfo(@RequestBody CompanyInfoDto companyInfoDto) {
        try {
            JSONObject jsonObject = companyManage.getCompanyInfo(companyInfoDto.getCompany_code());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return  new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司概况查看异常");
    }

    /**
     * 合营公司概况-修改
     */
    @RequestMapping(COMPANY_OVERVIEW_EDIT)
    public ApiResult editCompanyInfo(@RequestBody CompanyInfoEditDto companyInfoEditDto) {
        try {
            companyManage.editCompanyInfo(companyInfoEditDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "合营公司概况修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司概况修改失败");
    }

    /**
     * 合营公司查询
     */
    @RequestMapping(COMPANY_SEARCH)
    public ApiResult companySearch(@RequestBody CompanyDto companyDto) {
        try {
            JSONArray jsonArray = companyManage.searchCompany(companyDto.getCompany_name());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司查询异常");
    }

    /**
     * 合营公司删除
     */
    @RequestMapping(COMPANY_DELETE)
    public ApiResult companyDelete(@RequestBody CompanyDto companyDto) {
        try {
            companyService.deleteCompany(companyDto.getCompany_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "合营公司删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司删除失败");
    }

    /**
     * 合营公司列表
     */
    @RequestMapping(COMPANY_LIST)
    public ApiResult companyList(@RequestBody ProjectCodeDto projectCodeDto) {
        try {
            JSONArray jsonArray = companyManage.listCompanyInPage(projectCodeDto.getPro_code(),
                    projectCodeDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司列表异常");
    }

    /**
     * 合营公司创建
     */
    @RequestMapping(COMPANY_CREATE)
    public ApiResult companyCreate(@RequestBody CompanyCodeDto companyCodeDto) {
        String result = companyService.createCompany(companyCodeDto.getCompany_name(),companyCodeDto.getCompany_code(),
                companyCodeDto.getPro_code(), companyCodeDto.isIs_show());
        if (result != null) {
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "合营公司创建成功");
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司创建失败");
    }

    /**
     * 合营公司编辑
     */
    @RequestMapping(COMPANY_EDIT)
    public ApiResult companyEdit(@RequestBody CompanyEditDto companyEditDto) {
        try {
            JSONObject res = companyService.editCompany(companyEditDto.getCompany_code(),
                    companyEditDto.getCompany_name(), companyEditDto.isIs_show());
            return new ApiResult(res);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "合营公司更新异常");
    }


    /**
     * 合营公司-人员-列表
     * @author LiuYong  
     */
    @RequestMapping(COMPANY_STAFF_LIST)
    public ApiResult stuffList(@RequestBody Map<String,String> map) {
        String companyCode = map.get("companyCode");
        if(StringUtils.isBlank(companyCode)){
            return new ApiResult(ResCodeEnum.INVALID_PARAM,"合营公司编号为空");
        }
        return new ApiResult(companyManage.buildCompanyStaff(companyCode));
    }

    /**
     * 合营公司-人员-删除
     * @author LiuYong
     */
    @RequestMapping(COMPANY_STAFF_DELETE)
    public ApiResult stuffDelete(@RequestBody Map<String,String> map) {
        String companyCode = map.get("companyCode");
        String id = map.get("id");
        if(StringUtils.isBlank(companyCode) || StringUtils.isBlank(id)){
            return new ApiResult(ResCodeEnum.INVALID_PARAM,"id或companyCode为空");
        }
        boolean bool = companyService.deleteStaffRelation(companyCode,Integer.parseInt(id));
        if(!bool){
            LOG.info("[合营公司-人员-删除]失败,info={}", JSON.toJSONString(map));
        }
        return new ApiResult(null);
    }

    /**
     * 合营公司-人员-新增
     * @author LiuYong
     */
    @RequestMapping(COMPANY_STAFF_ADD)
    public ApiResult stuffAdd(@RequestBody CompanyStaffDto companyStaffDto) {
        Map<String,Object> map = companyManage.initOrUpdate(companyStaffDto,"add");
        if(map ==null){
            LOG.info("[合营公司-人员-新增]失败,info={}", JSON.toJSONString(companyStaffDto));
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"添加失败");
        }
        Integer id = (Integer) map.get("id");
        if(id == null){
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"添加失败");
        }
        return new ApiResult(map);
    }

    /**
     * 合营公司-人员-修改
     * @author LiuYong
     */
    @RequestMapping(COMPANY_STAFF_EDIT)
    public ApiResult stuffEdit(@RequestBody CompanyStaffDto companyStaffDto) {
        Map<String,Object> map = companyManage.initOrUpdate(companyStaffDto,"update");
        if(map ==null){
            LOG.info("[合营公司-人员-修改]失败,info={}", JSON.toJSONString(companyStaffDto));
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"修改失败");
        }
        boolean flag = (Boolean) map.get("flag");
        if(!flag){
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"修改失败");
        }
        return new ApiResult(null);
    }


    /**
     * 合营公司-融资款-查询
     * @author LiuYong
     */
    @RequestMapping(COMPANY_RZK_QUERY)
    public ApiResult rzkQuery(@RequestBody Map<String,String> map) {
        String companyCode = map.get("companyCode");
        if(StringUtils.isBlank(companyCode)){
            return new ApiResult(ResCodeEnum.INVALID_PARAM,"合营公司编号为空");
        }
        JSONArray jsonArray = companyManage.buildFinanceSummary(companyCode);
        return new ApiResult(jsonArray);
    }

    /**
     * 合营公司-融资款-编辑
     * @author LiuYong
     */
    @RequestMapping(COMPANY_RZK_UPDATE)
    public ApiResult rzkUPDATE(@RequestBody FinanceSummaryDto financeSummaryDto) {
        Map<String,Object> map = companyManage.initOrUpdateFinanceSummary(financeSummaryDto,"update");
        if(map ==null){
            LOG.info("[合营公司-融资款-编辑]失败,info={}", JSON.toJSONString(financeSummaryDto));
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"修改失败");
        }
        boolean flag = (Boolean) map.get("flag");
        if(!flag){
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"修改失败");
        }
        return new ApiResult(null);
    }

    /**
     * 合营公司-融资款-删除
     * @author LiuYong
     */
    @RequestMapping(COMPANY_RZK_DELETE)
    public ApiResult rzkDELETE(@RequestBody Map<String,Integer> map) {
        boolean bool = companyService.deleteFinanceSummary(map.get("id"));
        if(!bool){
            LOG.info("[合营公司-融资款-删除]失败,info={}", JSON.toJSONString(map));
        }
        return new ApiResult(null);
    }

    /**
     * 合营公司-融资款-添加
     * @author LiuYong
     */
    @RequestMapping(COMPANY_RZK_ADD)
    public ApiResult rzkADD(@RequestBody FinanceSummaryDto financeSummaryDto) {
        Map<String,Object> map = companyManage.initOrUpdateFinanceSummary(financeSummaryDto,"add");
        if(map ==null){
            LOG.info("[合营公司-融资款-添加]失败,info={}", JSON.toJSONString(financeSummaryDto));
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"添加失败");
        }
        Integer id = (Integer) map.get("id");
        if(id == null){
            return new ApiResult(ResCodeEnum.SERVER_ERROR,"添加失败");
        }
        return new ApiResult(map);
    }

}
