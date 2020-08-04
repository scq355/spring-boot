package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.planb.common.PageInfo;
import cn.com.jcgroup.service.enums.BusinessTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.service.BusinessService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.JsonUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 出差
 * User: sunchangqing
 * Date: 2017-06-16
 * Time: 下午5:02
 */
@Service
public class BusinessManage {

    private Logger LOG = LoggerFactory.getLogger(BusinessManage.class);

    @Autowired
    BusinessService businessService;

    /**
     * 数据报表-接待数据
     * @author LiuYong  
     */
    public JSONObject buildReceptionData(String companyCode,int page, Date startTime, Date endTime) {
        JSONObject result = new JSONObject();
        JSONObject temp = businessService.queryReceptionByCompanyAndTime(companyCode,page, startTime, endTime);
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
                JSONObject tempJsonObject = tempJSONArray.getJSONObject(i);
                data.add(jsonObject);
                jsonObject.put("company",tempJsonObject.getString("guestCompany"));
                Date beginTime = tempJsonObject.getDate("startTime");
                jsonObject.put("date",beginTime == null ?"":DateUtil.formatDate("yyyy.MM.dd",beginTime));
                CompanyEnum companyEnum = CompanyEnum.convertToEnum(tempJsonObject.getString("company"));
                jsonObject.put("depart_name", companyEnum == null ? "" : companyEnum.getName());
                Long fee = tempJsonObject.getLong("fee");
                jsonObject.put("fee",fee == null ? 0 : NumberUtil.unitMoney(fee));
                jsonObject.put("number", tempJsonObject.getString("personNumber"));
                jsonObject.put("reason", tempJsonObject.getString("reason"));
                jsonObject.put("work_plan", tempJsonObject.getString("workPlan"));
            }
        }
        result.put("data",data);
        return result;
    }

    /**
     * 数据报表-出差数据
     *
     * @author LiuYong
     */
    public JSONObject buildTravelData(String companyCode, int page, Date startTime, Date endTime) {
        JSONObject result = new JSONObject();
        JSONObject temp = businessService.queryTravelByCompanyAndTime(companyCode, page, startTime, endTime);
        JSONArray data = null;
        if (temp != null && !temp.isEmpty()) {
            JSONArray tempJSONArray = temp.getJSONArray("data");
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(temp.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo", pageInfo);
            int length = tempJSONArray == null ? 0 : tempJSONArray.size();
            data = new JSONArray(length);
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject tempJsonObject = tempJSONArray.getJSONObject(i);
                data.add(jsonObject);
                //查询出差起始地
                String relationId = tempJsonObject.getString("relationId");
                String destination = "";
                if(StringUtils.isNotBlank(relationId)){
                    JSONArray jsonArray = businessService.queryDestination(relationId, BusinessTypeEnum.TRAVEL.getType());
                    if(jsonArray != null && !jsonArray.isEmpty()){
                        ArrayList<String> list = new ArrayList<>();
                        for (Object obj : jsonArray) {
                            JSONObject jsonObject1 = (JSONObject) obj;
                            list.add(jsonObject1.getString("departureLocation")+"--"+jsonObject1.getString("arriveLocation"));
                        }
                        destination = StringUtils.join(list,";");
                    }
                }
                jsonObject.put("destination", destination);
                Date beginTime = tempJsonObject.getDate("startTime");
                //开始时间
                String tempBeginTime = DateUtil.formatDate("yyyy-MM-dd", beginTime);
                Date deadTime = tempJsonObject.getDate("endTime");
                //结束时间
                String tempEndTime = DateUtil.formatDate("yyyy-MM-dd", deadTime);
                if(StringUtils.isBlank(tempBeginTime) || StringUtils.isBlank(tempEndTime)){
                    jsonObject.put("date", "");
                }else{
                    jsonObject.put("date", tempBeginTime + "~" + tempEndTime);
                }
                jsonObject.put("days", DateUtil.daysBetween(beginTime, deadTime));
                CompanyEnum companyEnum = CompanyEnum.convertToEnum(tempJsonObject.getString("company"));
                jsonObject.put("depart_name", companyEnum == null ? "" : companyEnum.getName());
                Long fee = tempJsonObject.getLong("fee");
                jsonObject.put("fee", fee == null ? 0 : NumberUtil.unitMoney(fee));
                jsonObject.put("number", tempJsonObject.getString("personNumber"));
                jsonObject.put("reason", tempJsonObject.getString("reason"));
            }
        }
        result.put("data", data);
        return result;
    }

    /**
     * 根据城市取前10条费用最高的数据
     *
     * @author LiuYong
     */
    public JSONObject buildTopTen(String cityName, Date start, Date end, String company) {
        JSONObject jsonObject = new JSONObject();
        List<String> companyList = CompanyEnum.covertToList(company);
        long totalMoney = 0;
        if (companyList != null && !companyList.isEmpty()) {
            JSONArray jsonArray = businessService.findTopTenByCity(cityName, start, end, companyList);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                int size = jsonArray.size();
                JSONArray result = new JSONArray();
                jsonObject.put("topTen", result);
                for (int i = 0; i < size; i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    JSONObject build = new JSONObject();
                    result.add(build);
                    build.put("date", DateUtil.formatDate("yyyy.MM.dd", temp.getDate("start_time")));
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(temp.getString("company"));
                    build.put("department", companyEnum == null ? "" : companyEnum.getName());
                    BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(temp.getString("type"));
                    build.put("type", businessTypeEnum == null ? "" : businessTypeEnum.getInfo());
                    Long fee = temp.getLong("fee");
                    long tempFee = fee == null ? 0 : fee;
                    build.put("price", fee == null ? 0 : NumberUtil.unitMoney(tempFee));
                }
            }
            //查询总费用
            long totalTravelMoney = businessService.findCostByCity(cityName,start,end,companyList,BusinessTypeEnum.TRAVEL.getType());
            long totalReceptionMoney = businessService.findCostByCity(cityName,start,end,companyList,BusinessTypeEnum.RECEPTION.getType());
            totalMoney = totalReceptionMoney + totalTravelMoney;
            jsonObject.put("totalMoney", NumberUtil.unitMoney(totalMoney));
        }
        return jsonObject;
    }

    /**
     * 按城市分组统计出差接待次数
     *
     * @author LiuYong
     */
    public JSONObject buildTravelTotalTimes(Date start, Date end, String company) {
        JSONObject resultObject = null;
        List<String> companyList = CompanyEnum.covertToList(company);
        if (companyList != null && !companyList.isEmpty()) {
            JSONArray tempArray = businessService.findTravelTotalTimes(start, end, companyList);
            if (tempArray != null && !tempArray.isEmpty()) {
                resultObject = new JSONObject();
                JSONArray cityList = new JSONArray();
                resultObject.put("city_list", cityList);
                Map<String, Integer> map = new HashMap<>();
                //统计接待次数
                for (Object object : tempArray) {
                    JSONObject tempObject = (JSONObject) object;
                    String key = tempObject.getString("locations");
                    int value = tempObject.getIntValue("totalTimes");
                    if (map.containsKey(key)) {
                        int originValue = map.get(key);
                        map.put(key, originValue + value);
                    } else {
                        map.put(key, value);
                    }
                }
                int max = 0;
                int min = 0;
                //获取value最大值
                for (String cityName : map.keySet()) {
                    int value = map.get(cityName);
                    JSONObject cityJsonObject = new JSONObject();
                    cityJsonObject.put("city_name", cityName);
                    cityJsonObject.put("size", value);
                    cityList.add(cityJsonObject);
                    max = max > value ? max : value;
                    if( min == 0){
                        min = value;
                    }else{
                        min = min < value ? min : value;
                    }
                }
                resultObject.put("max_size", max);
                resultObject.put("min_size", min);
            }
        }
        return resultObject;
    }

    /**
     * 出差/接待分析
     *
     * @author LiuYong
     */
    public JSONObject buildTravelAndReception(Date start, Date end, String company, String type) {
        JSONObject jsonObject = new JSONObject();
        //构建模板
        List<String> listName = CompanyEnum.getCompanyName(CompanyEnum.ALL.getCode());
        JSONArray travelCount;
        JSONArray travelMoney;
        JSONArray receptNumber = null;
        JSONArray keyWords;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
        if (businessTypeEnum != null && listName != null && !listName.isEmpty()) {
            int length = listName.size();
            keyWords = JsonUtil.initJsonArray(length, "");
            jsonObject.put("key_word", keyWords);
            jsonObject.put("xAlias", listName);
            travelCount = JsonUtil.initJsonArray(length, 0);
            travelMoney = JsonUtil.initJsonArray(length, "0");
            if (businessTypeEnum == BusinessTypeEnum.TRAVEL) {
                jsonObject.put("travel_count", travelCount);
                jsonObject.put("travel_money", travelMoney);
            } else if (businessTypeEnum == BusinessTypeEnum.RECEPTION) {
                receptNumber = JsonUtil.initJsonArray(length, 0);
                jsonObject.put("recept_times", travelCount);
                jsonObject.put("recept_number", receptNumber);
                jsonObject.put("recept_money", travelMoney);
            }
        } else {
            return jsonObject;
        }
        List<String> companyList = CompanyEnum.covertToList(company);
        if (companyList != null && !companyList.isEmpty()) {
            JSONArray jsonArray = businessService.findTravelByCompanyGroup(start, end, companyList, businessTypeEnum.getType());
            if (jsonArray != null && !jsonArray.isEmpty()) {
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    String companyCode = temp.getString("company");
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(companyCode);
                    int index = CompanyEnum.index(companyEnum);
                    if (index != -1) {
                        travelCount.set(index, temp.get("totalTimes"));
                        Long totalFee = temp.getLong("totalFee");
                        travelMoney.set(index, totalFee != null ? NumberUtil.unitMoney(totalFee) : "0");
                        if (businessTypeEnum == BusinessTypeEnum.RECEPTION) {
                            receptNumber.set(index, temp.getLong("totalNumbers"));
                        }
                    }
                }
            }
            //关键词提取
            JSONArray keyArray = businessService.findKeyWords(start, end, companyList, businessTypeEnum.getType());
            if (keyArray != null && !keyArray.isEmpty()) {
                Map<String, List<String>> words = new HashMap<>();
                //分部门组装词语
                for (Object object : keyArray) {
                    JSONObject keyObject = (JSONObject) object;
                    String companyName = keyObject.getString("company");
                    String wordValue = keyObject.getString("keyWords");
                    if (words.containsKey(companyName)) {
                        words.get(companyName).add(wordValue);
                    } else {
                        List<String> wordList = new ArrayList<>();
                        wordList.add(wordValue);
                        words.put(companyName, wordList);
                    }
                }
                //构建词频
                for (String key : words.keySet()) {
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(key);
                    int index = CompanyEnum.index(companyEnum);
                    if (index != -1) {
                        //排序词条大小
                        Map<String, Integer> tempMap = new HashMap<>();
                        List<String> list = words.get(key);
                        for (String str : list) {
                            try {
                                JSONArray jsonArray1 = JSONArray.parseArray(str);
                                for (Object object : jsonArray1) {
                                    String tempKey = String.valueOf(object);
                                    if (tempMap.containsKey(tempKey)) {
                                        Integer val = tempMap.get(tempKey);
                                        tempMap.put(tempKey, val + 1);
                                    } else {
                                        tempMap.put(tempKey, 1);
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("[业务高频词汇]解析错误,words=" + str, e);
                            }
                        }
                        //排序此条，取前17条记录
                        List<Map.Entry<String, Integer>> resultList = new ArrayList<>(tempMap.entrySet());
                        Collections.sort(resultList, new Comparator<Map.Entry<String, Integer>>() {
                            @Override
                            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                return (o2.getValue() - o1.getValue());
                            }
                        });
                        JSONObject keyWordsJSONObject = new JSONObject();
                        int resultSize = resultList.size();
                        int length = resultSize < 17 ? resultSize : 17;
                        List<String> keyList = new ArrayList<>(length);
                        for (int i = 0; i < length; i++) {
                            keyList.add(resultList.get(i).getKey());
                        }
                        keyWordsJSONObject.put("name", keyList);
                        keyWords.set(index, keyWordsJSONObject);
                    }
                }

            }
        }
        return jsonObject;
    }
}
