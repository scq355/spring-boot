package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.*;
import cn.com.jcgroup.service.enums.AgencyEnum;
import cn.com.jcgroup.service.repositories.*;
import cn.com.jcgroup.service.util.NumberUtil;
import cn.com.jcgroup.service.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FinanceDetailService {
    
    private static final Logger LOG = LoggerFactory.getLogger(FinanceDetailService.class);

    @Autowired
    private PbFinanceAgencyRepository pbFinanceAgencyRepository;
    @Autowired
    private PbFinanceSummaryRepository pbFinanceSummaryRepository;
    @Autowired
    private PbAgencyRepository pbAgencyRepository;
    @Autowired
    private PbPrivateFundRepository pbPrivateFundRepository;
    @Autowired
    private PbCompanyAgencyRelationRepository pbCompanyAgencyRelationRepository;

    /**
     * 机构添加
     */
    @Transactional
    public void agencyAdd(JSONObject jsonObject, String typeCode) {
        PbAgency agency = JSONObject.parseObject(jsonObject.toJSONString(), PbAgency.class);
        agency.setUpdateTime(new Date());
        agency.setCreateTime(new Date());
        agency.setIsShow("1");
        for (int i = 0; i < 3; i++) {
            String agencyCode = RandomUtil.generateAgencyCode();
            PbAgency pbAgency = pbAgencyRepository.findByAgencyCode(agencyCode);
            if (pbAgency != null) {
                continue;
            } else {
                agency.setAgencyCode(agencyCode);
                pbAgencyRepository.save(agency);
                PbCompanyAgencyRelation companyAgencyRelation = new PbCompanyAgencyRelation();
                companyAgencyRelation.setComAgencyRelationCode(agencyCode);
                companyAgencyRelation.setCompanyCode(jsonObject.getString("companyCode"));
                companyAgencyRelation.setType(typeCode);
                companyAgencyRelation.setId(pbCompanyAgencyRelationRepository.findSeqId());
                pbCompanyAgencyRelationRepository.save(companyAgencyRelation);
                break;
            }
        }
    }

    /**
     * 金融机构添加
     */
    public void financeAgencyAdd(JSONObject jsonObject) {
        PbFinanceAgency financeAgency = JSONObject.parseObject(jsonObject.toJSONString(), PbFinanceAgency.class);
        financeAgency.setIsShow("1");
        financeAgency.setCreateTime(new Date());
        financeAgency.setUpdateTime(new Date());
        for (int i = 0; i < 3; i++) {
            String financeAgencyCode = RandomUtil.generateAgencyCode();
            PbFinanceAgency pbFinanceAgency = pbFinanceAgencyRepository.findByFinanceAgencyCode(financeAgencyCode);
            if (pbFinanceAgency != null) {
                continue;
            } else {
                financeAgency.setFinanceAgencyCode(financeAgencyCode);
                pbFinanceAgencyRepository.save(financeAgency);
                PbCompanyAgencyRelation companyAgencyRelation = new PbCompanyAgencyRelation();
                companyAgencyRelation.setComAgencyRelationCode(financeAgencyCode);
                companyAgencyRelation.setCompanyCode(jsonObject.getString("companyCode"));
                companyAgencyRelation.setType(AgencyEnum.FINANCE_AGENCY.getCode());
                pbCompanyAgencyRelationRepository.save(companyAgencyRelation);
                break;
            }
        }

    }

    /**
     * 金融机构
     */
    public JSONObject findFinanceAgencyByAgencyCode(String financeAgencyCode) {
        JSONObject jsonObject;
        try {
            PbFinanceAgency financeAgency = pbFinanceAgencyRepository.findByFinanceAgencyCode(financeAgencyCode);
            if (financeAgency != null) {
                jsonObject = (JSONObject) JSON.toJSON(financeAgency);
                return jsonObject;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 显示汇总数据
     * @param companyCode
     * @return
     */
    public JSONArray findFinanceSummary(String companyCode) {
        JSONArray jsonArray = new JSONArray();
        List<PbFinanceSummary> summaryList = pbFinanceSummaryRepository.findAllByCompanyCode(companyCode);
        if (summaryList != null && !summaryList.isEmpty()) {
            for (PbFinanceSummary summary : summaryList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("agency", NumberUtil.unitTenThousand(summary.getAgency()));
                jsonObject.put("cost", NumberUtil.unitTenThousand(summary.getFinancialCostAvg()));
                jsonObject.put("financial", NumberUtil.unitTenThousand(summary.getFinancialAgency()));
                jsonObject.put("other", NumberUtil.unitTenThousand(summary.getRemain()));
                jsonObject.put("private", NumberUtil.unitTenThousand(summary.getPrivateFund()));
                jsonObject.put("raise_amount", NumberUtil.unitTenThousand(summary.getAmountRaised()));
                jsonObject.put("type_name", summary.getType());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 获取机构信息
     */
    public JSONObject findAngecyByAgencyCodeAndType(String agencyCode, String agencyType) {
        PbAgency agency = pbAgencyRepository.findByAgencyCodeAndType(agencyCode, agencyType);
        JSONObject jsonObject = new JSONObject();
        if (agency != null) {
            jsonObject = (JSONObject) JSON.toJSON(agency);
        }
        return jsonObject;
    }
    
    /**
     * 查询基金名称
     * @author LiuYong  
     */
    public String findAgencyName(String agencyCode, String agencyType){
        String name = "";
        if(StringUtils.isBlank(agencyCode) || StringUtils.isBlank(agencyType)){
            LOG.error("[机构列表]机构编码或机构类别为空");
        }else{
            AgencyEnum agencyEnum = AgencyEnum.convertToEnum(agencyType);
            if(agencyEnum == null){
                LOG.error("[机构列表]机构类别转换错误,type={}",agencyType);
            }else  if(AgencyEnum.PRIVATE_FUND == agencyEnum){
                //私募基金
                name = pbPrivateFundRepository.findFundName(agencyCode);
            }else if(AgencyEnum.FINANCE_AGENCY == agencyEnum){
                name = pbFinanceAgencyRepository.findAgencyName(agencyCode);
            }else{
                name = pbAgencyRepository.findAgencyName(agencyCode);
            }
        }
        return name;
    }

    /**
     * 获取私募数据
     * @param fundCode
     * @return
     */
    public JSONObject findPrivateFundByFundCode(String fundCode) {
        JSONObject jsonObject = new JSONObject();
        PbPrivateFund privateFund = pbPrivateFundRepository.findByFundCode(fundCode);
        if (privateFund != null) {
            jsonObject.put("apr", privateFund.getApr());
            jsonObject.put("capitalUse", privateFund.getCapitalUse());
            jsonObject.put("consignFee", privateFund.getConsignFee());
            jsonObject.put("createTime", privateFund.getCreateTime());
            jsonObject.put("custodyFee", privateFund.getCustodyFee());
            jsonObject.put("financeSide", privateFund.getFinanceSide());
            jsonObject.put("fundCode", privateFund.getFundCode());
            jsonObject.put("fundCompany", privateFund.getFundCompany());
            jsonObject.put("fundDuration", privateFund.getFundDuration());
            jsonObject.put("fundManager", privateFund.getFundManager());
            jsonObject.put("guarantor", privateFund.getGuarantor());
            jsonObject.put("investFee", privateFund.getInvestFee());
            jsonObject.put("isShow", privateFund.getIsShow());
            jsonObject.put("manageFee", privateFund.getManageFee());
            jsonObject.put("periodInfo", privateFund.getPeriodInfo());
            jsonObject.put("raiseAmount", privateFund.getRaiseAmount());
            jsonObject.put("realAmount", privateFund.getRealAmount());
            jsonObject.put("riskControl", privateFund.getRiskControl());
            jsonObject.put("riskLevel", privateFund.getRiskLevel());
            jsonObject.put("updateTime", privateFund.getUpdateTime());
        }
        return jsonObject;
    }



}
