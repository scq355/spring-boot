package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbFile;
import cn.com.jcgroup.service.domain.PbImage;
import cn.com.jcgroup.service.service.ProjectDocService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:文档库
 * User: sunchangqing
 * Date: 2017-06-15
 * Time: 上午11:34
 */
@Service
public class ProjectDocManage {

    @Autowired
    private ProjectDocService projectDocService;

    /**
     * 文档库列表
     */
    public JSONArray listFile(String proCdde, int page) {
        JSONArray resArray = new JSONArray();

        JSONArray jsonArray = projectDocService.querryFileByProCodeInPage(proCdde, page);
        if (jsonArray != null && !(jsonArray.isEmpty())) {
            int length = jsonArray.size();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("file_code", jsonArray.getJSONObject(i).getString("file_code"));
                jsonObject.put("file_name", jsonArray.getJSONObject(i).getString("file_name"));
                jsonObject.put("file_type", jsonArray.getJSONObject(i).getString("file_type"));
                jsonObject.put("pro_node", "========");
                jsonObject.put("pro_stage", "========");
                jsonObject.put("sign_time", "========");
                jsonObject.put("work_item", "========");
                resArray.add(jsonObject);
            }
        }
        return resArray;
    }

    /**
     * 图片库-查询
     */
    public JSONArray queryImagesByProCode(String proCode) {
        JSONArray jsonArray = projectDocService.queryImagesByProCode(proCode);
        JSONArray resArray = new JSONArray();
        if (jsonArray != null && !(jsonArray.isEmpty())) {
            int jsonSize = jsonArray.size();
            for (int i = 0; i < jsonSize; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject imageObj = jsonArray.getJSONObject(i);
                if (imageObj != null) {
                    jsonObject.put("is_show", imageObj.getString("is_show"));
                    jsonObject.put("photo_id", imageObj.getString("id"));
                    jsonObject.put("photo_name", imageObj.getString("image_name"));
                    jsonObject.put("photo_path", imageObj.getString("image_path"));
                    jsonObject.put("photo_type", imageObj.getString("image_type"));
                    resArray.add(jsonObject);
                }
            }
        }
       return resArray;
    }
}
