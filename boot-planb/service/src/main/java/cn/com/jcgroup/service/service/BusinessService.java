package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import static cn.com.jcgroup.service.constant.ServiceIdentifier.DEFAULT_ADMIN_PAGE_SIZE;
import cn.com.jcgroup.service.domain.PbReception;
import cn.com.jcgroup.service.domain.PbTravel;
import cn.com.jcgroup.service.domain.PbTravelReceptionDetail;
import cn.com.jcgroup.service.enums.BusinessTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.repositories.PbReceptionRepository;
import cn.com.jcgroup.service.repositories.PbTravelReceptionRepository;
import cn.com.jcgroup.service.repositories.PbTravelRepository;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 业务分析
 * @author LiuYong on 17/6/16 上午12:45.
 */
@Service
public class BusinessService {
    
    private static final Logger LOG = LoggerFactory.getLogger(BusinessService.class);
    
    @Autowired
    private PbTravelRepository pbTravelRepository;
    @Autowired
    private PbReceptionRepository pbReceptionRepository;
    @Autowired
    private PbTravelReceptionRepository pbTravelReceptionRepository;
    
    /**
     * 根据部门和时间段查询出差记录
     * @author LiuYong  
     */
    public JSONObject queryTravelByCompanyAndTime(String company, int page,Date start, Date end){
        JSONObject result;
        JSONArray jsonArray = null;
        if(StringUtils.isBlank(company) || start == null || end == null){
            return null;
        }
        CompanyEnum companyEnum = CompanyEnum.convertToEnum(company);
        if(companyEnum == null){
            LOG.error("部门类别转换错误，company={}",company);
            return null;
        }
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC, "startTime"));
        Slice<PbTravel> slice = pbTravelRepository.findByCompanyAndDate(CompanyEnum.covertToList(company),pageable, start, end);
        if(slice != null && slice.hasContent()){
            jsonArray = new JSONArray();
            JSONObject jsonObject;
            List<PbTravel> list = slice.getContent();
            int size = list.size();
            for (int i=0;i<size;i++){
                PbTravel pbTravel = list.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("id",pbTravel.getId());
                jsonObject.put("company",pbTravel.getCompany());
                jsonObject.put("personNumber",pbTravel.getPersonNumber());
                jsonObject.put("startTime",pbTravel.getStartTime());
                jsonObject.put("endTime",pbTravel.getEndTime());
                jsonObject.put("fee",pbTravel.getFee());
                jsonObject.put("reason",pbTravel.getReason());
                jsonObject.put("relationId",pbTravel.getRelationId());
                jsonArray.add(jsonObject);
            }
        }
        result = new JSONObject();
        result.put("hasNext",slice != null && slice.hasNext());
        result.put("data",jsonArray);
        return result;
    }

    /**
     * 根据部门和时间段查询接待记录
     * @author LiuYong
     */
    public JSONObject queryReceptionByCompanyAndTime(String company,int page, Date start, Date end){
        JSONObject result;
        JSONArray jsonArray = null;
        if(StringUtils.isBlank(company) || start == null || end == null){
            return null;
        }
        CompanyEnum companyEnum = CompanyEnum.convertToEnum(company);
        if(companyEnum == null){
            LOG.error("部门类别转换错误，company={}",company);
            return null;
        }
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC, "startTime"));
        Slice<PbReception> slice = pbReceptionRepository.findByCompanyAndDate(CompanyEnum.covertToList(company),pageable, start, end);
        if(slice != null && slice.hasContent()){
            jsonArray = new JSONArray();
            JSONObject jsonObject;
            List<PbReception> list = slice.getContent();
            int size = slice.getSize();
            for(int i=0;i<size;i++){
                PbReception pbReception = list.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("id",pbReception.getId());
                jsonObject.put("company",pbReception.getCompany());
                jsonObject.put("personNumber",pbReception.getPersonNumber());
                jsonObject.put("startTime",pbReception.getStartTime());
                jsonObject.put("endTime",pbReception.getEndTime());
                jsonObject.put("fee",pbReception.getFee());
                jsonObject.put("workPlan",pbReception.getWorkPlan());
                jsonObject.put("guestCompany",pbReception.getGuestCompany());
                jsonObject.put("reason",pbReception.getReason());
                jsonObject.put("relationId",pbReception.getRelationId());
                jsonObject.put("guestCompany",pbReception.getGuestCompany());
                jsonArray.add(jsonObject);
            }
        }
        result = new JSONObject();
        result.put("hasNext",slice != null && slice.hasNext());
        result.put("data",jsonArray);
        return result;
    }
    
    /**
     * 查询出发地和目的地
     * @author LiuYong  
     */
    public JSONArray queryDestination(String relationId,String type){
        JSONArray jsonArray = null;
        if(StringUtils.isBlank(relationId)){
            LOG.error("核销relationId不能为空");
            return null;
        }
        List<Object[]> list = pbTravelReceptionRepository.findByRelationId(relationId,type);
        if(list != null && !list.isEmpty()){
            jsonArray = new JSONArray();
            for (Object[] objects:list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("departureLocation",objects[0]);
                jsonObject.put("arriveLocation",objects[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
    
    /**
     * 根据城市取前10条费用最高的数据
     * @author LiuYong  
     */
    public JSONArray findTopTenByCity(String cityName,Date start,Date end,List<String> company){
        JSONArray jsonArray = null;
        List<Object[]> list = pbTravelReceptionRepository.findTopTenByCity(cityName, start, end, company);
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray();
            for(int i=0;i<size;i++){
                Object[] objects = list.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonArray.add(jsonObject);
                jsonObject.put("start_time",objects[0]);
                jsonObject.put("company",objects[1]);
                jsonObject.put("type",objects[2]);
                jsonObject.put("fee",objects[3]);
            }
        }
        return jsonArray;
    }
    
    /**
     * 根据城市统计出差／接待费用
     * @author LiuYong  
     */
    public long findCostByCity(String cityName,Date start,Date end,List<String> company,String type){
        long totalFee = 0L;
        BusinessTypeEnum typeEnum = BusinessTypeEnum.convertToEnum(type);
        if(typeEnum != null){
            if(typeEnum == BusinessTypeEnum.TRAVEL){
                Long fee = pbTravelReceptionRepository.countTravelTotalFeeByCity(cityName, start, end, company);
                totalFee = fee ==null ? 0L:fee;
            }else if(typeEnum == BusinessTypeEnum.RECEPTION){
                Long fee = pbTravelReceptionRepository.countReceptionTotalFeeByCity(cityName, start, end, company);
                totalFee = fee ==null ? 0L:fee;
            }
        }
        return totalFee;
    }

    /**
     * 根据时间段和部门查询飞行线路
     *
     * @author LiuYong
     */
    public JSONArray findTravelRecepionLineByTime(Date start, Date end, List<String> company) {
        JSONArray jsonArray = null;
        List<Object[]> list = pbTravelReceptionRepository.findTravelRecepionLineByTime(start, end, company);
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] objects : list) {
                JSONObject jsonObject = new JSONObject();
                jsonArray.add(jsonObject);
                jsonObject.put("from", objects[0]);
                jsonObject.put("to", objects[1]);
                jsonObject.put("type", objects[2]);
            }
        }
        return jsonArray;
    }

    /**
     * 按城市分组统计出差接待次数
     *
     * @author LiuYong
     */
    public JSONArray findTravelTotalTimes(Date start, Date end, List<String> company){
        JSONArray jsonArray = null;
        List<Object[]> list = pbTravelReceptionRepository.countTravelTotalTimes(start, end, company);
        if(list != null && !list.isEmpty()){
            jsonArray = new JSONArray();
            for(Object[] obj : list){
                JSONObject jsonObject = new JSONObject();
                jsonArray.add(jsonObject);
                jsonObject.put("locations",obj[0]);
                jsonObject.put("totalTimes",obj[1]);
            }
        }
        return jsonArray;
    }

    /**
     * 根据公司分组统计出差/接待数据
     *
     * @author LiuYong
     */
    public JSONArray findTravelByCompanyGroup(Date start, Date end, List<String> company, String type) {
        JSONArray jsonArray = null;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
        if (businessTypeEnum != null) {
            List<Object[]> list = null;
            if (businessTypeEnum == BusinessTypeEnum.TRAVEL) {
                list = pbTravelRepository.countTravelByCompanyGroup(start, end, company);
            } else if (businessTypeEnum == BusinessTypeEnum.RECEPTION) {
                list = pbReceptionRepository.countReceptionByCompanyGroup(start, end, company);
            }
            if (list != null && !list.isEmpty()) {
                int size = list.size();
                jsonArray = new JSONArray(size);
                for (Object[] obj : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.add(jsonObject);
                    jsonObject.put("company", obj[0]);
                    jsonObject.put("totalTimes", obj[1]);
                    jsonObject.put("totalFee", obj[2]);
                    if (businessTypeEnum == BusinessTypeEnum.RECEPTION) {
                        jsonObject.put("totalNumbers", obj[3]);
                    }
                }
            }
        }
        return jsonArray;
    }

    /**
     * 根据公司分组统计出差/接待词频
     *
     * @author LiuYong
     */
    public JSONArray findKeyWords(Date start, Date end, List<String> company, String type) {
        JSONArray jsonArray = null;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
        if (businessTypeEnum != null) {
            List<Object[]> list = null;
            if (businessTypeEnum == BusinessTypeEnum.TRAVEL) {
                list = pbTravelRepository.findKeyWords(start, end, company);
            } else if (businessTypeEnum == BusinessTypeEnum.RECEPTION) {
                list = pbReceptionRepository.findKeyWords(start, end, company);
            }
            if (list != null && !list.isEmpty()) {
                int size = list.size();
                jsonArray = new JSONArray(size);
                for (Object[] obj : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.add(jsonObject);
                    jsonObject.put("company", obj[0]);
                    jsonObject.put("keyWords", obj[1]);
                }
            }
        }
        return jsonArray;
    }

    /**
     * 分页查询员工出差或接待信息
     *
     * @author LiuYong
     */
    public JSONObject findTravelReceptionInfo(List<String> departmentList, int page,String type){
        JSONObject result = null;
        if(departmentList !=null && !departmentList.isEmpty()){
            JSONArray jsonArray = null;
            Pageable pageable = new PageRequest(page-1,DEFAULT_ADMIN_PAGE_SIZE,new Sort(Sort.Direction.DESC,"startTime"));
            Slice slice = null;
            BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
            if(businessTypeEnum != null){
                if(BusinessTypeEnum.TRAVEL == businessTypeEnum){
                    slice =pbTravelRepository.findByCompany(departmentList,pageable);
                }else if(BusinessTypeEnum.RECEPTION == businessTypeEnum){
                    slice = pbReceptionRepository.findByCompany(departmentList,pageable);
                }
                if(slice != null && slice.hasContent()){
                    int size = slice.getSize();
                    jsonArray = new JSONArray(size);
                    for(int i=0;i<size;i++){
                        jsonArray.add(slice.getContent().get(i));
                    }
                }
            }
            result = new JSONObject();
            result.put("hasNext",slice != null && slice.hasNext());
            result.put("data",jsonArray);
        }
        return result;
    }

    /**
     * 修改出差词频信息
     *
     * @author LiuYong
     */
    @Transactional
    public boolean updateKeyWords(String[] keyWords, String relationId,String type) {
        if (StringUtils.isNotBlank(relationId)) {
           if(keyWords != null && keyWords.length > 0){
               BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
               if(businessTypeEnum == BusinessTypeEnum.TRAVEL){
                   return pbTravelRepository.updateKeyWords(JSON.toJSONString(keyWords),relationId) > 0;
               }else if(businessTypeEnum == BusinessTypeEnum.RECEPTION){
                   return pbReceptionRepository.updateKeyWords(JSON.toJSONString(keyWords),relationId) > 0;
               }
           }
        }
        return false;
    }

    /**
     * 修改出差地映射信息
     *
     * @author LiuYong
     */
    @Transactional
    public boolean updateTravelLocation(String arriveMapLocation, String departureMapLocation, int id) {
        return pbTravelReceptionRepository.updateTravelLocation(arriveMapLocation, departureMapLocation, id) > 0;
    }


    /**
     * 查询出差或接待明细信息
     *
     * @author LiuYong
     */
    public JSONArray findByRelationId(String relationId,String type){
        JSONArray jsonArray = null;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.convertToEnum(type);
        if(businessTypeEnum == null){
            LOG.error("[查询出差或接待明细信息]type类型转换错误");
        }
        List<PbTravelReceptionDetail> list =pbTravelReceptionRepository.findByRelationId(relationId,type,new Sort(Sort.Direction.DESC,"startTime"));
        if(list != null && !list.isEmpty()){
            int size = list.size();
            jsonArray = new JSONArray(size);
            for(int i=0;i<size;i++){
                PbTravelReceptionDetail pbTravel = list.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("arriveLocation",pbTravel.getArriveLocation());
                jsonObject.put("arriveMapLocation", pbTravel.getArriveMapLocation());
                jsonObject.put("departureLocation",pbTravel.getDepartureLocation());
                jsonObject.put("departureMapLocation",pbTravel.getDepartureMapLocation());
                jsonObject.put("fee", NumberUtil.unitMoney(pbTravel.getFee()));
                jsonObject.put("id",String.valueOf(pbTravel.getId()));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

}
