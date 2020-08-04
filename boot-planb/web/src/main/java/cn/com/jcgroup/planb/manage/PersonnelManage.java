package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.planb.common.PageInfo;
import cn.com.jcgroup.service.enums.AgeTypeEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.enums.DegreeTypeEnum;
import cn.com.jcgroup.service.enums.SalaryTypeEnum;
import cn.com.jcgroup.service.enums.SexEnum;
import cn.com.jcgroup.service.enums.StaffFlowTypeEnum;
import cn.com.jcgroup.service.enums.WorkTimeTypeEnum;
import cn.com.jcgroup.service.service.PersonnelFlowService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.JsonUtil;
import cn.com.jcgroup.service.util.MathUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PersonnelManage {


    @Autowired
    private PersonnelFlowService personnelFlowService;

    /**
     * 基本信息
     */
    public JSONObject buildBasicInfo(String deptCode) throws Exception{
        //初始化模板
        //性别
        JSONArray sexArray = new JSONArray();
        JSONObject temp;
        for (SexEnum sexEnum : SexEnum.values()) {
            temp = new JSONObject();
            temp.put("key", sexEnum.getType());
            temp.put("name", sexEnum.getInfo());
            temp.put("percent", "0");
            sexArray.add(temp);
        }
        JSONArray degreeArray = new JSONArray();
        //学历
        for (DegreeTypeEnum typeEnum : DegreeTypeEnum.values()) {
            temp = new JSONObject();
            temp.put("key", typeEnum.getType());
            temp.put("name", typeEnum.getInfo());
            temp.put("percent", "0");
            degreeArray.add(temp);
        }
        JSONArray ageArray = new JSONArray();
        //年龄
        for(AgeTypeEnum ageTypeEnum : AgeTypeEnum.values()){
            temp = new JSONObject();
            temp.put("key", ageTypeEnum.getType());
            temp.put("name", ageTypeEnum.getInfo());
            temp.put("percent", "0");
            ageArray.add(temp);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age",ageArray);
        jsonObject.put("degree",degreeArray);
        jsonObject.put("sex",sexArray);
        if(StringUtils.isBlank(deptCode)){
            return jsonObject;
        }
        List<String> deptList = CompanyEnum.covertToList(deptCode);
        //计算总人数
        Integer total = personnelFlowService.queryTotalStaff(deptList);
        if(total == 0){
            return jsonObject;
        }
        //组装性别
        JSONArray tempSex = personnelFlowService.querySexByDeptCode(deptList);
        if(tempSex != null && !tempSex.isEmpty()){
            for(Object object : tempSex){
                JSONObject sexObject = (JSONObject) object;
                int num = sexObject.getIntValue("num");
                String sex = sexObject.getString("sex");
                //初始化值
                for(Object o1 : sexArray){
                    JSONObject tempObject = (JSONObject) o1;
                    String type = tempObject.getString("key");
                    if(type.equalsIgnoreCase(sex)){
                        tempObject.put("percent", MathUtil.format(MathUtil.div(num,total)));
                        break;
                    }
                }
                
            }
        }
        //组装学位
        JSONArray tempDegree = personnelFlowService.queryAcademicByDeptCode(deptList);
        if(tempDegree != null && !tempDegree.isEmpty()){
            for(Object object : tempDegree){
                JSONObject degreeObject = (JSONObject) object;
                int num = degreeObject.getIntValue("num");
                String degree = degreeObject.getString("academic");
                //初始化值
                for(Object o1 : degreeArray){
                    JSONObject tempObject = (JSONObject) o1;
                    String type = tempObject.getString("key");
                    if(type.equalsIgnoreCase(degree)){
                        tempObject.put("percent", MathUtil.format(MathUtil.div(num,total)));
                        break;
                    }
                }
            }
        }
        //组装年龄
        Map<String,Integer> map = personnelFlowService.queryAgeByDeptCode(deptList);
        if(map !=null && !map.isEmpty()){
            for(Object object : ageArray){
                JSONObject ageObject = (JSONObject) object;
                String type = ageObject.getString("key");
                Integer  number = map.get(type);
                ageObject.put("percent",MathUtil.format(MathUtil.div(number == null ? 0 :number,total)));
            }
        }
        return jsonObject;
    }

    /**
     * 薪资年限
     */
    public JSONObject buildTimeSalary(String deptCode) {
        //初始化模板
        //工作年限
        JSONArray workArray = new JSONArray();
        JSONObject temp;
        for (WorkTimeTypeEnum workTimeTypeEnum : WorkTimeTypeEnum.values()) {
            temp = new JSONObject();
            temp.put("key", workTimeTypeEnum.getType());
            temp.put("name", workTimeTypeEnum.getInfo());
            temp.put("percent", "0");
            workArray.add(temp);
        }
        //本公司工作年限
        JSONArray companyArray = JsonUtil.deepClone(workArray);
        //年薪待遇
        JSONArray salaryArray = new JSONArray();
        //学历
        for (SalaryTypeEnum typeEnum : SalaryTypeEnum.values()) {
            temp = new JSONObject();
            temp.put("key", typeEnum.getType());
            temp.put("name", typeEnum.getInfo());
            temp.put("percent", "0");
            salaryArray.add(temp);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("annual_salary",salaryArray);
        jsonObject.put("company_work_years",companyArray);
        jsonObject.put("work_years",workArray);
        if(StringUtils.isBlank(deptCode)){
            return jsonObject;
        }
        List<String> deptList = CompanyEnum.covertToList(deptCode);
        //计算总人数
        Integer total = personnelFlowService.queryTotalStaff(deptList);
        if(total == 0){
            return jsonObject;
        }
        //组装工作年限制
        Map<String,Integer> map = personnelFlowService.queryWorkTime(deptList,"2");
        if(map !=null && !map.isEmpty()){
            for(Object object : workArray){
                JSONObject tempObject = (JSONObject) object;
                String type = tempObject.getString("key");
                Integer  number = map.get(type);
                tempObject.put("percent",MathUtil.format(MathUtil.div(number == null ? 0 :number,total)));
            }
        }
        //组装本公司工作年限制
        map = personnelFlowService.queryWorkTime(deptList,"1");
        if(map !=null && !map.isEmpty()){
            for(Object object : companyArray){
                JSONObject tempObject = (JSONObject) object;
                String type = tempObject.getString("key");
                Integer  number = map.get(type);
                tempObject.put("percent",MathUtil.format(MathUtil.div(number == null ? 0 :number,total)));
            }
        }
        //组装薪水
        map = personnelFlowService.queryAnnualSalary(deptList);
        if(map !=null && !map.isEmpty()){
            for(Object object : salaryArray){
                JSONObject tempObject = (JSONObject) object;
                String type = tempObject.getString("key");
                Integer  number = map.get(type);
                tempObject.put("percent",MathUtil.format(MathUtil.div(number == null ? 0 :number,total)));
            }
        }
        return jsonObject;
    }
    
    /**
     * 人员流动信息
     * @author LiuYong  
     */
    public JSONObject buildStaffFlow(Date startTime, Date endTime, int page, String department) {
        JSONObject jsonObject = new JSONObject();
        int entry_total = 0;
        int leave_total = 0;
        if (page == 1) {
            //仅在第一页时返回统计数据
            //入职
            entry_total = personnelFlowService.queryTotalEntryOrLeaveStaffNum(StaffFlowTypeEnum.ENTRY.getCode(), startTime, endTime, department);
            leave_total = personnelFlowService.queryTotalEntryOrLeaveStaffNum(StaffFlowTypeEnum.LEAVE.getCode(), startTime, endTime, department);
        }
        //分页查询员工信息
        JSONObject resultObject = personnelFlowService.queryPersonFlowInfo(startTime, endTime, department, page);
        JSONArray staffArray = null;
        if(resultObject != null){
            JSONArray jsonArray = resultObject.getJSONArray("data");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                //组装pageInfo
                PageInfo pageInfo = new PageInfo();
                pageInfo.setHasNext(resultObject.getBooleanValue("hasNext"));
                pageInfo.setPageNo(page);
                pageInfo.setPageSize(resultObject.getIntValue("pageSize"));
                jsonObject.put("pageInfo",pageInfo);
                //组装data
                int size = jsonArray.size();
                staffArray = new JSONArray(size);
                for (Object object : jsonArray) {
                    JSONObject data = (JSONObject) object;
                    JSONObject temp = new JSONObject();
                    temp.put("age", DateUtil.getAge(data.getDate("birthday")));
                    temp.put("degree", data.get("academic"));
                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(data.getString("department"));
                    temp.put("depart_name", companyEnum == null ?"":companyEnum.getName());
                    String label = data.getString("label");
                    temp.put("label",label == null ? "": JSONArray.parseArray(label));
                    temp.put("post",data.get("positions"));
                    temp.put("real_name",data.get("staff_name"));
                    temp.put("sex",data.get("sex"));
                    temp.put("staff_code",data.get("stuff_code"));
                    temp.put("staff_level",data.get("position_level"));
                    temp.put("time", DateUtil.formatDate("yyyy.MM.dd",data.getDate("begin_time")));
                    StaffFlowTypeEnum staffFlowTypeEnum = StaffFlowTypeEnum.convertToEnum(data.getString("type"));
                    temp.put("type",staffFlowTypeEnum == StaffFlowTypeEnum.LEAVE  ? "2" : "1" );
                    staffArray.add(temp);
                }
            }
        }
        JSONObject data = new JSONObject();
        data.put("staff",staffArray);
        data.put("entry_total",entry_total);
        data.put("leave_total",leave_total);
        jsonObject.put("data",data);
        return jsonObject;
    }

}
