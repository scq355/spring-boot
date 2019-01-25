package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbFile;
import cn.com.jcgroup.service.domain.PbFileField;
import cn.com.jcgroup.service.domain.PbImage;
import cn.com.jcgroup.service.repositories.PbFileFieldReponsitory;
import cn.com.jcgroup.service.repositories.PbFileReponsitory;
import cn.com.jcgroup.service.repositories.PbImageRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 项目文档相关
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午6:24
 */
@Service
public class ProjectDocService {

    @Autowired
    private PbImageRepository pbImageRepository;
    @Autowired
    private PbFileFieldReponsitory pbFileFieldReponsitory;
    @Autowired
    private PbFileReponsitory pbFileReponsitory;

    //TODO 未完待续
    public boolean saveOrUpdateFileField(String fieldName, String fieldValue, int id, String isShow, String prpoCode) {
        return false;
    }

    /**
     * 根据项目编码统计文档数
     */
    public int queryFileNumByProCode(String proCode) {
        if (proCode != null) {
            Integer count = pbFileReponsitory.countAllByProCode(proCode);
            return count == null ? 0 : count;
        }
        return 0;
    }

    /**
     * 文档库列表
     */
    public JSONArray querryFileByProCodeInPage(String proCdde, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC,"proCode"));
        List<PbFile> fileList = pbFileReponsitory.findByFileCodeInPage(proCdde, pageable);
        if (fileList != null && !(fileList.isEmpty())) {
            for (PbFile file : fileList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("file_code", file.getFileCode());
                jsonObject.put("file_name", file.getFileName());
                jsonObject.put("file_type", file.getFileType());
                jsonObject.put("pro_node", "========");
                jsonObject.put("pro_stage", "========");
                jsonObject.put("sign_time", "========");
                jsonObject.put("work_item", "========");
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 文档详情-查询
     */
    public JSONObject queryFileInfoByProCode(String fileCode) {
        JSONObject resObj = new JSONObject();
        List<PbFileField> fileFields = pbFileFieldReponsitory.findAllByFileCode(fileCode);
        PbFile file = pbFileReponsitory.findByFileCode(fileCode);
        JSONArray jsonArray = new JSONArray();
        if (fileFields != null && !(fileFields.isEmpty())) {
            for (PbFileField field : fileFields) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("field_name", field.getFieldName());
                jsonObject.put("field_value", field.getFieldValue());
                jsonObject.put("id", field.getId());
                jsonObject.put("is_show", field.getIsShow());
                jsonArray.add(jsonObject);
            }
        }
        resObj.put("field", jsonArray);
        if (file != null) {
            resObj.put("path", file.getPath());
        }
        return resObj;
    }

    /**
     * 图片库-查询
     */
    public JSONArray queryImagesByProCode(String proCode) {
        List<PbImage> imageList = pbImageRepository.findAllByProCode(proCode);
        JSONArray jsonArray = new JSONArray();
        if (imageList != null && !(imageList.isEmpty())) {
            for (PbImage image : imageList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("is_show", image.getIsShow());
                jsonObject.put("id", image.getId());
                jsonObject.put("image_name", image.getImageName());
                jsonObject.put("image_path", image.getImagePath());
                jsonObject.put("image_type", image.getImageType());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
