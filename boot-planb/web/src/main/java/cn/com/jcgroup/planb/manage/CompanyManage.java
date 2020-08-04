package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.service.service.CompanyService;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 合营公司
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午2:38
 */
@Service
public class CompanyManage {
    
    private static final Logger LOG = LoggerFactory.getLogger(CompanyManage.class);

    @Autowired
    private CompanyService companyService;

    /**
     * 合营公司详细信息
     */
    public JSONObject buildCompanyInfoByCompanyCode(String companyCode) {
        JSONObject resObj = new JSONObject();
        JSONObject companyObj = companyService.getCompanyInfoByCompanyCode(companyCode);
        if (companyObj != null) {
            //董监高信息
            JSONObject djgObj = new JSONObject();
            djgObj.put("gjjl", companyObj.getString("supervisors"));
            djgObj.put("js", companyObj.getString("seniorManager"));
            djgObj.put("ds", companyObj.getString("director"));

            resObj.put("djg", djgObj);
            //人事信息
            JSONArray hrInfoArray = null;
            List<Integer> ids = companyService.findStaffIdByCompanyCode(companyCode);
            if (ids != null && !ids.isEmpty()) {
                JSONArray jsonArray = companyService.findStaffInfoByIds(ids);
                if(jsonArray != null && !jsonArray.isEmpty()){
                    hrInfoArray = new JSONArray();
                    for(Object object:jsonArray){
                        JSONObject temp =  (JSONObject) JSON.toJSON(object);
                        JSONObject hrObj = new JSONObject();
                        hrObj.put("name",temp.get("name"));
                        hrObj.put("post",temp.get("post"));
                        hrObj.put("sex",temp.get("sex"));
                        hrObj.put("tel",temp.get("telephone"));
                        hrInfoArray.add(hrObj);
                    }
                }
            }
            resObj.put("rsxx", hrInfoArray);
            //其他基本信息
            resObj.put("fr", companyObj.getString("legalPerson"));
            String ownerShipStruct = companyObj.getString("ownershipStruct");
            try{
                if(StringUtils.isNotBlank(ownerShipStruct)){
                    JSONArray jsonArray = JSONArray.parseArray(ownerShipStruct);
                    resObj.put("gqjg", jsonArray);
                }else{
                    resObj.put("gqjg", "");
                }
            }catch (Exception e){
                LOG.error("[合营公司详细]股权架构图解析错误");
                resObj.put("gqjg", "");
            }
            resObj.put("gqzryd", companyObj.getString("equityTransAgree"));
            resObj.put("lrfpfa", companyObj.getString("profitDistrPlan"));
            resObj.put("xmfzr", companyObj.getString("projectManager"));
            resObj.put("zctsyd", companyObj.getString("rulePromise"));
            resObj.put("zczb", NumberUtil.unitTenThousand(companyObj.getLongValue("registerCapital")));
            resObj.put("bgdz", companyObj.getString("officeAddress"));
            resObj.put("bgdztp", companyObj.getString("officeImages"));
            resObj.put("bgmj", companyObj.getString("officesize"));
        }
        return resObj;
    }
}
