package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbPersonFlowInfo;
import cn.com.jcgroup.service.domain.PbPersonnelData;
import cn.com.jcgroup.service.enums.AgeTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.enums.OaCompanyEnum;
import cn.com.jcgroup.service.enums.SalaryTypeEnum;
import cn.com.jcgroup.service.enums.StaffFlowTypeEnum;
import cn.com.jcgroup.service.enums.WorkTimeTypeEnum;
import cn.com.jcgroup.service.repositories.PbPersonFlowInfoRepository;
import cn.com.jcgroup.service.repositories.PbPersonnelDataRepository;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.StringConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 人员流动
 * @author sunchangqing
 */
@Service
public class PersonnelFlowService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonnelFlowService.class);

    @Autowired
    private PbPersonnelDataRepository pbPersonnelDataRepository;
    @Autowired
    private PbPersonFlowInfoRepository pbPersonFlowInfoRepository;

    /**
     * 人员流动
     */
    public JSONObject queryStaffByDepartCode(String departCode, Date beginDate, Date endDate, int page) {
        if (StringUtils.isBlank(departCode)) {
            return null;
        }
        if (page < 1) {
            //数据库中page从零开始
            LOG.error("[项目服务]请求页面数值不合法，page={}", page);
        }
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC, "entryTime"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        List<PbPersonnelData> stuffList = pbPersonnelDataRepository.findAllByDepartment(departCode, beginDate, endDate, pageable);
        JSONArray jsonArray = new JSONArray();
        JSONObject resJsonObj = new JSONObject();
        int leavedStuff = 0;
        int realStuff = 0;
        for (PbPersonnelData stuff : stuffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("age", stuff.getBirthday());
            jsonObject.put("degree", stuff.getAcademic());
            jsonObject.put("depart_name", stuff.getDepartment());
            jsonObject.put("label", stuff.getLabel());
            jsonObject.put("post", stuff.getPositionLevel());
            jsonObject.put("real_name", stuff.getId());
            jsonObject.put("sex", stuff.getSex());
            jsonObject.put("staff_code", stuff.getStuffCode());
            jsonObject.put("staff_level", stuff.getPositionLevel());
            jsonObject.put("type", stuff.getStatus());
            if (stuff.getStatus().equals("")) {
                leavedStuff++;
            } else {
                realStuff++;
            }
            jsonArray.add(jsonObject);
        }
        resJsonObj.put("entry_total", realStuff);
        resJsonObj.put("leave_total", leavedStuff);
        resJsonObj.put("staff", jsonArray);
        resJsonObj.put("time", dateFormat.format(new Date()));
        return resJsonObj;
    }

    /**
     * 基本信息-性别
     */
    public JSONArray querySexByDeptCode(List<String> deptCode) {
        JSONArray jsonArray = null;
        if(deptCode == null || deptCode.isEmpty()){
            return null;
        }
        List<Object[]> sexInfo = pbPersonnelDataRepository.findSexByDepartmentCode(deptCode);
        JSONObject jsonObject;
        if(sexInfo != null && !sexInfo.isEmpty()){
            jsonArray = new JSONArray();
            for(Object[] objects : sexInfo){
                jsonObject = new JSONObject();
                jsonObject.put("num",objects[0]);
                jsonObject.put("sex",objects[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 基本信息-学历
     */
    public JSONArray queryAcademicByDeptCode(List<String> deptCode) {
        JSONArray jsonArray = null;
        if(deptCode == null || deptCode.isEmpty()){
            return null;
        }
        List<Object[]> academicInfo = pbPersonnelDataRepository.findAcademicByDepartmentCode(deptCode);
        JSONObject jsonObject;
        if(academicInfo != null && !academicInfo.isEmpty()){
            jsonArray = new JSONArray();
            for(Object[] objects : academicInfo){
                jsonObject = new JSONObject();
                jsonObject.put("num",objects[0]);
                jsonObject.put("academic",objects[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
    
    public Integer queryTotalStaff(List<String> deptCode){
        return pbPersonnelDataRepository.countByDepartmentCode(deptCode);
    }


    /**
     * 基本信息-年龄
     */
    public Map<String,Integer> queryAgeByDeptCode(List<String> deptCode){
        if(deptCode == null || deptCode.isEmpty()){
            return null;
        }
        List<Date> ageInfo = pbPersonnelDataRepository.findAgeByDepartmentCode(deptCode);
        Map<String,Integer> map = null;
        if(ageInfo != null && !ageInfo.isEmpty()){
            map = new HashMap<String,Integer>();
            for(Date temp : ageInfo){
                //年龄计算
                if(temp == null){
                    continue;
                }
                int age = DateUtil.getAge(temp);
                AgeTypeEnum ageTypeEnum = null;
                if(age>=20 && age<=30){
                    ageTypeEnum = AgeTypeEnum.FIRST_LEVEL;
                }else if(age >=31 && age<=40){
                    ageTypeEnum = AgeTypeEnum.SECOND_LEVEL;
                }else if(age>=41 && age<=50){
                    ageTypeEnum = AgeTypeEnum.THIRD_LEVEL;
                }else if(age > 50){
                    ageTypeEnum = AgeTypeEnum.FOURTH_LEVEL;
                }
                if(ageTypeEnum != null){
                    String key = ageTypeEnum.getType();
                    if(map.containsKey(key)){
                        int value = map.get(key);
                        map.put(key,++value);
                    }else{
                        map.put(key,1);
                    }
                }
            }
        }
        return map;
    }



    /**
     * 查询本公司工作年限 或 工作年限
     * 
     * type = '1'   本公司工作年限
     * type = '2'   累计工作年限
     * 
     * @author LiuYong  
     */
    public Map<String,Integer> queryWorkTime(List<String> deptCode,String type) {
        if(deptCode == null || deptCode.isEmpty() || StringUtils.isBlank(type)){
            return null;
        }
        List<Date> list = null;
        if("1".equalsIgnoreCase(type)){
            list = pbPersonnelDataRepository.findEntryTimeByDepartmentCode(deptCode);
        }else if("2".equalsIgnoreCase(type)){
            list = pbPersonnelDataRepository.findFirstWorkTimeByDepartmentCode(deptCode);
        }
        Map<String,Integer> map = null;
        if(list != null && !list.isEmpty()){
            map = new HashMap<String,Integer>();
            for(Date temp : list){
                //工龄计算
                if(temp == null){
                    continue;
                }
                int age = DateUtil.getWorkYears(temp);
                WorkTimeTypeEnum workTimeTypeEnum;
                if(age<=1){
                    workTimeTypeEnum = WorkTimeTypeEnum.FIRST_LEVEL;
                }else if(age<=3){
                    workTimeTypeEnum = WorkTimeTypeEnum.SECOND_LEVEL;
                }else if(age<=5){
                    workTimeTypeEnum = WorkTimeTypeEnum.THIRD_LEVEL;
                }else if(age<=10){
                    workTimeTypeEnum = WorkTimeTypeEnum.FOURTH_LEVEL;
                }else{
                    workTimeTypeEnum = WorkTimeTypeEnum.FIFTH_LEVEL;
                }
                String key = workTimeTypeEnum.getType();
                if(map.containsKey(key)){
                    int value = map.get(key);
                    map.put(key,++value);
                }else{
                    map.put(key,1);
                }
            }
        }
        return map;
    }

    /**
     * 年薪待遇
     */
    public Map<String,Integer> queryAnnualSalary(List<String>  deptCode) {
        if(deptCode == null || deptCode.isEmpty()){
            return null;
        }
        List<Long> list = pbPersonnelDataRepository.findAnnualSalaryByDepartmentCode(deptCode);
        Map<String,Integer> map = null;
        if(list != null && !list.isEmpty()){
            map = new HashMap<String,Integer>();
            for(Long temp : list){
                if(temp == null){
                    continue;
                }
                long money = temp / 100; //以分为单位
                SalaryTypeEnum salaryTypeEnum;
                if(money<=50000){
                    salaryTypeEnum = SalaryTypeEnum.FIRST_LEVEL;
                }else if(money<=100000){
                    salaryTypeEnum = SalaryTypeEnum.SECOND_LEVEL;
                }else if(money<=30*10000){
                    salaryTypeEnum = SalaryTypeEnum.THIRD_LEVEL;
                }else if(money<=60*10000){
                    salaryTypeEnum = SalaryTypeEnum.FOURTH_LEVEL;
                }else{
                    salaryTypeEnum = SalaryTypeEnum.FIFTH_LEVEL;
                }
                String key = salaryTypeEnum.getType();
                if(map.containsKey(key)){
                    int value = map.get(key);
                    map.put(key,++value);
                }else{
                    map.put(key,1);
                }
            }
        }
        return map;
    }

    /**
     * 部门列表
     */
    public JSONArray queryDepartments() {
        JSONArray jsonArray = new JSONArray();
        for (CompanyEnum companyEnum : CompanyEnum.values()) {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(companyEnum.getCode(), companyEnum.getName());
            jsonObject.put("depart_code", companyEnum.getCode());
            jsonObject.put("depart_name", companyEnum.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    
    /**
     * 人员信息批量插入
     * @author LiuYong  
     */
    @Transactional
    public boolean batchAddStaff(List<Map<String, Object>> staffList) {
        if (staffList != null && !staffList.isEmpty()) {
            int size = staffList.size();
            List<PbPersonnelData> list = new ArrayList<>(size);
            try {
                for (int i = 0; i < size; i++) {
                    Map<String, Object> map = staffList.get(i);
                    PbPersonnelData pbPersonnelData = new PbPersonnelData();
                    pbPersonnelData.setId(pbPersonnelDataRepository.findSeqId());
                    pbPersonnelData.setPositionLevel(StringConvertUtil.convertToString(map.get("positionLevel")));
                    pbPersonnelData.setSex(StringConvertUtil.convertToString(map.get("sex")));
                    //部门映射转换
                    Object department = map.get("department");
                    if(department != null){
                        BigDecimal tempDepartment = (BigDecimal) department;
                        OaCompanyEnum oaCompanyEnum = OaCompanyEnum.convertToEnum(tempDepartment.intValue());
                        pbPersonnelData.setDepartment(oaCompanyEnum == null ? null:oaCompanyEnum.getDepartment());
                    }
                    pbPersonnelData.setStuffCode(StringConvertUtil.convertToString(map.get("stuffCode")));
                    pbPersonnelData.setStaffName(StringConvertUtil.convertToString(map.get("stuffName")));
                    pbPersonnelData.setAcademic(StringConvertUtil.convertToString(map.get("degree")));
                    try{
                        Object birthDay = map.get("birthday");
                        pbPersonnelData.setBirthday(birthDay == null ? null : DateUtil.covertToDate("yyyy-MM-dd", String.valueOf(birthDay)));
                        Object entryTime = map.get("entryTime");
                        pbPersonnelData.setEntryTime(entryTime == null ? null : DateUtil.covertToDate("yyyy-MM-dd", String.valueOf(entryTime)));
                        Object workTime = map.get("workTime");
                        pbPersonnelData.setFirstWorkTime(workTime == null ? null : DateUtil.covertToDate("yyyy-MM-dd", String.valueOf(workTime)));
                    }catch (Exception e){
                        LOG.error("[人员信息]日期格式转换失败,info="+JSON.toJSONString(map),e);
                    }
                    pbPersonnelData.setPositions(StringConvertUtil.convertToString(map.get("positions")));
                    pbPersonnelData.setStatus(StringConvertUtil.convertToString(map.get("status")));
                    pbPersonnelData.setWorkSite(StringConvertUtil.convertToString(map.get("workSite")));
                    BigDecimal id = (BigDecimal) map.get("id");
                    pbPersonnelData.setRelationId(id.intValue());
                    list.add(pbPersonnelData);
                }
                pbPersonnelDataRepository.save(list);
            } catch (Exception e) {
                LOG.error("[人员信息]批量插入失败,info=" + JSON.toJSONString(staffList), e);
                return false;
            }
        }
        return true;
    }
    
    /**
     * 查询上一次同步人员信息id
     * @author LiuYong  
     */
    public int queryLastStaffId(){
        Integer id = pbPersonnelDataRepository.findMaxRelationId();
        return id == null ? 0 : id;
    }

    /**
     * 查询上一次同步人员异动信息时间
     * @author LiuYong
     */
    public Date queryLastModifyTime(){
        return pbPersonFlowInfoRepository.findLastModifyTime();
    }

    /**
     * 查询用户异动流水是否存在
     * @author LiuYong
     */
    public boolean queryRelationIdExist(String relationId){
        return pbPersonFlowInfoRepository.findRelationIdExist(relationId) > 0;
    }

    /**
     * 更新人员异动信息
     * @author LiuYong
     */
    @Transactional
    public void updatePersonFlowInfo(Map<String,Object> personInfo){
        if(personInfo != null && !personInfo.isEmpty()){
            PbPersonFlowInfo pbPersonFlowInfo = new PbPersonFlowInfo();
            try{
                Object endTime = personInfo.get("endTime");
                pbPersonFlowInfo.setEndTime(endTime == null ? null : DateUtil.parseDate( String.valueOf(endTime),"yyyy-MM-dd"));
                pbPersonFlowInfo.setIsEnd(StringConvertUtil.convertToString(personInfo.get("isEnd")));
                pbPersonFlowInfo.setRelationId(StringConvertUtil.convertToString(personInfo.get("relationId")));
                Object modifyTime = personInfo.get("modifyTime");
                pbPersonFlowInfo.setModifyTime(modifyTime == null ? null : DateUtil.parseDate( String.valueOf(modifyTime),"yyyy-MM-dd HH:mm:ss"));
                pbPersonFlowInfoRepository.updatePersonFlowInfo(pbPersonFlowInfo);
            }catch (Exception e){
                LOG.error("[人员异动信息]更新失败,info="+ JSON.toJSONString(personInfo),e);
            }
        }
    }
    
    /**
     * 增加人员异动信息
     * @author LiuYong  
     */
    @Transactional
    public void addPersonFlowInfo(Map<String,Object> personInfo){
        if(personInfo != null && !personInfo.isEmpty()){
            PbPersonFlowInfo pbPersonFlowInfo = new PbPersonFlowInfo();
            try{
                pbPersonFlowInfo.setId(pbPersonFlowInfoRepository.findSeqId());
                Object beginTime = personInfo.get("beginTime"); 
                pbPersonFlowInfo.setBeginTime(beginTime == null ? null : DateUtil.parseDate( String.valueOf(beginTime),"yyyy-MM-dd"));
                Object endTime = personInfo.get("endTime");
                pbPersonFlowInfo.setEndTime(endTime == null ? null : DateUtil.parseDate( String.valueOf(endTime),"yyyy-MM-dd"));
                pbPersonFlowInfo.setIsEnd(StringConvertUtil.convertToString(personInfo.get("isEnd")));
                pbPersonFlowInfo.setRelationId(StringConvertUtil.convertToString(personInfo.get("relationId")));
                Object modifyTime = personInfo.get("modifyTime");
                pbPersonFlowInfo.setModifyTime(modifyTime == null ? null : DateUtil.parseDate( String.valueOf(modifyTime),"yyyy-MM-dd HH:mm:ss"));
                pbPersonFlowInfo.setStuffCode(StringConvertUtil.convertToString(personInfo.get("staffCode")));
                pbPersonFlowInfo.setType(StringConvertUtil.convertToString(personInfo.get("type")));
                pbPersonFlowInfoRepository.saveAndFlush(pbPersonFlowInfo);
            }catch (Exception e){
                LOG.error("[人员异动信息]插入失败,info="+JSON.toJSONString(personInfo),e);
            }
        }
    }

    /**
     * 增加入职或离职人员数量
     * @author LiuYong
     */
    public int queryTotalEntryOrLeaveStaffNum(String type,Date startTime,Date endTime,String department){
        int num = 0;
        StaffFlowTypeEnum staffFlowTypeEnum = StaffFlowTypeEnum.convertToEnum(type);
        if(staffFlowTypeEnum == null){
            LOG.error("[人员信息]员工异动类型转换异常,type={}",type);
            return num;
        }
        CompanyEnum companyEnum = CompanyEnum.convertToEnum(department);
        if(companyEnum == null){
            LOG.error("[人员信息]部门类型转换异常,department={}",department);
            return num;
        }
        List<String> departmentList = CompanyEnum.covertToList(department);
        if(staffFlowTypeEnum == StaffFlowTypeEnum.ENTRY){
            num = pbPersonFlowInfoRepository.countEntryStaffNumbers(startTime,endTime,departmentList);
        }else if(staffFlowTypeEnum == StaffFlowTypeEnum.LEAVE){
            num = pbPersonFlowInfoRepository.countLeaveStaffNumbers(startTime,endTime,departmentList);
        }else{
            LOG.error("[人员信息]员工异动类型错误,只能为入职或离职,type={}",type);
        }
        return num;
    }

    /**
     * 分页查询员工异动信息
     * 
     * @author LiuYong
     */
    public JSONObject queryPersonFlowInfo(Date startTime, Date endTime, String department, int page) {
        JSONObject result;
        JSONArray jsonArray = null;
        if (page < 1) {
            LOG.error("[员工异动信息]请求页数无效page={}", page);
            return null;
        }
        CompanyEnum companyEnum = CompanyEnum.convertToEnum(department);
        if(companyEnum == null){
            LOG.error("[员工异动信息]部门类型转换异常,department={}",department);
            return null;
        }
        ArrayList<String> departmentList = new ArrayList<>();
        if(CompanyEnum.ALL == companyEnum){
            departmentList.add(CompanyEnum.FIRST.getCode());
            departmentList.add(CompanyEnum.SECOND.getCode());
            departmentList.add(CompanyEnum.THIRD.getCode());
            departmentList.add(CompanyEnum.FOURTH.getCode());
            departmentList.add(CompanyEnum.FIFTH.getCode());
        }else{
            departmentList.add(companyEnum.getCode());
        }
        int startNum = (page -1) * ServiceIdentifier.STAFF_PAGE_SIZE;
        int endNum = page * ServiceIdentifier.STAFF_PAGE_SIZE;
        List<Object[]> list= pbPersonFlowInfoRepository.findPersonFlowInfo(startTime,endTime,departmentList,startNum,endNum);
        boolean hasNext = false;
        if(list != null && !list.isEmpty()){
            int size = list.size();
            hasNext = size == ServiceIdentifier.STAFF_PAGE_SIZE;
            jsonArray = new JSONArray();
            for(int i=0;i<size;i++){
                JSONObject jsonObject = new JSONObject();
                Object[] objects = list.get(i);
                jsonObject.put("type",objects[0]);
                jsonObject.put("begin_time",objects[1]);
                jsonObject.put("staff_name",objects[2]);
                jsonObject.put("sex",objects[3]);
                jsonObject.put("position_level",objects[4]);
                jsonObject.put("department",objects[5]);
                jsonObject.put("birthday",objects[6]);
                jsonObject.put("academic",objects[7]);
                jsonObject.put("positions",objects[8]);
                jsonObject.put("label",objects[9]);
                jsonObject.put("work_site",objects[10]);
                jsonObject.put("stuff_code",objects[11]);
                jsonArray.add(jsonObject);
            }
        }
        result = new JSONObject();
        result.put("data",jsonArray);
        result.put("pageSize", ServiceIdentifier.STAFF_PAGE_SIZE);
        result.put("hasNext", hasNext);
        return result;
    }
    
    

}
