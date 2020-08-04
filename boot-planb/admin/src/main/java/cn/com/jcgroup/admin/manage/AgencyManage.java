package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.AgencyCodeDto;
import cn.com.jcgroup.admin.dto.FinanceAgencyCodeDto;
import cn.com.jcgroup.service.enums.AgencyEnum;
import cn.com.jcgroup.service.service.AgencyService;
import cn.com.jcgroup.service.service.CompanyService;
import cn.com.jcgroup.service.service.FinanceDetailService;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description: 机构（金融机构，机构，私募基金）
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午8:30
 */
@Service
public class AgencyManage {

    private static final Logger LOG = LoggerFactory.getLogger(AgencyManage.class);

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private FinanceDetailService financeDetailService;
    @Autowired
    private CompanyService companyService;

    /**
     * 金融机构-单条记录-显示
     */
    public JSONObject buildFinanceAgencyInfoShow(String financeAgencyCode) {
        JSONObject jsonObject = financeDetailService.findFinanceAgencyByAgencyCode(financeAgencyCode);
        JSONObject resObject = new JSONObject();
        if (jsonObject != null) {
            resObject.put("finance_agency_code", jsonObject.getString("financeAgencyCode"));
            resObject.put("finance_agency_name", jsonObject.getString("financeAgencyName"));
            resObject.put("capital_cost", jsonObject.getString("capitalCost"));
            String creditDateStr = DateUtil.formatDate("yyyy.MM", (Date) jsonObject.get("creditDate"));
            resObject.put("credit_date", creditDateStr);
            resObject.put("credit_amount", jsonObject.getString("creditAmount"));
            resObject.put("used_amount", jsonObject.getString("usedAmount"));
            String isShow = jsonObject.getString("isShow");
            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                resObject.put("is_show", true);
            } else {
                resObject.put("is_show", false);
            }
        }
        return resObject;
    }

    /**
     * 其他-详细信息-显示
     */
    public JSONObject buildAgencyInfoShow(String agencyCode) {
        JSONObject jsonObject = new JSONObject();
        if (agencyCode != null) {
            JSONObject resObject = agencyService.agencyInfoShowByAgencyCode(agencyCode);
            jsonObject.put("agency_name", resObject.getString("agencyName"));
            jsonObject.put("amount", resObject.getString("amount"));
            jsonObject.put("capital_cost", resObject.getString("capitalCost"));
            jsonObject.put("time_limit", resObject.getString("timeLimit"));
            jsonObject.put("capital_use", resObject.getString("capitalUse"));
            jsonObject.put("business_item", resObject.getString("businessItem"));
            String isShow = resObject.getString("isShow");
            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                jsonObject.put("is_show", true);
            } else {
                jsonObject.put("is_show", false);
            }
            jsonObject.put("type", resObject.getString("type"));
        }
        return jsonObject;
    }

    /**
     * 其他-修改
     */
    public void otherEdit(AgencyCodeDto agencyCodeDto) {
        JSONObject jsonObject = new JSONObject();
        if (agencyCodeDto != null) {
            jsonObject.put("amount", agencyCodeDto.getAmount());
            jsonObject.put("agencyCode", agencyCodeDto.getAgency_code());
            jsonObject.put("capitalCost", agencyCodeDto.getCapital_cost());
            jsonObject.put("agencyName", agencyCodeDto.getAgency_name());
            jsonObject.put("businessItem", agencyCodeDto.getBusiness_item());
            jsonObject.put("capitalUse", agencyCodeDto.getCapital_use());
            jsonObject.put("timeLimit", agencyCodeDto.getTime_limit());
            agencyService.updateAgency(jsonObject);
        }
    }

    /**
     * 其他-添加
     */
    public void otherAdd(AgencyCodeDto agencyCodeDto) {
        if (agencyCodeDto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("companyCode", agencyCodeDto.getCompany_code());
            jsonObject.put("amount", agencyCodeDto.getAmount());
            Date createTime = DateUtil.parseDate(agencyCodeDto.getCreate_time());
            jsonObject.put("createTime", createTime);
            jsonObject.put("updateTime", new Date());
            jsonObject.put("agencyName", agencyCodeDto.getAgency_name());
            jsonObject.put("capitalCost", agencyCodeDto.getCapital_cost());
            jsonObject.put("businessItem", agencyCodeDto.getBusiness_item());
            jsonObject.put("capitalUse", agencyCodeDto.getCapital_use());
            jsonObject.put("timeLimit", agencyCodeDto.getTime_limit());
            jsonObject.put("type", AgencyEnum.OTHER.getCode());
            financeDetailService.agencyAdd(jsonObject, AgencyEnum.OTHER.getCode());
        }
    }


    /**
     * 其他-删除
     */
    @Transactional
    public void deleteOther(String otherCode) {
        try {
            if (otherCode != null) {
                companyService.deleteRelationByAgencyCode(otherCode);
                agencyService.deleteAgencyByAgencyCode(otherCode);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage() ,e);
        }
    }

    /**
     * 其它-列表
     */
    public JSONArray buildOtherListByCompanyCode(String companyCode) {
        JSONArray jsonArray = new JSONArray();
        JSONArray otherArray = agencyService.queryCodeByCompanyCodeAndType(companyCode, AgencyEnum.OTHER.getCode());
        if (otherArray != null && !(otherArray.isEmpty())) {
            int otherNum = otherArray.size();
            for (int i = 0; i < otherNum; i++) {
                JSONObject otherObj = financeDetailService.findAngecyByAgencyCodeAndType(otherArray.getString(i), AgencyEnum.OTHER.getCode());
                if (otherObj != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("agency_name", otherObj.getString("agencyName"));
                    String dateStr = DateUtil.formatDate("yyyy.MM", (Date) otherObj.get("createTime"));
                    jsonObject.put("create_time", dateStr);
                    jsonObject.put("capital_cost", otherObj.getString("capitalUse"));
                    jsonObject.put("capital_use", otherObj.getString("capitalCost"));
                    jsonObject.put("amount", otherObj.getString("amount"));
                    jsonObject.put("business_item", otherObj.getString("businessItem"));
                    jsonObject.put("time_limit", otherObj.getString("timeLimit"));
                    jsonObject.put("agency_code", otherObj.getString("agencyCode"));
                    jsonArray.add(jsonObject);
                }
            }
        }
        return jsonArray;
    }

    /**
     * 金融机构-更新
     */
    public void financeAgencyEdit(FinanceAgencyCodeDto financeAgencyCodeDto) {
        JSONObject jsonObject = new JSONObject();
        if (financeAgencyCodeDto != null) {
            jsonObject.put("usedAmount", financeAgencyCodeDto.getUsed_amount());
            jsonObject.put("residueAmount", financeAgencyCodeDto.getResidue_amount());
            jsonObject.put("financeAgencyName", financeAgencyCodeDto.getFinance_agency_name());
            Date creditDate = DateUtil.parseDate(financeAgencyCodeDto.getCredit_date());
            jsonObject.put("creditDate", creditDate);
            jsonObject.put("creditAmount", financeAgencyCodeDto.getCredit_amount());
            jsonObject.put("companyCode", financeAgencyCodeDto.getCompany_code());
            jsonObject.put("capitalCost", financeAgencyCodeDto.getCapital_cost());
            jsonObject.put("financeAgencyCode", financeAgencyCodeDto.getFinance_agency_code());
            agencyService.updateFinanceAgency(jsonObject);
        }
    }

    /**
     * 机构-更新
     */
    public void agencyUpdate(AgencyCodeDto agencyCodeDto) {
        JSONObject jsonObject = new JSONObject();
        if (agencyCodeDto != null) {
            jsonObject.put("agencyCode", agencyCodeDto.getAgency_code());
            jsonObject.put("amount", agencyCodeDto.getAmount());
            jsonObject.put("capitalCost", agencyCodeDto.getCapital_cost());
            jsonObject.put("updateTime", new Date());
            jsonObject.put("agencyName", agencyCodeDto.getAgency_name());
            jsonObject.put("businessItem", agencyCodeDto.getBusiness_item());
            jsonObject.put("capitalUse", agencyCodeDto.getCapital_use());
            jsonObject.put("timeLimit", agencyCodeDto.getTime_limit());
            agencyService.updateAgency(jsonObject);
        }
    }

    /**
     * 机构-列表
     */
    public JSONArray buildAgencyListByCompanyCode(String companyCode) {
        JSONArray jsonArray = new JSONArray();
        JSONArray agencyCodeArray = agencyService.queryCodeByCompanyCodeAndType(companyCode, AgencyEnum.AGENCY.getCode());
        if (agencyCodeArray != null && !(agencyCodeArray.isEmpty())) {
            int agencyNum = agencyCodeArray.size();
            for (int i = 0; i < agencyNum; i++) {
                JSONObject agencyObject = financeDetailService.findAngecyByAgencyCodeAndType(agencyCodeArray.getString(i), AgencyEnum.AGENCY.getCode());
                if (agencyObject != null) {
                    JSONObject tempAgency = new JSONObject();
                    tempAgency.put("agency_name", agencyObject.getString("agencyName"));
                    tempAgency.put("agency_code", agencyObject.getString("agencyCode"));
                    tempAgency.put("create_time", agencyObject.getString("createTime"));
                    tempAgency.put("capital_cost", agencyObject.getString("capitalUse"));
                    tempAgency.put("capital_use", agencyObject.getString("capitalCost"));
                    tempAgency.put("amount", agencyObject.getString("amount"));
                    tempAgency.put("business_item", agencyObject.getString("businessItem"));
                    tempAgency.put("time_limit", agencyObject.getString("timeLimit"));
                    jsonArray.add(tempAgency);
                }
            }
        }
        return jsonArray;
    }


    /**
     * 金融机构-列表
     */
    public JSONArray buildFinanceAgencyListByCompanyCode(String companyCode) {
        JSONArray agencyCodeArray = agencyService.queryCodeByCompanyCodeAndType(companyCode,
                AgencyEnum.FINANCE_AGENCY.getCode());
        JSONArray agencyArray = new JSONArray();
        if (agencyCodeArray != null && !(agencyCodeArray.isEmpty())) {
            int codeNum = agencyCodeArray.size();
            for (int i = 0; i < codeNum; i++) {
                String agencyCode = (String) agencyCodeArray.get(i);
                if (StringUtils.isNotBlank(agencyCode)) {
                    JSONObject agencyObj = financeDetailService.findFinanceAgencyByAgencyCode(agencyCode);
                    if (agencyObj != null) {
                        JSONObject tempObj = new JSONObject();
                        tempObj.put("capital_cost", agencyObj.getString("capitalCost"));
                        tempObj.put("credit_amount", agencyObj.getString("creditAmount"));
                        String creditDateStr = DateUtil.formatDate("yyyy.MM", (Date) agencyObj.get("creditDate"));
                        tempObj.put("credit_date", creditDateStr);
                        tempObj.put("finance_agency_name", agencyObj.getString("financeAgencyName"));
                        String creditAmount = agencyObj.getString("creditAmount");
                        String usedAmount = agencyObj.getString("usedAmount");
                        if (StringUtils.isNotBlank(creditAmount) && StringUtils.isNotBlank(usedAmount)) {
                            Double creditAmountDouble = Double.parseDouble(creditAmount);
                            Double usedAmountDouble = Double.parseDouble(usedAmount);
                            Double remainingAmount =creditAmountDouble - usedAmountDouble;
                            tempObj.put("remaining_amount", remainingAmount);
                        }
                        tempObj.put("used_amount", agencyObj.getString("usedAmount"));
                        agencyArray.add(tempObj);
                    }
                }
            }
        }
        return agencyArray;
    }

    /**
     * 机构-删除
     */
    @Transactional
    public void deleteAgency(String agencyCode) {
        if (agencyCode != null) {
            companyService.deleteRelationByAgencyCode(agencyCode);
            agencyService.deleteAgencyByAgencyCode(agencyCode);
        }
    }

    /**
     * 金融机构-删除
     */
    @Transactional
    public void deleteFinanceAgency(String agencyCode) {
        if (agencyCode != null) {
            companyService.deleteRelationByAgencyCode(agencyCode);
            agencyService.deleteFinanceAgencyByAgencyCode(agencyCode);
        }
    }

    /**
     * 机构-添加
     */
    public void agencyAdd(AgencyCodeDto agencyCodeDto) {
        if (agencyCodeDto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("companyCode", agencyCodeDto.getCompany_code());
            jsonObject.put("amount", agencyCodeDto.getAmount());
            Date createTime = DateUtil.parseDate(agencyCodeDto.getCreate_time());
            jsonObject.put("createTime", createTime);
            jsonObject.put("agencyName", agencyCodeDto.getAgency_name());
            jsonObject.put("capitalCost", agencyCodeDto.getCapital_cost());
            jsonObject.put("businessItem", agencyCodeDto.getBusiness_item());
            jsonObject.put("capitalUse", agencyCodeDto.getCapital_use());
            jsonObject.put("timeLimit", agencyCodeDto.getTime_limit());
            jsonObject.put("type", AgencyEnum.AGENCY.getCode());
            financeDetailService.agencyAdd(jsonObject, AgencyEnum.AGENCY.getCode());
        }
    }

    /**
     * 金融机构-添加
     */
    public void financeAgencyAdd(FinanceAgencyCodeDto financeAgencyCodeDto) {
        if (financeAgencyCodeDto != null) {
            JSONObject jsonObject = new JSONObject();
            long res_amount = Long.parseLong(financeAgencyCodeDto.getCredit_amount()) -
                    Long.parseLong(financeAgencyCodeDto.getUsed_amount());
            jsonObject.put("financeAgencyCode", financeAgencyCodeDto.getFinance_agency_code());
            jsonObject.put("capitalCost", financeAgencyCodeDto.getCapital_cost());
            Date creditDate = DateUtil.parseDate(financeAgencyCodeDto.getCredit_date());
            jsonObject.put("creditAmount", financeAgencyCodeDto.getCredit_amount());
            jsonObject.put("creditDate", creditDate);
            jsonObject.put("financeAgencyName", financeAgencyCodeDto.getFinance_agency_name());
            jsonObject.put("companyCode", financeAgencyCodeDto.getCompany_code());
            jsonObject.put("usedAmount", financeAgencyCodeDto.getUsed_amount());
            jsonObject.put("remainingAmount", res_amount);
            financeDetailService.financeAgencyAdd(jsonObject);
        }
    }

}
