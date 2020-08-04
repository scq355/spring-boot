package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.EncourageDto;
import cn.com.jcgroup.admin.dto.EncourageFileDto;
import cn.com.jcgroup.admin.dto.ProjectInfoDto;
import cn.com.jcgroup.admin.manage.EncourageManage;
import cn.com.jcgroup.service.service.EncourageService;
import cn.com.jcgroup.service.util.NumberUtil;
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
 * Description:激励，激励文件
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午1:53
 */
@RestController
@RequestMapping(AdminIdentifier.Url.PROJECT)
public class EncourageController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private EncourageService encourageService;

    @Autowired
    private EncourageManage encourageManage;

    /**
     * 激励文件-列表
     */
    @RequestMapping(ENCOURAGE_FILE_LIST)
    public ApiResult encourageFileSearch(@RequestBody ProjectInfoDto projectDto) {
        try {
            JSONArray jsonArray = encourageManage.buildEncourageFileByProCode(projectDto.getPro_code(), projectDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励文件列表显示异常");
    }

    /**
     * 激励文件-修改
     */
    @RequestMapping(ENCOURAGE_FILE_UPDATE)
    public ApiResult encourageFileUpdate(@RequestBody EncourageFileDto encourageFileDto) {
        try {
            encourageManage.updateEncourageFile(encourageFileDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "激励文件修改成功");
        } catch (Exception e){
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励文件修改异常");
    }

    /**
     * 激励文件-上传
     */
    @RequestMapping(ENCOURAGE_FILE_UPLOAD)
    public ApiResult encourageFileUpload(@RequestBody ProjectInfoDto projectDto) {
        return new ApiResult();
    }


    /**
     * 激励情况-添加
     */
    @RequestMapping(ENCOURAGE_ADD)
    public ApiResult encourageAdd(@RequestBody EncourageDto encourageDto) {
        try {
            encourageManage.encourageAdd(encourageDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "激励情况添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励情况添加失败");
    }

    /**
     * 激励情况-删除
     */
    @RequestMapping(ENCOURAGE_DELETE)
    public ApiResult encourageDelete(@RequestBody EncourageDto encourageDto) {
        try {
            encourageManage.encourageDelete(encourageDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励情况删除失败");
    }

    /**
     * 激励情况-列表
     */
    @RequestMapping(ENCOURAGE_LIST)
    public ApiResult encourageList(@RequestBody EncourageDto encourageDto) {
        try {
            JSONObject jsonObject = encourageManage.encourageList(encourageDto.getPro_code(), encourageDto.getPage());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR ,"激励情况列表异常");
    }

    /**
     * 激励情况-查询
     */
    @RequestMapping(ENCOURAGE_QUERY)
    public ApiResult encourageQuery(@RequestBody EncourageDto encourageDto) {
        try {
            JSONArray jsonArray = encourageService.queryEncourageByProCode(encourageDto.getPro_code(),
                    encourageDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励情况查询异常");
    }

    /**
     * 激励情况（总金额）-修改
     */
    @RequestMapping(ENCOURAGE_TOTAL_UPDATE)
    public ApiResult encourageRecodeUpdate(@RequestBody EncourageDto encourageDto) {
        //以万为单位
        try {
            encourageManage.encourageTotalUpdate(encourageDto.getPro_code(),
                    encourageDto.getLevel(),NumberUtil.convertToPointWithUnit(encourageDto.getEncourage_money(),10000));
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "激励情况（总金额）修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励情况(总金额)修改失败");
    }

    /**
     * 激励情况（记录）-修改
     */
    @RequestMapping(ENCOURAGE_RECORD_UPDATE)
    public ApiResult encourageTotalUpdate(@RequestBody EncourageDto encourageDto) {
        try {
            encourageManage.encourageUpdate(encourageDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "激励情况(记录)修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "激励情况（记录）修改失败");
    }
}