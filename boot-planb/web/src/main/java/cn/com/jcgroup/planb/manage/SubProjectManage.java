package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.planb.common.PageInfo;
import cn.com.jcgroup.service.service.ProjectService;
import cn.com.jcgroup.service.service.SubProjectService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 工程相关
 * @author LiuYong on 17/6/14 下午9:29.
 */
@Service
public class SubProjectManage {

    private static Logger LOG = LoggerFactory.getLogger(SubProjectManage.class);
    
    @Autowired
    private SubProjectService subProjectService;
    @Autowired
    private ProjectService projectService;


    public JSONArray buildPaidItemBySubProCode(String subProCode, Date startTime, Date endTime) {
        JSONArray resArray = null;
        JSONArray paidItemArray = subProjectService.queryPaidFlowBySubProCode(subProCode, startTime, endTime);
        if(paidItemArray != null && !paidItemArray.isEmpty()){
            int size = paidItemArray.size();
            resArray = new JSONArray(size);
            for(int i=0;i<size;i++){
                JSONObject temp = (JSONObject) JSON.toJSON(paidItemArray.get(i));
                JSONObject jsonObject = new JSONObject();
                resArray.add(jsonObject);
                jsonObject.put("info",temp.get("fundInfo"));
                jsonObject.put("money",NumberUtil.unitTenThousand(temp.getLongValue("paidMoney")));
                jsonObject.put("paid_time",DateUtil.formatDate("yyyy.MM.dd",temp.getDate("payTime")));
            }
        }
        return resArray;
    }

    /**
     * 月度台账
     */
    public JSONArray buidMonthPaperBySubProCode(String subProCode) {
        JSONArray resArray = new JSONArray();
        JSONArray monthPaperArray = subProjectService.queryMonthPaperBySubProCode(subProCode);
        if (monthPaperArray != null && !(monthPaperArray.isEmpty())) {
            int monthPaperNum = monthPaperArray.size();
            for (int i = 0; i < monthPaperNum; i++) {
                JSONObject jsonObject = new JSONObject();
                String paperName = monthPaperArray.getJSONObject(i).getString("paperName");
                String paperType = monthPaperArray.getJSONObject(i).getString("paperExt");
                String fullName = paperName + "." + paperType;
                jsonObject.put("file_name", fullName);
                jsonObject.put("url", monthPaperArray.getJSONObject(i).getString("paperLink"));
                resArray.add(jsonObject);
            }
        }
        return resArray;
     }

    /**
     * 应付台账
     */
    public JSONObject buildPayforBySubProCode(String subProCode,int page) {
        JSONObject result = new JSONObject();
        JSONObject temp = subProjectService.queryBillPayforBySubProCode(subProCode,page);
        JSONArray data = null;
        if (temp != null && !temp.isEmpty()) {
            JSONArray tempJSONArray = temp.getJSONArray("data");
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(temp.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo",pageInfo);
            int length = tempJSONArray==null ? 0: tempJSONArray.size();
            data = new JSONArray(length);
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject tempJsonObject = (JSONObject) JSON.toJSON(tempJSONArray.get(i));
                data.add(jsonObject);
                jsonObject.put("contract_amount",NumberUtil.unitTenThousand(tempJsonObject.getLongValue("contractMoney")));
                jsonObject.put("contract_item", tempJsonObject.get("summaryContract"));
                jsonObject.put("contract_name", tempJsonObject.get("contractName"));
                jsonObject.put("other", tempJsonObject.get("otherInfo"));
                jsonObject.put("party_A", tempJsonObject.get("cooperA"));
                jsonObject.put("party_B", tempJsonObject.get("cooperB"));
                jsonObject.put("pay_amount", NumberUtil.unitTenThousand(tempJsonObject.getLongValue("payMoney")));
                jsonObject.put("pay_time", DateUtil.formatDate("yyyy.MM.dd",tempJsonObject.getDate("payTime")));
            }
        }
        result.put("data",data);
        return result;
    }

    /**
     * 应收台账
     */
    public JSONObject buildPayableBySubProCode(String subProCode,int page) {
        JSONObject result = new JSONObject();
        JSONObject temp = subProjectService.queryPayabelByPySubProCode(subProCode,page);
        JSONArray data = null;
        if (temp != null && !temp.isEmpty()) {
            JSONArray tempJSONArray = temp.getJSONArray("data");
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(temp.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo",pageInfo);
            int length = tempJSONArray==null ? 0: tempJSONArray.size();
            data = new JSONArray(length);
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject tempJsonObject = (JSONObject) JSON.toJSON(tempJSONArray.get(i));
                data.add(jsonObject);
                jsonObject.put("contract_name", tempJsonObject.get("contractName"));
                jsonObject.put("other", tempJsonObject.get("otherInfo"));
                jsonObject.put("party_A", tempJsonObject.get("cooperA"));
                jsonObject.put("party_B", tempJsonObject.get("cooperB"));
                jsonObject.put("real_amount", NumberUtil.unitTenThousand(tempJsonObject.getLongValue("amountPaid")));
                jsonObject.put("receive_amount", tempJsonObject.get("payMoney"));
                jsonObject.put("receive_item", tempJsonObject.get("payAbleItem"));
                jsonObject.put("sign_amount", NumberUtil.unitTenThousand(tempJsonObject.getLongValue("contractMoney")));
            }
        }
        result.put("data",data);
        return result;
    }


    /**
     * 根据公司编码获取工程列表
     */
    public JSONArray buildSubProjectByCompanyCode(String proCode) {
        if(StringUtils.isBlank(proCode)){
            return null;
        }
        JSONArray subProjectArray = subProjectService.querySubProByProCode(proCode);
        JSONArray jsonArray = null;
        if (subProjectArray != null && !subProjectArray.isEmpty()) {
             jsonArray = new JSONArray();
             for(Object object : subProjectArray){
                 JSONObject jsonObject = (JSONObject) object;
                 JSONObject temp = new JSONObject();
                 temp.put("name", jsonObject.getString("subProjectName"));
                 temp.put("identify", jsonObject.getString("subProCode"));
                 jsonArray.add(temp);
             }
         }
         return jsonArray;
    }

    /**
     *  根据工程编码统计截止当前月份工程产值和累计付款
     * @author LiuYong
     */
    public JSONObject buildTotalMoneyByCodeAndTime(String subProCode){
        JSONObject jsonObject = new JSONObject();
        //初始化模板
        jsonObject.put("production_value","0");
        jsonObject.put("total_pay","0");
        //查询
        Date reportTime = DateUtil.getFirstDay(DateUtil.getMonth(new Date()));
        JSONObject tempJsonObject = subProjectService.queryTotalMoneyByCodeAndTime(subProCode, reportTime);
        if(tempJsonObject != null && !tempJsonObject.isEmpty()){
            long totalPaidMoney = tempJsonObject.getLongValue("totalPaidMoney");
            long totalPay = tempJsonObject.getLongValue("totalPay");
            jsonObject.put("production_value", NumberUtil.unitTenThousand(totalPay));
            jsonObject.put("total_pay",NumberUtil.unitTenThousand(totalPaidMoney));
        }
        return jsonObject;
    }

    /**
     * 查询工程款汇总流水
     *
     * @author LiuYong
     */
    public JSONObject buildSubProjectMoneySum(String subProCode, String reportTime) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tempJsonObject = subProjectService.querySubProjectMoneySum(subProCode, DateUtil.parseDate(reportTime,"yyyy.MM"));
        //组装模板
        JSONObject current = new JSONObject();
        JSONObject grand = new JSONObject();
        jsonObject.put("current", current);
        jsonObject.put("grand_total", grand);
        String checkedMoney = tempJsonObject == null ? "0" : NumberUtil.unitTenThousand(tempJsonObject.getLongValue("checkedMoney"));
        String realPaidMoney = tempJsonObject == null ? "0" : NumberUtil.unitTenThousand(tempJsonObject.getLongValue("realPaidMoney"));
        current.put("value", new String[]{realPaidMoney, checkedMoney});
        current.put("yAxis", new String[]{"实际支付工程款", "审定工程款"});
        String totalMoney = tempJsonObject == null ? "0 " : NumberUtil.unitTenThousand(tempJsonObject.getLongValue("totalMoney"));
        String totalCheckedMoney = tempJsonObject == null ? "0 " : NumberUtil.unitTenThousand(tempJsonObject.getLongValue("totalCheckedMoney"));
        String totalPaidMoney = tempJsonObject == null ? "0 " : NumberUtil.unitTenThousand(tempJsonObject.getLongValue("totalRealPaidMoney"));
        grand.put("value", new String[]{totalPaidMoney, totalCheckedMoney, totalMoney});
        grand.put("yAxis", new String[]{"累计实际支付工程款", "累计审定工程款", "合同总金额"});
        jsonObject.put("sub_project_process", tempJsonObject == null ? "" : tempJsonObject.getString("projectProgress"));
        return jsonObject;
    }
}
