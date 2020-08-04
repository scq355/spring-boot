package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.service.enums.CashFlowTypeEnum;
import cn.com.jcgroup.service.service.FinanceProgressService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.JsonUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资金流相关
 * @author LiuYong on 17/6/9 下午12:45.
 */
@Service
public class FinanceProgressManage {
    
    @Autowired
    private FinanceProgressService financeProgressService;

    /**
     * 根据时间段和资金类别统计预期资金和实际资金
     * @author LiuYong
     */
    public JSONObject buildMoneyByCompanyCodeAndReportTimeBetween(String companyCode, String type){
        JSONObject result = new JSONObject();
        JSONArray jsonArray = financeProgressService.getMoneyByCompanyCodeAndReportTimeBetween(companyCode,type);
        JSONArray preArray = new JSONArray(JsonUtil.initJsonArray(12,"0"));
        JSONArray realArray = new JSONArray(JsonUtil.initJsonArray(12,"0"));
        JSONArray xAliasArray = new JSONArray(DateUtil.buildCurrentYearMonth("yyyy.MM"));
        if(jsonArray != null && !jsonArray.isEmpty()){
            int size = jsonArray.size();
            for(int i=0;i<size;i++){
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                Date reportTime = tempJsonObject.getDate("reportTime");
                if(reportTime != null){
                    int index = DateUtil.getMonth(reportTime);
                    //转换为万元单位
                    preArray.set(index, NumberUtil.unitTenThousand(tempJsonObject.getLongValue("totalExpectMoney")));
                    realArray.set(index, NumberUtil.unitTenThousand(tempJsonObject.getLongValue("totalRealMoney")));
                }
            }
        }
        result.put("pre_value",preArray);
        result.put("real_value",realArray);
        result.put("xAxis",xAliasArray);
        result.put("xAxis_key",xAliasArray);
        return result;
    }

    /**
     * 根据资金类别和合营公司编号查询资金明细
     * @author LiuYong
     */
    public JSONArray buildCashFlowHoverByCompanyCodeAndType(String companyCode, String type){
        JSONArray jsonArray = null;
        JSONArray itemArray = financeProgressService.getSecondLevelCashFlowByType(type);
        if(itemArray != null && !itemArray.isEmpty()){
            //组装财务条目
            int size = itemArray.size();
            JSONArray menu = new JSONArray(size);
            JSONObject jsonObject;
            List<Integer> items = new ArrayList<>(size);
            for(int i=0;i<size;i++){
                JSONObject temp = itemArray.getJSONObject(i);
                jsonObject = new JSONObject();
                jsonObject.put("name",temp.get("itemName"));
                jsonObject.put("id",temp.get("id"));
                items.add(temp.getInteger("id"));
                jsonObject.put("value","0");
                menu.add(jsonObject);
            }
            //初始化12个月数据
            jsonArray = new JSONArray(12);
            for(int i=1;i<=12;i++){
                String date = DateUtil.formatDate("yyyy.MM",DateUtil.getFirstDay(i));
                JSONObject tempJsonObject = new JSONObject();
                tempJsonObject.put("date",date);
                tempJsonObject.put("expect_menu",JsonUtil.deepClone(menu));
                tempJsonObject.put("real_menu",JsonUtil.deepClone(menu));
                jsonArray.add(tempJsonObject);
            }
            //查询各时间段的财务条目的数值
            JSONArray cashArray = financeProgressService.findCashFlowByItemIdAndTypeAndCompanyCode(companyCode,type,items);
            //填充数据
            if(cashArray != null && !cashArray.isEmpty()){
                for(Object cashObject: cashArray){
                    JSONObject cashJsonObject = (JSONObject)cashObject;
                    Date date = cashJsonObject.getDate("reportTime");
                    if(date != null){
                        int index = DateUtil.getMonth(date);
                        JSONObject indexJsonObject = jsonArray.getJSONObject(index);
                        JSONArray expectMenu = indexJsonObject.getJSONArray("expect_menu");
                        JSONArray realMenu = indexJsonObject.getJSONArray("real_menu");
                        for(Object object : expectMenu){
                            JSONObject expect = (JSONObject)object;
                            if(expect.getIntValue("id") == cashJsonObject.getIntValue("itemId")){
                                expect.put("value",NumberUtil.unitTenThousand(cashJsonObject.getLongValue("expectMoney")));
                                break;
                            }
                        }
                        for(Object object : realMenu){
                            JSONObject real = (JSONObject)object;
                            if(real.getIntValue("id") == cashJsonObject.getIntValue("itemId")){
                                real.put("value",NumberUtil.unitTenThousand(cashJsonObject.getLongValue("realMoney")));
                                break;
                            }
                        }
                    }
                }
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类别和合营公司编号查询所属月份资金报表
     * @author LiuYong
     */
    public JSONObject buildCashFlowReportByCompanyCodeAndType(String companyCode,Date reportTime,Date endTime, String type){
        JSONObject jsonObject = null;
        //构建模板
        //查询第一层条目
        JSONArray tempFirstArray = financeProgressService.getFirstLevelCashFlowByType(type);
        if(tempFirstArray != null && !tempFirstArray.isEmpty()){
            int size = tempFirstArray.size();
            JSONArray menuArray = new JSONArray(size);
            JSONObject firstJsonObject = null;
            //查询第二层条目
            JSONArray tempSecondArray = financeProgressService.getSecondLevelCashFlowByType(type);
            for(int i=0;i<size;i++){
                firstJsonObject = new JSONObject();
                JSONObject tempFirstJsonObject = tempFirstArray.getJSONObject(i);
                firstJsonObject.put("first_menu_name",tempFirstJsonObject.get("itemName"));
                firstJsonObject.put("second_menu",null);
                //组装第二层条目
                if(tempSecondArray != null && !tempSecondArray.isEmpty()){
                    JSONArray secondMenuArray = new JSONArray();
                    firstJsonObject.put("second_menu",secondMenuArray);
                    int secondSize = tempSecondArray.size();
                    JSONObject secondJsonObject = null;
                    // 组装所有条目的id
                    List<Integer> secondId = new ArrayList<>();
                    for(int j =0;j<secondSize;j++){
                        JSONObject tempSecondJsonObject = tempSecondArray.getJSONObject(j);
                        secondJsonObject = new JSONObject();
                        if(tempSecondJsonObject.getIntValue("pid") == tempFirstJsonObject.getIntValue("id")){
                            secondJsonObject.put("second_menu_name",tempSecondJsonObject.get("itemName"));
                            secondJsonObject.put("second_menu_id",tempSecondJsonObject.get("id"));
                            //查询资金
                            JSONObject moneyObject = financeProgressService.getSingleCashFlow(companyCode,type,reportTime,tempSecondJsonObject.getIntValue("id"));
                            secondJsonObject.put("real_money",moneyObject == null ? "0":NumberUtil.unitTenThousand(moneyObject.getLongValue("realMoney")));
                            secondJsonObject.put("pre_money",moneyObject == null ? "0":NumberUtil.unitTenThousand(moneyObject.getLongValue("expectMoney")));
                            //组装第三层
                            secondJsonObject.put("third_menu",null);
                            secondId.add(tempSecondJsonObject.getInteger("id"));
                            secondMenuArray.add(secondJsonObject);
                        }
                    }
                    //查询第三层条目
                    JSONArray tempThirdArray = financeProgressService.getThirdLevelCashFlowByType(type,secondId);
                    if(tempThirdArray != null && !tempThirdArray.isEmpty()){
                        for(Object object: secondMenuArray){
                            JSONObject temp = (JSONObject) object;
                            int second_menu_id = temp.getIntValue("second_menu_id");
                            int thirdSize = tempThirdArray.size();
                            JSONArray thirdMenuArray = new JSONArray();
                            temp.put("third_menu",thirdMenuArray);
                            for(int k=0;k<thirdSize;k++){
                                JSONObject tempThirdJsonObject = tempThirdArray.getJSONObject(k);
                                if(tempThirdJsonObject.getIntValue("pid") == second_menu_id){
                                    JSONObject thirdJsonObject = new JSONObject();
                                    thirdJsonObject.put("third_menu_name",tempThirdJsonObject.get("itemName"));
                                    //查询实际资金
                                    //查询资金
                                    JSONObject moneyObject = financeProgressService.getSingleCashFlow(companyCode,type,reportTime,tempThirdJsonObject.getIntValue("id"));
                                    thirdJsonObject.put("money",moneyObject == null ? "0":NumberUtil.unitTenThousand(moneyObject.getLongValue("realMoney")));
                                    thirdMenuArray.add(thirdJsonObject);
                                    break;
                                }
                            }
                            if(thirdMenuArray.isEmpty()){
                                temp.put("third_menu",null);
                            }
                        }
                    }
                }
                menuArray.add(firstJsonObject);
            }
            jsonObject = new JSONObject();
            jsonObject.put("menu",menuArray);
            String title = "";
            if(CashFlowTypeEnum.convertToEnum(type) == CashFlowTypeEnum.CASH_IN){
                title = CashFlowTypeEnum.CASH_IN.getInfo();
            }else if(CashFlowTypeEnum.convertToEnum(type) == CashFlowTypeEnum.CASH_OUT){
                title = CashFlowTypeEnum.CASH_OUT.getInfo();
            }
            jsonObject.put("title",title);
            //查询总金额
            Long totalMoney = financeProgressService.getSumCashFlowByCompanyCodeAndTypeAndMonth(companyCode, type, reportTime,endTime);
            jsonObject.put("total_money",totalMoney == null ? "0":NumberUtil.unitTenThousand(totalMoney)); 
        }
        return jsonObject;
    }
    
}
