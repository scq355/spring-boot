package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.common.PageApiResult;
import cn.com.jcgroup.planb.common.PageInfo;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_MAP;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_MILESTONE;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_OVERVIEW;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_STATISTIC;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_STATUS_COUNT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_TOTAL_ASSET_PERCENT;
import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Url.PROJECT_TOTAL_ENCOURAGE;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.dto.MileStoneDto;
import cn.com.jcgroup.planb.dto.TypeDto;
import cn.com.jcgroup.planb.manage.ProjectManage;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.service.SecurityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 项目相关服务
 *
 * @author LiuYong on 17/5/30 下午6:33.
 */
@RestController
@Validated
@RequestMapping(PROJECT)
public class ProjectContoller {

    @Autowired
    private ProjectManage projectManage;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/menu")
    public ApiResult menuInfo(@NotNull String username, @NotNull String projectCode) {
        String menu = securityService.hasProjectAuth(projectCode, username);
        if (StringUtils.isEmpty(menu)) {
            return new ApiResult(ResCodeEnum.NOT_ENOUGH_AUTHORITY, null);
        }
        JSONArray jsonArray = JSON.parseArray(menu);
        JSONArray firstMenuArray = new JSONArray();
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            jsonObject.get
        }
        JSONObject firstMenu = new JSONObject();
//        firstMenu.put("")

        return new ApiResult();
    }

    /**
     * 项目地图
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_MAP)
    public ApiResult projectMap() {
        JSONArray provinceArray = projectManage.buildProjectNumberByProvinceGroup();
        if (provinceArray != null && !provinceArray.isEmpty()) {
            int size = provinceArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = (JSONObject) provinceArray.get(i);
                JSONArray projectInfo = projectManage.buildProjectInfoByByProvince(jsonObject.getString("province"));
                jsonObject.put("project_info", projectInfo);
            }
        }
        return new ApiResult(provinceArray);
    }

    /**
     * 项目概览
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_OVERVIEW)
    public ApiResult projectOverview() {
        JSONObject jsonObject = projectManage.buildInfoByDepartmentAndStatusGroup();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            JSONObject statusJsonObject = projectManage.buildProjectScaleByStatusGroup();
            jsonObject.put("status_scale", statusJsonObject);
            //设置横坐标名称
            jsonObject.put("xAxis", CompanyEnum.getCompanyName("0"));
        }
        return new ApiResult(jsonObject);
    }

    /**
     * 按状态进行项目个数统计
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_STATUS_COUNT)
    public ApiResult statusCount() {
        JSONArray jsonArray = projectManage.buildProjectNumberByStatusGroup();
        return new ApiResult(jsonArray);
    }

    /**
     * 激励情况
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_TOTAL_ENCOURAGE)
    public ApiResult totalEncourage() {
        JSONObject jsonObject = projectManage.buildEncourageNumberByCompanyAndLevelGroup();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            JSONObject levelJsonObject = projectManage.buildEncourageNumberByLevelGroup();
            jsonObject.put("level_count", levelJsonObject);
            //设置横坐标名称
            jsonObject.put("depart_name", CompanyEnum.getCompanyName("0"));
        }
        return new ApiResult(jsonObject);
    }

    /**
     * 里程碑
     *
     * @author LiuYong
     */
    @RequestMapping(value = PROJECT_MILESTONE)
    public PageApiResult milestone(@RequestBody @Valid MileStoneDto projectDto) {
        JSONObject jsonObject = projectManage.buildMileStoneByProjectCode(projectDto.getProject_code(),projectDto.getPage());
        PageInfo pageInfo = null;
        JSONArray jsonArray = null;
        if(jsonObject != null && !jsonObject.isEmpty()){
            pageInfo = (PageInfo) jsonObject.get("pageInfo");
            jsonArray = jsonObject.getJSONArray("data");
            int size = jsonArray.size();
            for(int i=0;i<size;i++){
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                String fileCode = tempJsonObject.getString("file_code");
                //组装文件字段
                JSONArray fieldArray = projectManage.buildFileFiledByFileCode(fileCode);
                tempJsonObject.put("file_field",fieldArray);
            }
        }
        return new PageApiResult(pageInfo,jsonArray);
    }

    /**
     * 各部门资产形成
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_TOTAL_ASSET_PERCENT)
    public ApiResult totalAssetPercent() {
        JSONArray jsonArray = projectManage.buildTotalAssetPercent();
        return new ApiResult(jsonArray);
    }

    /**
     * 机构募集&私募募集&今年新增
     *
     * @author LiuYong
     */
    @RequestMapping(PROJECT_STATISTIC)
    public ApiResult statistic(@RequestBody @Valid TypeDto type) {
        JSONObject jsonObject = projectManage.buildStatistic(type.getType());
        return new ApiResult(jsonObject);
    }

}
