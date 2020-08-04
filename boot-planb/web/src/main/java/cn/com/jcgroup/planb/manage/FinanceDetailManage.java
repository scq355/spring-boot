package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.service.service.AgencyService;
import cn.com.jcgroup.service.service.FinanceDetailService;
import cn.com.jcgroup.service.util.MathUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:机构信息
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午3:09
 */
@Service
public class FinanceDetailManage {

    @Autowired
    private FinanceDetailService financeDetailService;
    @Autowired
    private AgencyService agencyService;

    /**
     * 获取机构或者其它
     */
    public JSONObject buildAgencyByAgencyCodeAndType(String agnecyCode, String type) {
        JSONObject agencyObj = financeDetailService.findAngecyByAgencyCodeAndType(agnecyCode, type);
        JSONObject resObj = new JSONObject();
        if (agencyObj != null) {
            resObj.put("amount", agencyObj.getString("amount"));
            resObj.put("business_item", agencyObj.getString("businessItem"));
            resObj.put("capital_cost", agencyObj.getString("capitalCost"));
            resObj.put("capital_use", agencyObj.getString("capitalUse"));
            resObj.put("time_limit", agencyObj.getString("timeLimit"));
        }
        return resObj;
    }

    /**
     * 金融机构
     */
    public JSONObject buildFinanceAgency(String financeAgencyCode) {
        JSONObject financeObject = financeDetailService.findFinanceAgencyByAgencyCode(financeAgencyCode);
        JSONObject resObj = new JSONObject();
        if (financeObject != null) {
            Long usedAmount = financeObject.getLong("usedAmount");
            Long totalAmount = financeObject.getLong("creditAmount");
            long tempUsedAmount = usedAmount == null ? 0L : usedAmount;
            long tempTotalAmount = totalAmount == null ?0L : totalAmount;
            resObj.put("capital_cost", financeObject.getString("capitalCost"));
            resObj.put("credit_amount", NumberUtil.unitTenThousand(tempTotalAmount));
            resObj.put("credit_date", financeObject.getString("createTime"));
            if(tempTotalAmount == 0){
                resObj.put("percent","0");
            }else{
                resObj.put("percent", MathUtil.format(MathUtil.div(tempUsedAmount,tempTotalAmount)));
            }
            long remainAmount = tempTotalAmount - tempUsedAmount;
            resObj.put("remain_amount", NumberUtil.unitTenThousand(remainAmount));
            resObj.put("use_amount", NumberUtil.unitTenThousand(tempUsedAmount));
        }
        return resObj;
    }

    /**
     * 私募数据
     */
    public JSONObject buildPrivateFunfByFundCode(String fundCode) {
        JSONObject resObj = new JSONObject();
        JSONObject financeObject = financeDetailService.findPrivateFundByFundCode(fundCode);
        if (financeObject != null) {
            resObj.put("apr", financeObject.getString("apr"));
            resObj.put("capital_use", financeObject.getString("capitalUse"));
            resObj.put("cooperation", financeObject.getString("financeSide"));
            resObj.put("custody_fee", financeObject.getString("consignFee"));
            resObj.put("fund_duration", financeObject.getString("fundDuration"));
            resObj.put("guarantor", financeObject.getString("guarantor"));
            resObj.put("invest_fee", financeObject.getString("investFee"));
            resObj.put("manage_fee", financeObject.getString("manageFee"));
            resObj.put("manager", financeObject.getString("fundManager"));
            resObj.put("risk_control", financeObject.getString("riskControl"));
            resObj.put("risk_level", financeObject.getString("riskLevel"));
            resObj.put("total_amount", financeObject.getString("raiseAmount"));
            resObj.put("consignment_fee", financeObject.getString("custodyFee"));
            JSONArray fundMoneyArray = null;
            String periodInfo = financeObject.getString("periodInfo");
            if(StringUtils.isNotBlank(periodInfo)){
                //成立金额及期数
                JSONArray temp = JSONArray.parseArray(periodInfo);
                int size = temp.size();
                fundMoneyArray = new JSONArray(size);
                for(int j =0;j<size;j++){
                    JSONObject tempObject = temp.getJSONObject(j);
                    JSONObject amountObj = new JSONObject();
                    amountObj.put("amount", NumberUtil.unitTenThousand(tempObject.getLongValue("productIssueScale")));
                    amountObj.put("num_periods", tempObject.get("productName"));
                    amountObj.put("time", tempObject.get("establishDate"));
                    fundMoneyArray.add(amountObj);
                }
            }
            resObj.put("fund_money",fundMoneyArray);
        }
        return resObj;
    }

    /**
     * 机构列表
     */
    public JSONArray buildAgencyList(String companyCode, String[] typeArray) {
        JSONArray resArray = null;
        if (typeArray != null && companyCode != null) {
            resArray = new JSONArray();
            int typeLength = typeArray.length;
            for (int i = 0; i < typeLength; i++) {
                //获取机构编码列表
                JSONArray agencyCodeArray = agencyService.queryCodeByCompanyCodeAndType(companyCode, typeArray[i]);
                JSONArray agencyArray = null;
                if (agencyCodeArray != null && !agencyCodeArray.isEmpty()) {
                    int agencyArrayLength = agencyCodeArray.size();
                    agencyArray = new JSONArray(agencyArrayLength);
                    for (int j = 0; j < agencyArrayLength; j++) {
                        JSONObject agencyObj = new JSONObject();
                        //根据编码查询机构名称
                        String code = agencyCodeArray.getString(j);
                        String name = financeDetailService.findAgencyName(code,typeArray[i]);
                        agencyObj.put("name", name);
                        agencyObj.put("identify",code);
                        agencyArray.add(agencyObj);
                    }
                }
                JSONObject typeObject = new JSONObject();
                typeObject.put("type", typeArray[i]);
                typeObject.put("agency",agencyArray);
                resArray.add(typeObject);
            }
        }
        return resArray;
    }
}
