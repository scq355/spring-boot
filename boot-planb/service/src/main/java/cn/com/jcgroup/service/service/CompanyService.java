package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbCompany;
import cn.com.jcgroup.service.domain.PbCompanyStaff;
import cn.com.jcgroup.service.domain.PbCompanyStaffRelation;
import cn.com.jcgroup.service.domain.PbFinanceSummary;
import cn.com.jcgroup.service.repositories.PbCompanyAgencyRelationRepository;
import cn.com.jcgroup.service.repositories.PbCompanyRepository;
import cn.com.jcgroup.service.repositories.PbCompanyStaffRelationRepository;
import cn.com.jcgroup.service.repositories.PbCompanyStaffRepository;
import cn.com.jcgroup.service.repositories.PbFinanceSummaryRepository;
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

import java.util.Date;
import java.util.List;

/**
 * @description 合营公司
 * @author sunchangqing
 */
@Service
public class CompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private PbCompanyRepository companyRepository;
    @Autowired
    private PbCompanyAgencyRelationRepository companyAgencyRelationRepository;
    @Autowired
    private PbCompanyStaffRepository pbCompanyStaffRepository;
    @Autowired
    private PbCompanyStaffRelationRepository pbCompanyStaffRelationRepository;
    @Autowired
    private PbFinanceSummaryRepository pbFinanceSummaryRepository;

    
    /**
     * 根据机构编码删除记录
     */
    public void deleteRelationByAgencyCode(String agencyCode) {
        try {
            companyAgencyRelationRepository.deleteAllByAgencyCode(agencyCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 合营公司查询
     */
    public JSONArray searchCompany(String companyName) {
        List<PbCompany> companyList = companyRepository.findByCompanyNameLike(companyName);
        JSONArray companyArray = new JSONArray();
        if (companyList != null && !(companyList.isEmpty())) {
            for (PbCompany company : companyList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("companyCode", company.getCompanyCode());
                jsonObject.put("stockHolder", company.getStockHolder());
                jsonObject.put("companyName", company.getCompanyName());
                jsonObject.put("createTime", company.getCreateTime());
                jsonObject.put("director", company.getDirector());
                jsonObject.put("equityTransferAgree", company.getEquityTransferAgree());
                jsonObject.put("hrInfo", company.getHrInfo());
                jsonObject.put("isShow", company.getIsShow());
                jsonObject.put("legalPerson", company.getLegalPerson());
                jsonObject.put("officeAddress", company.getOfficeAddress());
                jsonObject.put("officeImages", company.getOfficeImages());
                jsonObject.put("officeSize", company.getOfficeSize());
                jsonObject.put("ownershipStruct", company.getOwnershipStruct());
                jsonObject.put("proCode", company.getProCode());
                jsonObject.put("profitDistrPlan", company.getProfitDistrPlan());
                jsonObject.put("projectManager", company.getProjectManager());
                jsonObject.put("registerCapital", company.getRegisterCapital());
                jsonObject.put("rulePromise", company.getRulePromise());
                jsonObject.put("seniorManager", company.getSeniorManager());
                jsonObject.put("supervisors", company.getSupervisors());
                jsonObject.put("updateTime", company.getUpdateTime());
                companyArray.add(jsonObject);
            }
        }
        return companyArray;
    }

    /**
     * 合营公司-编辑
     */
    @Transactional
    public JSONObject editCompany(String companyCode, String companyName, boolean isShow) {
        String showFlag;
        if (isShow) {
            showFlag = "1";
        } else {
            showFlag = "0";
        }
        companyRepository.updateCompany(companyCode, companyName, new Date(), showFlag);
        return new JSONObject();
    }

    /**
     * 删除合营公司
     */
    @Transactional
    public void deleteCompany(String companyCode) {
        try {
            companyRepository.deleteByCompanyCode(companyCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 新建合营公司
     */
    public String createCompany(String companyName, String companyCode, String proCode, boolean isShow) {
        String resultCode;
        try {
            PbCompany company = new PbCompany();
            company.setCompanyCode(companyCode);
            company.setCompanyName(companyName);
            company.setProCode(proCode);
            if (isShow) {
                company.setIsShow("1");
            } else {
                company.setIsShow("0");
            }
            company.setCreateTime(new Date());
            company.setUpdateTime(new Date());
            PbCompany resCompany = companyRepository.save(company);
            resultCode = resCompany.getCompanyCode();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            resultCode = "";
        }
        return resultCode;
    }

    /**
     * 通过项目编码获取合营公司列表（分页）
     */
    public JSONArray getCompanyListByProjectCode(String projectCode, int page) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(projectCode)) {
            return jsonArray;
        }
        PageRequest pageable = new PageRequest(page - 1, 100, new Sort(Sort.Direction.DESC,"createTime"));
        List<PbCompany> info = companyRepository.findAllByProCode(projectCode, pageable);
        if (info != null && !(info.isEmpty())) {
            for (PbCompany company: info) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("companyCode", company.getCompanyCode());
                jsonObject.put("companyName", company.getCompanyName());
                jsonObject.put("isShow", company.getIsShow());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 通过项目编码获取合营公司列表
     */
    public JSONArray getCompanyListByProjectCode(String projectCode) {
        JSONArray resArray = new JSONArray();
        List<PbCompany> companyList = companyRepository.findAllByProCodeBack(projectCode);
        if (companyList != null && !(companyList.isEmpty())) {
            for (PbCompany company : companyList) {
                JSONObject companyObj = new JSONObject();
                companyObj.put("updateTime", company.getUpdateTime());
                companyObj.put("proCode", company.getProCode());
                companyObj.put("createTime", company.getCreateTime());
                companyObj.put("companyCode", company.getCompanyCode());
                companyObj.put("companyName", company.getCompanyName());
                companyObj.put("legalPerson", company.getLegalPerson());
                companyObj.put("officeImages", company.getOfficeImages());
                companyObj.put("profitDistrPlan", company.getProfitDistrPlan());
                companyObj.put("hrInfo", company.getHrInfo());
                companyObj.put("isShow", company.getIsShow());
                companyObj.put("officeAddress", company.getOfficeAddress());
                companyObj.put("eegisterCapital", company.getRegisterCapital());
                companyObj.put("eulePromise", company.getRulePromise());
                companyObj.put("equityTransferAgree", company.getEquityTransferAgree());
                companyObj.put("ownershipStruct", company.getOwnershipStruct());
                companyObj.put("officeSize", company.getOfficeSize());
                companyObj.put("projectManager", company.getProjectManager());
                companyObj.put("supervisors", company.getSupervisors());
                companyObj.put("seniorManager", company.getSeniorManager());
                companyObj.put("director", company.getDirector());
                resArray.add(companyObj);
            }
        }
        return resArray;

    }

    /**
     * 根据公司编码获取公司详细信息
     */
    public JSONObject getCompanyInfoByCompanyCode(String companyCode) {
        JSONObject jsonObject = new JSONObject();
        if (companyCode != null) {
            PbCompany company = companyRepository.findByCompanyCode(companyCode);
            jsonObject = (JSONObject) JSON.toJSON(company);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 更新合营公司信息
     */
    @Transactional
    public void updateCompanyInfo(JSONObject jsonObject) {
        try {
            PbCompany pbCompany = jsonObject.toJavaObject(PbCompany.class);
            pbCompany.setUpdateTime(new Date());
            companyRepository.updateCompanyInfo(pbCompany);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }
    
    /**
     * 查询员工信息
     * 
     * @author LiuYong  
     */
    public JSONArray findStaffInfoByIds(List<Integer> ids){
        JSONArray jsonArray = null;
        if(ids != null && !ids.isEmpty()){
            List<PbCompanyStaff> list = pbCompanyStaffRepository.findStaffInfoByIds(ids);
            if(list != null && !list.isEmpty()){
               jsonArray = new JSONArray();
               for(PbCompanyStaff pbCompanyStaff:list){
                   jsonArray.add(pbCompanyStaff);
               }
            }
        }
        return jsonArray;
    }

    /**
     * 根据合营公司编号查询员工编号列表
     *
     * @author LiuYong
     */
    public List<Integer> findStaffIdByCompanyCode(String companyCode){
        return pbCompanyStaffRelationRepository.findIdsByCompanyCode(companyCode);
    }

    /**
     * 删除员工关系
     *
     * @author LiuYong
     */
    @Transactional
    public boolean deleteStaffRelation(String companyCode,int  companyStaffId){
        return pbCompanyStaffRelationRepository.deleteRelation(companyCode, companyStaffId) > 0;
    }

    /**
     * 新增合营公司员工关系
     *
     * @author LiuYong
     */
    @Transactional
    public Integer addCompanyStaff(JSONObject jsonObject,String companyCode){
        if(jsonObject != null){
            PbCompanyStaff pbCompanyStaff =jsonObject.toJavaObject(PbCompanyStaff.class);
            if(pbCompanyStaff != null){
                pbCompanyStaffRepository.saveAndFlush(pbCompanyStaff);
                //新增合营公司员工关系
                PbCompanyStaffRelation relation = new PbCompanyStaffRelation();
                relation.setCompanyCode(companyCode);
                relation.setCompanyStaffId(pbCompanyStaff.getId());
                pbCompanyStaffRelationRepository.saveAndFlush(relation);
                return pbCompanyStaff.getId();
            }
        }
        return null;
    }

    /**
     * 更新合营公司员工信息
     *
     * @author LiuYong
     */
    @Transactional
    public boolean updateStaffInfo(JSONObject jsonObject){
        if(jsonObject != null){
            PbCompanyStaff pbCompanyStaff =jsonObject.toJavaObject(PbCompanyStaff.class);
            if(pbCompanyStaff != null){
                return pbCompanyStaffRepository.updateStaffInfo(pbCompanyStaff) > 0;
            }
        }
        return false;
    }

    /**
     * 删除融资款
     *
     * @author LiuYong
     */
    @Transactional
    public boolean deleteFinanceSummary(int id){
        return pbFinanceSummaryRepository.deleteFinanceSummary(id) > 0;
    }

    /**
     * 更新融资款
     *
     * @author LiuYong
     */
    @Transactional
    public boolean updateFinanceSummary(JSONObject jsonObject){
        if(jsonObject != null){
            PbFinanceSummary pbFinanceSummary =jsonObject.toJavaObject(PbFinanceSummary.class);
            if(pbFinanceSummary != null){
                pbFinanceSummary.setUpdateTime(new Date());
                return pbFinanceSummaryRepository.updateFinanceSummary(pbFinanceSummary) > 0;
            }
        }
        return false;
    }

    /**
     * 新增融资款
     *
     * @author LiuYong
     */
    @Transactional
    public Integer addFinanceSummary(JSONObject jsonObject){
        if(jsonObject != null){
            PbFinanceSummary pbFinanceSummary =jsonObject.toJavaObject(PbFinanceSummary.class);
            if(pbFinanceSummary != null){
                Date date = new Date();
                pbFinanceSummary.setCreateTime(date);
                pbFinanceSummary.setUpdateTime(date);
                pbFinanceSummaryRepository.saveAndFlush(pbFinanceSummary);
                return pbFinanceSummary.getId();
            }
        }
        return null;
    }


    /**
     * 查询员工信息
     *
     * @author LiuYong
     */
    public JSONArray findFinanceSummary(String companyCode){
        JSONArray jsonArray = null;
        if(StringUtils.isNotBlank(companyCode)){
            List<PbFinanceSummary> list = pbFinanceSummaryRepository.findAllByCompanyCode(companyCode);
            if(list != null && !list.isEmpty()){
                int size = list.size();
                jsonArray = new JSONArray(size);
                for(int i=0;i<size;i++){
                    jsonArray.add(list.get(i));
                }
            }
        }
        return jsonArray;
    }

}
