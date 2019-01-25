package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.dto.FileDto;
import cn.com.jcgroup.admin.dto.FileFieldDto;
import cn.com.jcgroup.admin.dto.ImageDto;
import cn.com.jcgroup.admin.dto.ProjectInfoDto;
import cn.com.jcgroup.admin.manage.ProjectDocManage;
import cn.com.jcgroup.service.service.ProjectDocService;
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
 * Description:项目文档
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 上午11:51
 */
@RestController
@RequestMapping(AdminIdentifier.Url.DOCUMENT)
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectDocService projectDocService;
    @Autowired
    private ProjectDocManage projectDocManage;

    /**
     * 文档库-列表
     */
    @RequestMapping(DOCUMENT_DOC_LIST)
    public ApiResult documentDocList(@RequestBody FileDto fileDto) {
        JSONArray jsonArray = projectDocManage.listFile(fileDto.getPro_code(), fileDto.getPage());
        return new ApiResult(jsonArray);
    }

    /**
     * 文档库-导出
     */
    @RequestMapping(DOCUMENT_DOC_EXPORT)
    public ApiResult documentDocExport(@RequestBody ProjectInfoDto projectDto) {
        return new ApiResult();
    }

    /**
     * 文档详情-修改
     */
    @RequestMapping(DOCUMENT_DOC_DETAIL_UPDATE)
    public ApiResult documentDetailUpdate(@RequestBody FileFieldDto fileFieldDto) {
        return new ApiResult();
    }

    /**
     * 文档详情-查询
     */
    @RequestMapping(DOCUMENT_DOC_DETAIL_QUERY)
    public ApiResult documentDetailQuery(@RequestBody FileDto fileDto) {
        JSONObject jsonObject = projectDocService.queryFileInfoByProCode(fileDto.getFile_code());
        return new ApiResult(jsonObject);
    }

    /**
     * 图片库-查询
     */
    @RequestMapping(DOCUMENT_PHOTO_LIST)
    public ApiResult documenPhotoList(@RequestBody ProjectInfoDto projectDto) {
        JSONArray jsonArray = projectDocManage.queryImagesByProCode(projectDto.getPro_code());
        return new ApiResult(jsonArray);
    }

    /**
     * 图片库-修改
     */
    @RequestMapping(DOCUMENT_POTO_UPDATE)
    public ApiResult documenPhotoUpdate(@RequestBody ImageDto imageDto) {
        return new ApiResult();
    }

}
