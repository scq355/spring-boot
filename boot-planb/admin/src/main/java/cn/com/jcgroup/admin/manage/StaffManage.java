package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.common.PageInfo;
import cn.com.jcgroup.admin.dto.StaffInfoUpdateDto;
import cn.com.jcgroup.admin.dto.StuffInfoListDto;
import cn.com.jcgroup.service.service.StaffService;
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
import java.util.List;

/**
 * @author LiuYong on 17/6/25 下午3:50.
 */
@Service
public class StaffManage {

    private static final Logger LOG = LoggerFactory.getLogger(StaffManage.class);

    @Autowired
    private StaffService staffService;

    /**
     * 组装员工信息列表
     *
     * @author LiuYong
     */
    public JSONObject buildStaffInfoList(List<String> departCode, int page) {
        JSONObject result = null;
        JSONObject jsonObject = staffService.findStaffInfo(departCode, page);
        if (jsonObject != null) {
            result = new JSONObject();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setHasNext(jsonObject.getBooleanValue("hasNext"));
            pageInfo.setPageNo(page);
            result.put("pageInfo", pageInfo);
            JSONArray data = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                data = new JSONArray();
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject temp = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                    //修改数据类型
                    Date entryTime = temp.getDate("entryTime");
                    Date firstWorkTime = temp.getDate("firstWorkTime");
                    Date birthday = temp.getDate("birthday");
                    String label = temp.getString("label");
                    temp.put("entryTime", entryTime != null ? DateUtil.formatDate("yyyy-MM-dd", entryTime) : "");
                    //年龄计算
                    Integer age = DateUtil.getAge(birthday);
                    temp.put("age", age == null ? 0 : age);
                    temp.put("birthday",birthday == null ? "":DateUtil.formatDate("yyyy-MM-dd", birthday));
                    try {
                        if (StringUtils.isNotBlank(label)) {
                            JSONArray labelArray = JSONArray.parseArray(label);
                            temp.put("label", labelArray);
                        } else {
                            temp.put("label", "");
                        }
                    } catch (Exception e) {
                        LOG.error("[员工列表]标签转换异常,label=" + label, e);
                        temp.put("label", "");
                    }
                    //工作年限计算
                    Integer workYears = DateUtil.getWorkYears(firstWorkTime);
                    temp.put("workYears", workYears == null ? 0 : workYears);
                    temp.put("firstWorkTime",firstWorkTime == null ? "":DateUtil.formatDate("yyyy-MM-dd", firstWorkTime));
                    //本公司工作年限
                    Integer currentWorkYears = DateUtil.getWorkYears(entryTime);
                    temp.put("currentWorkYears", currentWorkYears == null ? 0 : currentWorkYears);
                    //年薪
                    Long annualSalary = temp.getLong("annualSalary");
                    temp.put("annualSalary",annualSalary == null ? 0 : NumberUtil.unitMoney(annualSalary));
                    //部门转换
//                    CompanyEnum companyEnum = CompanyEnum.convertToEnum(temp.getString("department")); 
                    temp.put("department",temp.getString("department"));
                    //性别转换
//                    SexEnum sexEnum = SexEnum.convertToEnum(jsonObject.getString("sex"));
                    temp.put("sex",jsonObject.getString("sex"));
                    //学历转换
//                    DegreeTypeEnum degreeTypeEnum = DegreeTypeEnum.convertToEnum(temp.getString("academic"));
                    temp.put("academic",temp.getString("academic"));
                    StuffInfoListDto stuffInfoListDto = temp.toJavaObject(StuffInfoListDto.class);
                    data.add(stuffInfoListDto);
                }
            }
            result.put("data", data);
        }
        return result;
    }
    
    /**
     * 员工信息修改
     * 
     * @author LiuYong  
     */
    public boolean buildStaffInfoUpdate(StaffInfoUpdateDto staffInfoUpdateDto){
        boolean success = false;
        try{
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(staffInfoUpdateDto);
            //修改数据格式
            String birthday = staffInfoUpdateDto.getBirthday();
            String firstWorkTime = staffInfoUpdateDto.getFirstWorkTime();
            String[] label = staffInfoUpdateDto.getLabel();
            String annualSalary = staffInfoUpdateDto.getAnnualSalary();
            if(StringUtils.isNotBlank(birthday)){
                jsonObject.put("birthday",DateUtil.parseDate(birthday,"yyyy-MM-dd"));
            }
            if(StringUtils.isNotBlank(firstWorkTime)){
                jsonObject.put("firstWorkTime",DateUtil.parseDate(firstWorkTime,"yyyy-MM-dd"));
            }
            jsonObject.put("label", JSON.toJSONString(label));
            jsonObject.put("annualSalary",NumberUtil.convertToPoint(annualSalary));
            success = staffService.updateStaffInfoByStaffCode(jsonObject);
        }catch (Exception e){
            LOG.error("[员工信息修改],数据转换异常",e);
        }
        return success;
    }
    
}
