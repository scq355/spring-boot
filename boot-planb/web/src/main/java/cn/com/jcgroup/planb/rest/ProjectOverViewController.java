package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_BANNER;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_BASIC_INFO;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_COMPANY_INFO;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_COMPANY_LIST;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_ENCOURAGE;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_FILE;
import cn.com.jcgroup.planb.dto.CompanyDto;
import cn.com.jcgroup.planb.dto.ProjectDto;
import cn.com.jcgroup.planb.manage.CompanyManage;
import cn.com.jcgroup.planb.manage.ProjectManage;
import cn.com.jcgroup.planb.manage.ProjectOverViewManage;
import cn.com.jcgroup.service.service.ProjectService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目概览
 * @author LiuYong on 17/6/7 下午3:21.
 */
@RestController
@Validated
@RequestMapping(PROJECT)
public class ProjectOverViewController {

    @Autowired
    private CompanyManage companyManage;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectOverViewManage projectOverViewManage;
    @Autowired
    private ProjectManage projectManage;

    /**
     * 激励情况
     */
    @RequestMapping(PROJECT_ENCOURAGE)
    public ApiResult encourageInfo(@RequestBody ProjectDto projectDto) {
        String projectCode = projectDto.getProject_code();
        JSONObject jsonObject = projectManage.buildEncourageInfoByProCode(projectCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 项目基本信息
     */
    @RequestMapping(PROJECT_BASIC_INFO)
    public ApiResult projectBasicInfo(@RequestBody ProjectDto projectDto) {
        String projectCode = projectDto.getProject_code();
        JSONObject jsonObject = projectManage.buildProjectInfoByProCode(projectCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 合营公司详细信息
     */
    @RequestMapping(PROJECT_COMPANY_INFO)
    public ApiResult companyInfo(@RequestBody CompanyDto companyDto) {
        String companyCode = companyDto.getCompany_code();
        JSONObject jsonObject = companyManage.buildCompanyInfoByCompanyCode(companyCode);
        return new ApiResult(jsonObject);
    }

    /**
     * 合营公司列表
     */
    @RequestMapping(PROJECT_COMPANY_LIST)
    public ApiResult companyList(@RequestBody ProjectDto projectDto) {
        String projectCode = projectDto.getProject_code();
        JSONArray jsonArray = projectOverViewManage.buildCompanyListByProCode(projectCode);
        return new ApiResult(jsonArray);
    }

    /**
     * 获取文件列表
     */
    @RequestMapping(PROJECT_FILE)
    public ApiResult fileList(@RequestBody ProjectDto projectDto) {
        String projectCode = projectDto.getProject_code();
        JSONArray jsonArray = projectOverViewManage.buildEncourageFileByProCode(projectCode);
        return new ApiResult(jsonArray);
    }

    /**
     * 项目Banner图
     */
    @RequestMapping(PROJECT_BANNER)
    public ApiResult getBannerList(@RequestBody ProjectDto projectDto) {
        String projectCode = projectDto.getProject_code();
        JSONArray jsonArray = projectService.findBannerListByProjectCode(projectCode);
        return new ApiResult(jsonArray);
    }
}
