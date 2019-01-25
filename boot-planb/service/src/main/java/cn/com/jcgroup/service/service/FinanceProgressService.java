package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.enums.CashFlowTypeEnum;
import cn.com.jcgroup.service.repositories.PbCashFlowReponsitory;
import cn.com.jcgroup.service.repositories.PbCashReportItemReponsitory;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 资金进度相关
 * @author LiuYong on 17/6/9 上午11:27.
 */
@Service
public class FinanceProgressService {
    
    private static final Logger LOG = LoggerFactory.getLogger(FinanceProgressService.class);
    
    @Autowired
    private PbCashFlowReponsitory pbCashFlowReponsitory;
    @Autowired
    private PbCashReportItemReponsitory pbCashReportItemReponsitory;

    /**
     * 根据时间段和资金类别统计预期资金和实际资金
     * @author LiuYong
     */
    public JSONArray getMoneyByCompanyCodeAndReportTimeBetween(String companyCode,String type){
        JSONArray jsonArray = null;
        if(StringUtils.isBlank(companyCode)){
            return  null;
        }
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        Date start = DateUtil.getFirstDay(1);
        Date end = DateUtil.getFirstDay(12);
        List<Object[]> list = pbCashFlowReponsitory.countByCompanyCodeAndReportTimeBetween(companyCode,type,start,end,new Sort("reportTime"));
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            for(int i=0;i<size;i++){
                Object[] temp = list.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("reportTime",temp[0]);
                jsonObject.put("totalRealMoney",temp[1]);
                jsonObject.put("totalExpectMoney",temp[2]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类型查询第二层级财务条目
     * @author LiuYong
     */
    public JSONArray getSecondLevelCashFlowByType(String type){
        JSONArray jsonArray = null;
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        List<Object[]> list = pbCashReportItemReponsitory.findSecondLevelCashFlowByType(type);
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            JSONObject jsonObject;
            for(int i=0;i<size;i++){
                jsonObject = new JSONObject();
                Object[] object = list.get(i);
                jsonObject.put("id",object[0]);
                jsonObject.put("itemName",object[1]);
                jsonObject.put("pid",object[2]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类别按照时间查询各财务条目预期资金和实际资金
     * @author LiuYong
     */
    public JSONArray findCashFlowByItemIdAndTypeAndCompanyCode(String companyCode,String type,List<Integer> items){
        JSONArray jsonArray = null;
        if(StringUtils.isBlank(companyCode)){
            return  null;
        }
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        Date start = DateUtil.getFirstDay(1);
        Date end = DateUtil.getFirstDay(12);
        List<Object[]> list = pbCashFlowReponsitory.findCashFlowByItemIdAndTypeAndCompanyCode(companyCode,type,start,end,items);
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            JSONObject jsonObject;
            for(int i=0;i<size;i++){
                jsonObject = new JSONObject();
                Object[] object = list.get(i);
                jsonObject.put("itemId",object[0]);
                jsonObject.put("realMoney",object[1]);
                jsonObject.put("expectMoney",object[2]);
                jsonObject.put("reportTime",object[3]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类型查询第一层级财务条目
     * @author LiuYong
     */
    public JSONArray getFirstLevelCashFlowByType(String type){
        JSONArray jsonArray = null;
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        List<Object[]> list = pbCashReportItemReponsitory.findFirstLevelCashFlowByType(type,new Sort("sortOrder"));
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            JSONObject jsonObject;
            for(int i=0;i<size;i++){
                jsonObject = new JSONObject();
                Object[] object = list.get(i);
                jsonObject.put("id",object[0]);
                jsonObject.put("itemName",object[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类型查询第三层级财务条目
     * @author LiuYong
     */
    public JSONArray getThirdLevelCashFlowByType(String type,List<Integer> items){
        JSONArray jsonArray = null;
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        List<Object[]> list = pbCashReportItemReponsitory.findThirdLevelCashFlowByType(type,items,new Sort("pid","sortOrder"));
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            JSONObject jsonObject;
            for(int i=0;i<size;i++){
                jsonObject = new JSONObject();
                Object[] object = list.get(i);
                jsonObject.put("id",object[0]);
                jsonObject.put("itemName",object[1]);
                jsonObject.put("pid",object[2]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据资金类别按照时间统计合营公司实际资金
     *
     * @author LiuYong
     */
    public Long getSumCashFlowByCompanyCodeAndTypeAndMonth(String companyCode, String type, Date reportTime, Date enTime) {
        if(StringUtils.isBlank(companyCode)){
            return  null;
        }
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        return pbCashFlowReponsitory.countSumCashFlowByCompanyCodeAndTypeAndMonth(companyCode,type,reportTime,enTime);
    }

    /**
     * 根据资金类别按照时间查询单个财务条目预期资金和实际资金
     *
     * @author LiuYong
     */
    public JSONObject getSingleCashFlow(String companyCode, String type, Date reportTime, int itemId) {
        JSONObject jsonObject = null;
        if(StringUtils.isBlank(companyCode)){
            return  null;
        }
        CashFlowTypeEnum cashFlowTypeEnum = CashFlowTypeEnum.convertToEnum(type);
        if(cashFlowTypeEnum == null){
            LOG.error("[资金进度service]资金流类别转换错误,type={}",type);
            return null;
        }
        List<Object[]> list = pbCashFlowReponsitory.findSingleCashFlow(companyCode, type, reportTime, itemId);
        if (list != null && !list.isEmpty()) {
            Object[] o = list.get(0);
            jsonObject = new JSONObject();
            jsonObject.put("realMoney",o[0]);
            jsonObject.put("expectMoney",o[1]);
        }
        return jsonObject;
    }
}
