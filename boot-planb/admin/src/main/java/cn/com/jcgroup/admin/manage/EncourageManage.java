package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.EncourageDto;
import cn.com.jcgroup.admin.dto.EncourageFileDto;
import cn.com.jcgroup.service.service.EncourageService;
import cn.com.jcgroup.service.service.ProjectService;
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

/**
 * Description: 激励，激励文件
 * User: sunchangqing
 * Date: 2017-06-22
 * Time: 下午2:44
 */
@Service
public class EncourageManage {

    private static final Logger LOG = LoggerFactory.getLogger(EncourageManage.class);

    @Autowired
    private EncourageService encourageService;
    @Autowired
    private ProjectService projectService;

    /**
     * 激励文件修改
     */
    public void updateEncourageFile(EncourageFileDto encourageFileDto) {
        if (encourageFileDto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileName", encourageFileDto.getFile_name());
            jsonObject.put("fileCode", encourageFileDto.getFile_code());
            String isShow = jsonObject.getString("isShow");

            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                jsonObject.put("is_show", true);
            } else {
                jsonObject.put("is_show", false);
            }
            jsonObject.put("fileExt", encourageFileDto.getFile_ext());
            encourageService.updateEncourageFile(jsonObject);
        }
    }

    /**
     * 激励文件-列表
     */
    public JSONArray buildEncourageFileByProCode(String proCode, int page) {
        JSONArray encourageArray = encourageService.queryEncourageFileByProCode(proCode, page);
        JSONArray jsonArray = new JSONArray();
        if (encourageArray != null && !encourageArray.isEmpty()) {
            int encourageNum = encourageArray.size();
            for (int i = 0; i < encourageNum; i++) {
                JSONObject jsonObject = encourageArray.getJSONObject(i);
                jsonObject.put("file_name", jsonObject.getString("fileName"));
                jsonObject.put("file_ext", jsonObject.getString("fileExt"));
                String isShow = jsonObject.getString("isShow");
                if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                    jsonObject.put("is_show", true);
                } else {
                    jsonObject.put("is_show", false);
                }
                jsonObject.put("file_code", jsonObject.getString("fileCode"));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 激励情况修改
     */
    public void encourageTotalUpdate(String proCode, String level, long money) {
        projectService.updateEncourageMoneyByProCode(proCode, level, money);
    }

    /**
     * 激励情况修改
     */
    public void encourageUpdate(EncourageDto encourageDto) {
        if (encourageDto != null) {
            JSONObject jsonObject = new JSONObject();
            if (encourageDto.isMoney_flag()) {
                jsonObject.put("moneyFlag", 1);
            } else {
                jsonObject.put("moneyFlag", 0);
            }

            Date preDate = DateUtil.parseDate(encourageDto.getPre_date());
            jsonObject.put("preDate", preDate);
            jsonObject.put("prePercent", encourageDto.getPre_percent());
            Double realMoney = Double.valueOf(encourageDto.getReal_money()) * 1000000;
            jsonObject.put("realMoney", realMoney);
            jsonObject.put("realPercent", encourageDto.getReal_percent());
            if (encourageDto.isReal_status()) {
                jsonObject.put("realStatus", 1);
            } else {
                jsonObject.put("realStatus", 0);
            }
            jsonObject.put("stage", encourageDto.getStage());
            jsonObject.put("id", encourageDto.getId());
            encourageService.updateEncourage(jsonObject);
        }
    }


    /**
     * 激励情况显示
     */
    public JSONObject encourageList(String proCode, int page) {
        JSONObject resObject = new JSONObject();
        JSONArray encourageArray = encourageService.queryEncourageByProCode(proCode, page);
        JSONObject projectObject = projectService.findProjectBasicInfo(proCode);
        JSONArray jsonArray = new JSONArray();
        if (encourageArray != null && !(encourageArray.isEmpty())) {
            int encourageNum = encourageArray.size();
            for (int i = 0; i < encourageNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject encourageObj = encourageArray.getJSONObject(i);
                if (encourageObj != null) {
                    jsonObject.put("id", encourageObj.getString("id"));
                    jsonObject.put("stage", encourageObj.getString("stage"));
                    Integer realStatus = encourageObj.getInteger("realStatus");
                    if(realStatus != null && (realStatus == 1)){
                        jsonObject.put("real_status", true);
                    }else{
                        jsonObject.put("real_status", false);
                    }
                    jsonObject.put("real_percent", encourageObj.getString("realPercent"));
                    Integer moneyFlag = encourageObj.getInteger("moneyFlag");
                    if(moneyFlag != null && (moneyFlag == 1)){
                        jsonObject.put("money_flag", true);
                    }else{
                        jsonObject.put("money_flag", false);
                    }
                    String dateStr = DateUtil.formatDate("yyyy.MM", encourageObj.getDate("preDate"));
                    jsonObject.put("pre_date", dateStr);

                    jsonObject.put("pre_percent", encourageObj.getString("prePercent"));
                    //转换为以万为单位
                    Long realMoney = encourageObj.getLong("realMoney");
                    jsonObject.put("real_money", realMoney == null ? "0" : NumberUtil.unitTenThousand(realMoney));
                    jsonArray.add(jsonObject);
                }
            }
        }
        resObject.put("encourage_list", jsonArray);
        resObject.put("level", projectObject.getString("levels"));
        Long encourageMoney = projectObject.getLong("encourageMoney");
        resObject.put("encourage_money", encourageMoney == null ? "0" : NumberUtil.unitTenThousand(encourageMoney));
        return resObject;
    }

    /**
     * 激励添加
     */
    public void encourageAdd(EncourageDto encourageDto) {
        if (encourageDto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stage", encourageDto.getStage());
            if (encourageDto.isMoney_flag()) {
                jsonObject.put("moneyFlag", 1);
            } else {
                jsonObject.put("moneyFlag", 0);
            }
            if (encourageDto.isReal_status()) {
                jsonObject.put("realStatus", 1);
            } else {
                jsonObject.put("realStatus", 0);
            }
            jsonObject.put("realPercent", encourageDto.getReal_percent());
            Double realMoney = Double.valueOf(encourageDto.getReal_money()) * 1000000;
            jsonObject.put("realMoney", realMoney);
            jsonObject.put("prePercent", encourageDto.getPre_percent());
            Date preDate = DateUtil.parseDate(encourageDto.getPre_date());
            jsonObject.put("getPreDate", preDate);
            jsonObject.put("proCode", encourageDto.getPro_code());
            encourageService.insertEncourage(jsonObject, preDate);
        }
    }

    /**
     * 激励删除
     */
    public void encourageDelete(int id) {
        try {
            encourageService.deleteEncourage(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
