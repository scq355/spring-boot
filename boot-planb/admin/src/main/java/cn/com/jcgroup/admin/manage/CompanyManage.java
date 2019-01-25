package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.CompanyInfoEditDto;
import cn.com.jcgroup.admin.dto.CompanyStaffDto;
import cn.com.jcgroup.admin.dto.FinanceSummaryDto;
import cn.com.jcgroup.admin.dto.PrivateFundDto;
import cn.com.jcgroup.service.enums.AgencyEnum;
import cn.com.jcgroup.service.service.AgencyService;
import cn.com.jcgroup.service.service.CompanyService;
import cn.com.jcgroup.service.service.PrivateFundSyncService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:合营公司
 * User: sunchangqing
 * Date: 2017-06-17
 * Time: 下午1:01
 */
@Service
public class CompanyManage {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private PrivateFundSyncService privateFundSyncService;
    @Autowired
    private AgencyService agencyService;

    /**
     * 私募列表
     */
    public JSONArray buildPrivateFundList(String companyCode) {
        JSONArray resArray = new JSONArray();
        JSONArray codeArray = agencyService.queryCodeByCompanyCodeAndType(companyCode, AgencyEnum.PRIVATE_FUND.getCode());
        if (codeArray != null && !codeArray.isEmpty()) {
            int privateFundNum = codeArray.size();
            for (int i = 0; i < privateFundNum; i++) {
                String agencyCode = codeArray.getString(i);
                if (StringUtils.isNotBlank(agencyCode)) {
                    JSONObject jsonObject = privateFundSyncService.findPrivateFundWithPage(agencyCode);
                    if (jsonObject != null) {
                        JSONObject tempObject = new JSONObject();
                        tempObject.put("fundCode", jsonObject.getString("fundCode"));
                        tempObject.put("fundName", jsonObject.getString("fundName"));
                        tempObject.put("fundCompany", jsonObject.getString("fundCompany"));
                        tempObject.put("raiseAmount", jsonObject.getString("raiseAmount"));
                        tempObject.put("fundDuration", jsonObject.getString("fundDuration"));
                        tempObject.put("riskLevel", jsonObject.getString("riskLevel"));
                        tempObject.put("apr", jsonObject.getString("apr"));
                        tempObject.put("custodyFee", jsonObject.getString("custodyFee"));
                        tempObject.put("investFee", jsonObject.getString("investFee"));
                        tempObject.put("manageFee", jsonObject.getString("manageFee"));
                        tempObject.put("consignFee", jsonObject.getString("consignFee"));
                        tempObject.put("financeSide", jsonObject.getString("financeSide"));
                        tempObject.put("guarantor", jsonObject.getString("guarantor"));
                        tempObject.put("fundManager", jsonObject.getString("fundManager"));
                        tempObject.put("riskControl", jsonObject.getString("capitalUse"));
                        tempObject.put("riskControl", jsonObject.getString("riskControl"));
                        //组装periodInfo的数据内容
//                        String periodInfoArray = jsonObject.getString("periodInfo");
//                        JSONArray objArray = new JSONArray();
//                        if (StringUtils.isNotBlank(periodInfoArray)) {
//                            List<HashMap> periodInfoList = JSON.parseArray(periodInfoArray, HashMap.class);
//                            for (HashMap map : periodInfoList) {
//                                JSONObject infoObject = new JSONObject();
//                                Iterator iterator = map.entrySet().iterator();
//                                while (iterator.hasNext()) {
//                                    Map.Entry entry = (Map.Entry) iterator.next();
//                                    Object key = entry.getKey();
//                                    Object value = entry.getValue();
//                                    infoObject.put(key.toString(), value.toString());
//                                }
//                                objArray.add(infoObject);
//                            }
//                        }
//                        tempObject.put("periodInfo", objArray);
//                        String createDate = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("createTime"));
//                        tempObject.put("createTime", createDate);
                        tempObject.put("isShow", jsonObject.getString("isShow"));
                        tempObject.put("realAmount", jsonObject.getString("realAmount"));
                        String updateDate = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("updateTime"));
                        tempObject.put("updateTime", updateDate);
                        tempObject.put("currentTotalMoney", jsonObject.getString("currentTotalMoney"));
                        resArray.add(tempObject);
                    }
                }
            }
        }
        return resArray;
    }

    /**
     * 合营公司查询
     */
    public JSONArray searchCompany(String company_name) {
        JSONArray companyArray = companyService.searchCompany(company_name);
        JSONArray jsonArray = new JSONArray();
        if (companyArray != null && !(companyArray.isEmpty())) {
            int companyNum = companyArray.size();
            for (int i = 0; i < companyNum; i++) {
                JSONObject resCompany = new JSONObject();
                JSONObject jsonObject = companyArray.getJSONObject(i);
                resCompany.put("company_name", jsonObject.getString("companyName"));
                resCompany.put("company_code", jsonObject.getString("companyCode"));
                String isShow = jsonObject.getString("isShow");
                if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                    resCompany.put("is_show", true);
                } else {
                    resCompany.put("is_show", false);
                }
                jsonArray.add(resCompany);
            }
        }
        return jsonArray;
    }

    /**
     * 合营公司列表（分页）
     */
    public JSONArray listCompanyInPage(String proCode, int page) {
        JSONArray resArray = new JSONArray();
        JSONArray jsonArray = companyService.getCompanyListByProjectCode(proCode, page);
        if (jsonArray != null && !(jsonArray.isEmpty())) {
            int companyNum = jsonArray.size();
            for (int i = 0; i < companyNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject companyObject = jsonArray.getJSONObject(i);
                jsonObject.put("company_code", companyObject.getString("companyCode"));
                jsonObject.put("company_name", companyObject.getString("companyName"));
                String isShow = companyObject.getString("isShow");
                if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                    jsonObject.put("is_show", true);
                } else {
                    jsonObject.put("is_show", false);
                }
                resArray.add(jsonObject);
            }
        }
        return resArray;
    }


    /**
     * 获取合营公司概况信息
     */
    public JSONObject getCompanyInfo(String company_code) {
        JSONObject jsonObject = new JSONObject();
        JSONObject companyObject = companyService.getCompanyInfoByCompanyCode(company_code);
        if (companyObject != null) {
            jsonObject.put("office_address", companyObject.getString("officeAddress"));
            jsonObject.put("office_rent", companyObject.getString("officeRent"));
            jsonObject.put("office_size", companyObject.getString("officeSize"));
            jsonObject.put("project_manager", companyObject.getString("projectManager"));
            jsonObject.put("legal_person", companyObject.getString("legalPerson"));
            jsonObject.put("register_capital", companyObject.getString("registerCapital"));
            jsonObject.put("stock_holder", companyObject.getString("stockHolder"));
            jsonObject.put("director", companyObject.getString("director"));
            jsonObject.put("senior_manager", companyObject.getString("seniorManager"));
            jsonObject.put("supervisors", companyObject.getString("supervisors"));
            jsonObject.put("equity_transfer_agree", companyObject.getString("equityTransferAgree"));
            jsonObject.put("rule_promise", companyObject.getString("rulePromise"));
            jsonObject.put("profit_distr_plan", companyObject.getString("profitDistrPlan"));
            jsonObject.put("company_code", companyObject.getString("companyCode"));
        }
        return jsonObject;
    }

    /**
     * 合营公司信息修改
     */
    public void editCompanyInfo(CompanyInfoEditDto companyInfoEditDto) {
        JSONObject companyObject = new JSONObject();
        companyObject.put("supervisors", companyInfoEditDto.getSupervisors());
        companyObject.put("stockHolder", companyInfoEditDto.getStock_holder());
        companyObject.put("seniorManager", companyInfoEditDto.getSenior_manager());
        companyObject.put("rulePromise", companyInfoEditDto.getRule_promise());
        companyObject.put("registerCapital", companyInfoEditDto.getRegister_capital());
        companyObject.put("projectManager", companyInfoEditDto.getProject_manager());
        companyObject.put("profitDistrPlan", companyInfoEditDto.getProfit_distr_plan());
        companyObject.put("legalPerson", companyInfoEditDto.getLegal_person());
        companyObject.put("director", companyInfoEditDto.getDirector());
        companyObject.put("companyCode", companyInfoEditDto.getCompany_code());
        companyObject.put("equityTransferAgree", companyInfoEditDto.getEquity_transfer_agree());
        companyObject.put("officeAddress", companyInfoEditDto.getOffice_address());
        companyObject.put("officeRent", companyInfoEditDto.getOffice_rent());
        companyObject.put("officeSize", companyInfoEditDto.getOffice_size());
        companyService.updateCompanyInfo(companyObject);
    }

    /**
     * 组装合营公司员工信息
     *
     * @author LiuYong
     */
    public JSONArray buildCompanyStaff(String companyCode) {
        JSONArray result = null;
        List<Integer> ids = companyService.findStaffIdByCompanyCode(companyCode);
        if (ids != null && !ids.isEmpty()) {
            JSONArray jsonArray = companyService.findStaffInfoByIds(ids);
            result = new JSONArray();
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
                //数据格式转换
                jsonObject.put("company", jsonObject.getString("company"));
                //年龄
                Date birthday = jsonObject.getDate("birthday");
                Integer age = DateUtil.getAge(birthday);
                jsonObject.put("age", age == null ? 0 : age);
                jsonObject.put("birthday", birthday == null ? "" : DateUtil.formatDate("yyyy-MM-dd",birthday));
                //学历
                jsonObject.put("degree", jsonObject.getString("degree"));
                //性别转换
                jsonObject.put("sex", jsonObject.getString("sex"));
                //年薪
                Long annualSalary = jsonObject.getLong("annualSalary");
                jsonObject.put("annualSalary", annualSalary == null ? 0 : NumberUtil.unitMoney(annualSalary));
                CompanyStaffDto companyStaffListDto = jsonObject.toJavaObject(CompanyStaffDto.class);
                result.add(companyStaffListDto);
            }
        }
        return result;
    }

    /**
     * 新增或修改合营公司员工信息
     *
     * @author LiuYong
     */
    public Map<String,Object> initOrUpdate(CompanyStaffDto companyStaffDto,String type){
        if(companyStaffDto == null || StringUtils.isBlank(type)){
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(companyStaffDto);
        //时间转换
        String birthday = companyStaffDto.getBirthday();
        Date birthDay = DateUtil.parseDate(birthday,"yyyy-MM-dd");
        jsonObject.put("birthday",birthDay);
        //薪水转换
        String annualSalary = companyStaffDto.getAnnualSalary();
        jsonObject.put("annualSalary",NumberUtil.convertToPoint(annualSalary));
        if("add".equalsIgnoreCase(type)){
           Integer id = companyService.addCompanyStaff(jsonObject,companyStaffDto.getCompanyCode());
           map.put("id",id);
        }else if("update".equalsIgnoreCase(type)){
            map.put("flag",companyService.updateStaffInfo(jsonObject));
        }
        return map;
    }

    /**
     * 新增或修改融资款
     *
     * @author LiuYong
     */
    public Map<String,Object> initOrUpdateFinanceSummary(FinanceSummaryDto financeSummaryDto, String type){
        if(financeSummaryDto == null || StringUtils.isBlank(type)){
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(financeSummaryDto);
        //金额转换为long
        jsonObject.put("privateFund",NumberUtil.convertToPointWithUnit(financeSummaryDto.getPrivateFund(),10000));
        jsonObject.put("agency",NumberUtil.convertToPointWithUnit(financeSummaryDto.getAgency(),10000));
        jsonObject.put("financialAgency",NumberUtil.convertToPointWithUnit(financeSummaryDto.getFinancialAgency(),10000));
        jsonObject.put("remain",NumberUtil.convertToPointWithUnit(financeSummaryDto.getRemain(),10000));
        jsonObject.put("financialCostAvg",NumberUtil.convertToPointWithUnit(financeSummaryDto.getFinancialCostAvg(),10000));
        jsonObject.put("amountRaised",NumberUtil.convertToPointWithUnit(financeSummaryDto.getAmountRaised(),10000));
        if("add".equalsIgnoreCase(type)){
            Integer id = companyService.addFinanceSummary(jsonObject);
            map.put("id",id);
        }else if("update".equalsIgnoreCase(type)){
            map.put("flag",companyService.updateFinanceSummary(jsonObject));
        }
        return  map;
    }

    /**
     * 组装融资款信息
     *
     * @author LiuYong
     */
    public JSONArray buildFinanceSummary(String companyCode) {
        JSONArray result = null;
        JSONArray jsonArray = companyService.findFinanceSummary(companyCode);
        if(jsonArray != null && !jsonArray.isEmpty()){
            int size = jsonArray.size();
            result = new JSONArray(size);
            for(int i=0;i<size;i++){
                JSONObject jsonObject = (JSONObject) JSON.toJSON(jsonArray.get(i));
                //金额转换为String
                jsonObject.put("privateFund",NumberUtil.unitTenThousand(jsonObject.getLong("privateFund")));
                jsonObject.put("agency",NumberUtil.unitTenThousand(jsonObject.getLong("agency")));
                jsonObject.put("financialAgency",NumberUtil.unitTenThousand(jsonObject.getLong("financialAgency")));
                jsonObject.put("remain",NumberUtil.unitTenThousand(jsonObject.getLong("remain")));
                jsonObject.put("financialCostAvg",NumberUtil.unitTenThousand(jsonObject.getLong("financialCostAvg")));
                jsonObject.put("amountRaised",NumberUtil.unitTenThousand(jsonObject.getLong("amountRaised")));
                FinanceSummaryDto financeSummaryDto = jsonObject.toJavaObject(FinanceSummaryDto.class);
                result.add(financeSummaryDto);
            }
        }
        return result;
    }

    /**
     * 私募基金显示
     */
    public JSONObject privateFundShow(String fundCode) {
        JSONObject tempObject = new JSONObject();
        JSONObject jsonObject = privateFundSyncService.getPrivateFundInfo(fundCode);
        if (jsonObject != null) {
            tempObject.put("fundCode", jsonObject.getString("fundCode"));
            tempObject.put("fundName", jsonObject.getString("fundName"));
            tempObject.put("fundCompany", jsonObject.getString("fundCompany"));
            tempObject.put("raiseAmount", jsonObject.getString("raiseAmount"));
            tempObject.put("fundDuration", jsonObject.getString("fundDuration"));
            tempObject.put("riskLevel", jsonObject.getString("riskLevel"));
            tempObject.put("apr", jsonObject.getString("apr"));
            tempObject.put("custodyFee", jsonObject.getString("custodyFee"));
            tempObject.put("investFee", jsonObject.getString("investFee"));
            tempObject.put("manageFee", jsonObject.getString("manageFee"));
            tempObject.put("consignFee", jsonObject.getString("consignFee"));
            tempObject.put("financeSide", jsonObject.getString("financeSide"));
            tempObject.put("guarantor", jsonObject.getString("guarantor"));
            tempObject.put("fundManager", jsonObject.getString("fundManager"));
            tempObject.put("capitalUse", jsonObject.getString("capitalUse"));
            tempObject.put("riskControl", jsonObject.getString("riskControl"));
            //组装periodInfo的数据内容
//            String periodInfoArray =  jsonObject.getString("periodInfo");
//            JSONArray objArray = new JSONArray();
//            if (StringUtils.isNotBlank(periodInfoArray)) {
//                List<HashMap> periodInfoList = JSON.parseArray(periodInfoArray, HashMap.class);
//                for (HashMap map : periodInfoList) {
//                    JSONObject infoObject = new JSONObject();
//                    Iterator iterator = map.entrySet().iterator();
//                    while (iterator.hasNext()) {
//                        Map.Entry entry = (Map.Entry) iterator.next();
//                        Object key = entry.getKey();
//                        Object value = entry.getValue();
//                        infoObject.put(key.toString(), value.toString());
//                    }
//                    objArray.add(infoObject);
//                }
//            }
//            tempObject.put("periodInfo", objArray);
//            String createDate = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("createTime"));
//            tempObject.put("createTime", createDate);
            String isShow = jsonObject.getString("isShow");
            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                tempObject.put("isShow", true);
            } else {
                tempObject.put("isShow", false);
            }
            tempObject.put("realAmount", jsonObject.getString("realAmount"));
            String updateDate = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("updateTime"));
            tempObject.put("updateTime", updateDate);
            tempObject.put("currentTotalMoney", jsonObject.getString("currentTotalMoney"));
        }
        return tempObject;
    }

    /**
     * 私募基金编辑
     */
    public void privateFundUpdate(PrivateFundDto privateFundDto) {
        JSONObject jsonObject = new JSONObject();
        if (privateFundDto != null) {
            if (privateFundDto.isShow()) {
                jsonObject.put("isShow", "1");
            } else {
                jsonObject.put("isShow", "0");
            }
            jsonObject.put("custodyFee", privateFundDto.getCustodyFee());
            jsonObject.put("investFee", privateFundDto.getInvestFee());
            jsonObject.put("manageFee", privateFundDto.getManageFee());
            jsonObject.put("consignFee", privateFundDto.getConsignFee());
            jsonObject.put("financeSide", privateFundDto.getFinanceSide());
            jsonObject.put("guarantor", privateFundDto.getGuarantor());
            jsonObject.put("fundManager", privateFundDto.getFundManager());
            jsonObject.put("fundCode", privateFundDto.getFundCode());
            privateFundSyncService.updatePrivateFund(jsonObject);
        }
    }
}
