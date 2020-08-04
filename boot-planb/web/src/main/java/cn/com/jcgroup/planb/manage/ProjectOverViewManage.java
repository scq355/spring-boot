package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.service.domain.PbCompany;
import cn.com.jcgroup.service.service.CompanyService;
import cn.com.jcgroup.service.service.ProjectService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:项目概览
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 上午10:44
 */
@Service
public class ProjectOverViewManage {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProjectService projectService;

    /**
     * 相关文件
     */
    public JSONArray buildEncourageFileByProCode(String proCode) {
        JSONArray resArray = new JSONArray();
        JSONArray fileArray = projectService.queryFilesByProjectCode(proCode);
        if (fileArray != null && !(fileArray.isEmpty())) {
            int fileNum = fileArray.size();
            for (int i = 0; i < fileNum; i++) {
                JSONObject fileObject = new JSONObject();
                fileObject.put("file_name", fileArray.getJSONObject(i).getString("fileName"));
                fileObject.put("file_code", fileArray.getJSONObject(i).getString("fileCode"));
                resArray.add(fileObject);
            }
        }
        return resArray;
    }

    /**
     * 获取合营公司列表
     */
    public JSONArray buildCompanyListByProCode(String proCode) {
        JSONArray resArray = new JSONArray();
        if (StringUtils.isBlank(proCode)) {
            return resArray;
        }
        JSONArray companyArray = companyService.getCompanyListByProjectCode(proCode);
        if (companyArray != null && !(companyArray.isEmpty())) {
            int length = companyArray.size();
            for (int i = 0; i < length; i++) {
                JSONObject comObj = new JSONObject();
                comObj.put("company_code", companyArray.getJSONObject(i).getString("companyCode"));
                comObj.put("company_name", companyArray.getJSONObject(i).getString("companyName"));
                resArray.add(comObj);
            }
        }
        return resArray;
    }
}
