package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.SubProAmountDto;
import cn.com.jcgroup.admin.dto.SubProPaidAmountDto;
import cn.com.jcgroup.service.service.SubProjectAmountService;
import cn.com.jcgroup.service.service.SubProjectService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:工程
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午1:31
 */
@Service
public class SubProjectManage {

    @Autowired
    private SubProjectService subProjectService;
    @Autowired
    private SubProjectAmountService subProjectAmountService;

    /**
     * 已付资金-列表
     */
    public JSONArray subProPaidAmountList(String subProCode, int page) {
        JSONArray jsonArray = subProjectAmountService.subProPaidAmountCode(subProCode, page);
        JSONArray resArray = new JSONArray();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            int subProPaidAmountNum = jsonArray.size();
            for (int i = 0; i < subProPaidAmountNum; i++) {
                JSONObject subProPaidAmount = jsonArray.getJSONObject(i);
                if (subProPaidAmount != null) {
                    JSONObject jsonObject = new JSONObject();
                    String formatDate = DateUtil.formatDate("yyyy.MM.dd", (Date) subProPaidAmount.get("payTime"));
                    jsonObject.put("pay_time", formatDate);
                    jsonObject.put("fund_info", subProPaidAmount.getString("fundInfo"));

                    Long amountOfSubProLongForm = (Long) subProPaidAmount.get("amountOfSubPro");
                    if (amountOfSubProLongForm != null) {
                        String amountOfSubPro = NumberUtil.unitMoney(amountOfSubProLongForm);
                        jsonObject.put("amount_of_sub_pro", amountOfSubPro);
                    }
                    Long paidMoneyLongForm = (Long) subProPaidAmount.get("paidMoney");
                    if (paidMoneyLongForm != null) {
                        String paidMoney = NumberUtil.unitMoney(paidMoneyLongForm);
                        jsonObject.put("paid_money", paidMoney);
                    }
                    jsonObject.put("id", subProPaidAmount.getString("id"));
                    resArray.add(jsonObject);
                }
            }
        }
        return resArray;
    }

    /**
     * 工程量-列表
     */
    public JSONArray subProAmountList(String subProCode, int page) {
        JSONArray jsonArray = subProjectAmountService.subProAmountCode(subProCode, page);
        JSONArray resArray = new JSONArray();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            int subProAmountNum = jsonArray.size();
            for (int i = 0; i < subProAmountNum; i++) {
                JSONObject subProObj = jsonArray.getJSONObject(i);
                if (subProObj != null) {
                    JSONObject jsonObject = new JSONObject();
                    String reportTime = DateUtil.formatDate("yyyy.MM", (Date) subProObj.get("reportTime"));
                    jsonObject.put("report_time", reportTime);
                    jsonObject.put("project_progress", subProObj.getString("projectProgress"));
                    jsonObject.put("id", subProObj.getString("id"));
                    //分为单位 -> 以万为单位
                    String totalRealPaidMoney = NumberUtil.unitTenThousand((Long) subProObj.get("totalRealPaidMoney"));
                    jsonObject.put("total_real_paid_money", totalRealPaidMoney);

                    String totalCheckedMoney = NumberUtil.unitTenThousand((Long) subProObj.get("totalCheckedMoney"));
                    jsonObject.put("total_checked_money", totalCheckedMoney);

                    String totalMoney = NumberUtil.unitTenThousand((Long) subProObj.get("totalMoney"));
                    jsonObject.put("total_money", totalMoney);

                    String realPaidMoney = NumberUtil.unitTenThousand((Long) subProObj.get("realPaidMoney"));
                    jsonObject.put("real_paid_money", realPaidMoney);

                    String checkedMoney = NumberUtil.unitTenThousand((Long) subProObj.get("checkedMoney"));
                    jsonObject.put("checked_money", checkedMoney);
                    resArray.add(jsonObject);
                }
            }
        }
        return resArray;
    }

    /**
     * 工程量查询
     */
    public JSONArray subProjectSearch(String subProjectName) {
        JSONArray resArray = new JSONArray();
        JSONArray jsonArray = subProjectService.subProjectSearch(subProjectName);
        if (jsonArray != null && !(jsonArray.isEmpty())) {

            int subProNum = jsonArray.size();
            for (int i = 0; i < subProNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject subProjectObj = jsonArray.getJSONObject(i);
                if (subProjectObj != null) {
                    String isShow = subProjectObj.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")){
                        jsonObject.put("is_show", true);
                    } else{
                        jsonObject.put("is_show", false);
                    }
                    jsonObject.put("sub_pro_code", subProjectObj.getString("subProCode"));
                    jsonObject.put("sub_pro_id", subProjectObj.getString("subProId"));
                    jsonObject.put("sub_pro_name", subProjectObj.getString("subProjectName"));
                    jsonObject.put("total_money", subProjectObj.getString("totalMoney"));
                    jsonObject.put("file_code", subProjectObj.getString("relatedContractFileCode"));
                    resArray.add(jsonObject);
                }
            }
        }
        return resArray;
    }

    /**
     * 工程列表
     */
    public JSONArray subProjectListInPage(String projectCode, int page) {
        JSONArray jsonArray = subProjectService.subProjectList(projectCode, page);
        JSONArray resArray = new JSONArray();
        if (jsonArray != null && !(jsonArray.isEmpty())) {

            int subProNum = jsonArray.size();
            for (int i = 0; i < subProNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject subProjectObj = jsonArray.getJSONObject(i);
                if (subProjectObj != null) {
                    String isShow = subProjectObj.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")){
                        jsonObject.put("is_show", true);
                    } else{
                        jsonObject.put("is_show", false);
                    }
                    jsonObject.put("sub_pro_code", subProjectObj.getString("subProCode"));
                    jsonObject.put("sub_pro_id", subProjectObj.getString("subProId"));
                    jsonObject.put("sub_pro_name", subProjectObj.getString("subProjectName"));
                    jsonObject.put("total_money", subProjectObj.getString("totalMoney"));
                    jsonObject.put("file_code", subProjectObj.getString("fileCode"));
                    resArray.add(jsonObject);
                }
            }
        }
        return resArray;
    }

    /**
     * 工程量-添加
     */
    public void subProAmountAdd(SubProAmountDto subProAmountDto) {
        JSONObject jsonObject = new JSONObject();
        if (subProAmountDto != null) {
            jsonObject.put("projectProgress", subProAmountDto.getProject_progress());
            jsonObject.put("subProCode", subProAmountDto.getSub_pro_code());
            //万 -> 分
            Long checkedMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getChecked_money(), 10000L);
            jsonObject.put("checkedMoney", checkedMoney);
            Long realPaidMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getReal_paid_money(), 10000L);
            jsonObject.put("realPaidMoney", realPaidMoney);
            Long totalCheckedMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_checked_money(), 10000L);
            jsonObject.put("totalCheckedMoney", totalCheckedMoney);
            Long totalMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_money(), 10000L);
            jsonObject.put("totalMoney", totalMoney);
            Long totalRealPaidMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_real_paid_money(), 10000L);
            jsonObject.put("totalRealPaidMoney", totalRealPaidMoney);

            Date reportTime = DateUtil.parseDate(subProAmountDto.getReport_time());
            jsonObject.put("reportTime", reportTime);
            subProjectAmountService.subProAmountAdd(jsonObject);
        }
    }

    /**
     * 资金流水-添加
     */
    public void subProPaidAmountAdd(SubProPaidAmountDto subProPaidAmountDto) {
        JSONObject jsonObject = new JSONObject();
        if (subProPaidAmountDto != null) {

            jsonObject.put("fundInfo", subProPaidAmountDto.getFund_info());
            jsonObject.put("subProCode", subProPaidAmountDto.getSub_pro_code());
            //万 -> 分
            Long amountOfSubPro = NumberUtil.convertToPointWithUnit(subProPaidAmountDto.getAmount_of_sub_pro(), 1L);
            jsonObject.put("amountOfSubPro", amountOfSubPro);
            Long paidMoney = NumberUtil.convertToPointWithUnit(subProPaidAmountDto.getPaid_money(), 1L);
            jsonObject.put("paidMoney", paidMoney);

            Date payTime = DateUtil.parseDate(subProPaidAmountDto.getPay_time());
            jsonObject.put("payTime", payTime);
            subProjectAmountService.subProPaidAmountAdd(jsonObject);
        }
    }

    /**
     * 工程量-编辑
     */
    public void subProAmountUpdate(SubProAmountDto subProAmountDto) {
        if (subProAmountDto != null){
            JSONObject jsonObject = new JSONObject();

            //万 -> 分
            Long checkedMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getChecked_money(), 10000L);
            jsonObject.put("checkedMoney", checkedMoney);
            Long realPaidMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getReal_paid_money(), 10000L);
            jsonObject.put("realPaidMoney", realPaidMoney);
            Long totalCheckedMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_checked_money(), 10000L);
            jsonObject.put("totalCheckedMoney", totalCheckedMoney);
            Long totalMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_money(), 10000L);
            jsonObject.put("totalMoney", totalMoney);
            Long totalRealPaidMoney = NumberUtil.convertToPointWithUnit(subProAmountDto.getTotal_real_paid_money(), 10000L);
            jsonObject.put("totalRealPaidMoney", totalRealPaidMoney);

            jsonObject.put("subProCode", subProAmountDto.getSub_pro_code());
            Date reportTime = DateUtil.parseDate(subProAmountDto.getReport_time());
            jsonObject.put("reportTime", reportTime);
            jsonObject.put("projectProgress", subProAmountDto.getProject_progress());
            jsonObject.put("id", subProAmountDto.getId());
            subProjectAmountService.subProAmountUpdate(jsonObject);
        }
    }

    /**
     * 资金流修改
     */
    public void subProPaidAmountUpdate(SubProPaidAmountDto subProPaidAmountDto) {
        JSONObject jsonObject = new JSONObject();
        if (subProPaidAmountDto != null) {
            //万 -> 分
            Long amountOfSubPro = NumberUtil.convertToPointWithUnit(subProPaidAmountDto.getAmount_of_sub_pro(), 1L);
            jsonObject.put("amountOfSubPro", amountOfSubPro);
            Long paidMoney = NumberUtil.convertToPointWithUnit(subProPaidAmountDto.getPaid_money(), 1L);
            jsonObject.put("paidMoney", paidMoney);
            Date payTime = DateUtil.parseDate(subProPaidAmountDto.getPay_time());
            jsonObject.put("payTime", payTime);

            jsonObject.put("fundInfo", subProPaidAmountDto.getFund_info());
            jsonObject.put("subProCode", subProPaidAmountDto.getSub_pro_code());
            jsonObject.put("id", subProPaidAmountDto.getId());
            subProjectAmountService.subProPaidAmountUpdate(jsonObject);
        }
    }
}
