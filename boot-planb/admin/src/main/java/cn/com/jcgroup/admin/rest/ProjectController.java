package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.ProjectCodeDto;
import cn.com.jcgroup.admin.dto.ProjectEditInfoDto;
import cn.com.jcgroup.admin.dto.ProjectInfoDto;
import cn.com.jcgroup.admin.dto.ProjectListProjectDto;
import cn.com.jcgroup.admin.manage.ProjectManage;
import cn.com.jcgroup.service.service.ProjectService;
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
 * @author LiuYong on 17/6/11 下午7:50.
 * @description 项目相关
 */
@RestController
@RequestMapping(AdminIdentifier.Url.PROJECT)
public class ProjectController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectManage projectManage;

    /**
     * 项目概况-修改
     */
    @RequestMapping(PROJECT_OVERVIEW_UPDATE)
    public ApiResult projectOverviewUpdate(@RequestBody ProjectEditInfoDto projectDto) {
        try {
            projectManage.editProjectOverviewByProCode(projectDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "项目概况修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目概况修改失败");
    }

    /**
     * 项目概况-查询
     */
    @RequestMapping(PROJECT_OVERVIEW_QUERY)
    public ApiResult projectOverviewQuery(@RequestBody ProjectInfoDto projectDto) {
        try {
            JSONObject jsonObject = projectManage.buildProjectOverviewByProCode(projectDto.getPro_code());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目概况查询异常");
    }

    /**
     * 项目删除
     */
    @RequestMapping(PROJECT_DELETE)
    public ApiResult projectDelete(@RequestBody ProjectListProjectDto projectListProjectDto) {
        try {
            projectService.deleteProject(projectListProjectDto.getPro_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "项目删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目删除失败");
    }

    /**
     * 项目查询
     */
    @RequestMapping(PROJECT_SEARCH)
    public ApiResult projectSearch(@RequestBody ProjectListProjectDto projectListProjectDto) {
        try {
            JSONArray jsonArray = projectManage.searchProject(projectListProjectDto.getPro_name());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目查询异常");
    }

    /**
     * 项目修改
     */
    @RequestMapping(PROJECT_UPDATE)
    public ApiResult projectUpdate(@RequestBody ProjectListProjectDto projectListProjectDto) {
        try {
            int res = projectService.updateProject(projectListProjectDto.getPro_abbr(),
                    projectListProjectDto.getPro_code(),
                    projectListProjectDto.getPro_name(),
                    projectListProjectDto.isIs_show());
            return new ApiResult(res);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目修改成功");
    }

    /**
     * 项目列表
     */
    @RequestMapping(PROJECT_LIST)
    public ApiResult projectList(@RequestBody  ProjectCodeDto projectCodeDto) {
        try {
            JSONArray jsonArray =  projectManage.queryProjectByProCode(projectCodeDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "项目列表出错");
    }


    /**
     * 项目新建
     */
    @RequestMapping(PROJECT_CREATE)
    public ApiResult projectCreate(@RequestBody ProjectListProjectDto projectListProjectDto) {
        try {
            String resCode = projectService.createProject(projectListProjectDto.getPro_abbr(),
                    projectListProjectDto.getPro_code(),
                    projectListProjectDto.getPro_name(),
                    projectListProjectDto.isIs_show());
            return new ApiResult(resCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "新建项目失败");
    }

}