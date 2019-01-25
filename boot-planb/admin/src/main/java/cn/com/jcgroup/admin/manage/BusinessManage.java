package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.common.PageInfo;
import cn.com.jcgroup.admin.dto.ReceptionListDto;
import cn.com.jcgroup.admin.dto.TravelListDto;
import cn.com.jcgroup.service.enums.BusinessTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.service.BusinessService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 业务分析相关
 *
 * @author LiuYong on 17/6/25 下午9:22.
 */
@Service
public class BusinessManage {
    
    private static final Logger LOG = LoggerFactory.getLogger(BusinessManage.class);
    @Autowired
    private BusinessService businessService;

    /**
     * 组装员工出差列表
     *
     * @author LiuYong
     */
    public JSONObject buildTravelInfoList(List<String> departCode, int page) {
        JSONObject result = null;
        JSONObject jsonObject = businessService.findTravelReceptionInfo(departCode, page, BusinessTypeEnum.TRAVEL.getType());
        if (jsonObject != null) {
            result = new JSONObject();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(jsonObject.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo", pageInfo);
            JSONArray data = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                data = new JSONArray();
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject temp = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                    //修改数据类型
                    Date endTime = temp.getDate("endTime");
                    Date startTime = temp.getDate("startTime");
                    temp.put("endTime", endTime != null ? DateUtil.formatDate("yyyy-MM-dd", endTime) : "");
                    temp.put("startTime", startTime != null ? DateUtil.formatDate("yyyy-MM-dd", startTime) : "");
                    //费用
                    Long fee = temp.getLong("fee");
                    temp.put("fee",fee == null ? 0 : NumberUtil.unitMoney(fee));
                    //部门转换
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(temp.getString("department"));
                    temp.put("department",companyEnum == null ? "" : companyEnum.getName());
                    //词频
                    String keyWords = temp.getString("keyWords");
                    try {
                        if (StringUtils.isNotBlank(keyWords)) {
                            JSONArray keyWordsArray = JSONArray.parseArray(keyWords);
                            temp.put("keyWords", keyWordsArray);
                        } else {
                            temp.put("keyWords", "");
                        }
                    } catch (Exception e) {
                        LOG.error("[出差列表]词频转换异常,keyWords=" + keyWords, e);
                        temp.put("keyWords", "");
                    }
                    TravelListDto travelListDto = temp.toJavaObject(TravelListDto.class);
                    data.add(travelListDto);
                }
            }
            result.put("data", data);
        }
        return result;
    }

    /**
     * 组装员工接待列表
     *
     * @author LiuYong
     */
    public JSONObject buildReceptionInfoList(List<String> departCode, int page) {
        JSONObject result = null;
        JSONObject jsonObject = businessService.findTravelReceptionInfo(departCode, page, BusinessTypeEnum.RECEPTION.getType());
        if (jsonObject != null) {
            result = new JSONObject();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(jsonObject.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo", pageInfo);
            JSONArray data = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                data = new JSONArray();
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject temp = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                    //修改数据类型
                    Date startTime = temp.getDate("startTime");
                    temp.put("startTime", startTime != null ? DateUtil.formatDate("yyyy-MM-dd", startTime) : "");
                    //费用
                    Long fee = temp.getLong("fee");
                    temp.put("fee",fee == null ? 0 : NumberUtil.unitMoney(fee));
                    //部门转换
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(temp.getString("department"));
                    temp.put("company",companyEnum == null ? "" : companyEnum.getName());
                    //词频
                    String keyWords = temp.getString("keyWords");
                    try {
                        if (StringUtils.isNotBlank(keyWords)) {
                            JSONArray keyWordsArray = JSONArray.parseArray(keyWords);
                            temp.put("keyWords", keyWordsArray);
                        } else {
                            temp.put("keyWords", "");
                        }
                    } catch (Exception e) {
                        LOG.error("[接待列表]词频转换异常,keyWords=" + keyWords, e);
                        temp.put("keyWords", "");
                    }
                    ReceptionListDto receptionListDto = temp.toJavaObject(ReceptionListDto.class);
                    data.add(receptionListDto);
                }
            }
            result.put("data", data);
        }
        return result;
    }

}
