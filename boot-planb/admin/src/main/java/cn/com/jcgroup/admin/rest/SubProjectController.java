package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.ProjectCodeDto;
import cn.com.jcgroup.admin.dto.SubProEditDto;
import cn.com.jcgroup.admin.dto.SubProjectAbbrDto;
import cn.com.jcgroup.admin.manage.SubProjectManage;
import cn.com.jcgroup.service.service.SubProjectService;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.*;

/**
 * Description: 工程
 * User: sunchangqing
 * Date: 2017-06-14
 * Time: 上午9:47
 */
@RestController
@RequestMapping(AdminIdentifier.Url.SUB_PROJECT)
public class SubProjectController {

    private static final Logger LOG = LoggerFactory.getLogger(SubProjectController.class);

    @Autowired
    private SubProjectService subProjectService;
    @Autowired
    private SubProjectManage subProjectManage;

    /**
     * 工程删除
     */
    @RequestMapping(SUB_PROJECT_DELETE)
    public ApiResult subProjectDelete(@RequestBody SubProEditDto subProEditDto) {
        try {
            subProjectService.subProjectDelete(subProEditDto.getSub_pro_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "工程删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程删除失败");
    }

    /**
     * 工程查询
     */
    @RequestMapping(SUB_PROJECT_SEARCH)
    public ApiResult SubProjectSearch(@RequestBody SubProEditDto subProEditDto) {
        try {
            JSONArray jsonArray = subProjectManage.subProjectSearch(subProEditDto.getSub_pro_name());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程查询异常");
    }

    /**
     * 工程修改
     */
    @RequestMapping(SUB_PROJECT_UPDATE)
    public ApiResult SubProjectUpdate(@RequestBody SubProEditDto subProEditDto) {
        try {
            int res = subProjectService.subProjectUpdate(subProEditDto.getSub_pro_code(),
                    subProEditDto.getSub_pro_name(), subProEditDto.isIs_show(), subProEditDto.getTotal_money(),
                    subProEditDto.getFile_code());
            return new ApiResult(res);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程修改失败");
    }

    /**
     * 工程列表
     */
    @RequestMapping(SUB_PROJECT_LIST)
    public ApiResult subProjectList(@RequestBody ProjectCodeDto projectCodeDto) {
        try {
            JSONArray jsonArray = subProjectManage.subProjectListInPage(projectCodeDto.getPro_code(), projectCodeDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程列表展示错误");
    }

    /**
     * 新建工程
     */
    @RequestMapping(SUB_PROJECT_CREATE)
    public ApiResult subProjectCreate(@RequestBody SubProjectAbbrDto subProjectAbbrDto) {
        try {
            subProjectService.subProjectCreate(subProjectAbbrDto.getPro_code(),
                    subProjectAbbrDto.getSub_pro_code(),
                    subProjectAbbrDto.getSub_pro_name(),
                    subProjectAbbrDto.getTotal_money(),
                    subProjectAbbrDto.getFile_code());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "新建工程成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "新建工程失败");
    }
}
