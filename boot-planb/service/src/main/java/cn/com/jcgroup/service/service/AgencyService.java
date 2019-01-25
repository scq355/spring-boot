package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbAgency;
import cn.com.jcgroup.service.domain.PbFinanceAgency;
import cn.com.jcgroup.service.repositories.PbAgencyRepository;
import cn.com.jcgroup.service.repositories.PbCompanyAgencyRelationRepository;
import cn.com.jcgroup.service.repositories.PbFinanceAgencyRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description: 机构（金融机构，机构，私募数据）
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午7:11
 */
@Service
public class AgencyService {

    private static final Logger LOG = LoggerFactory.getLogger(AgencyService.class);

    @Autowired
    private PbCompanyAgencyRelationRepository companyAgencyRelationRepository;
    @Autowired
    private PbFinanceAgencyRepository financeAgencyRepository;
    @Autowired
    private PbAgencyRepository agencyRepository;

    /**
     * 其他信息单条记录显示
     */
    public JSONObject agencyInfoShowByAgencyCode(String agencyCode) {
        try {
            PbAgency agency = agencyRepository.findByAgencyCode(agencyCode);
            if (agency != null) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(agency);
                return jsonObject;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 根据公司编码和类型获取编码
     */
    public JSONArray queryCodeByCompanyCodeAndType(String companyCode, String type) {
        List<String> codeList = companyAgencyRelationRepository.findAllByCompanyCodeAndType(companyCode, type);
        JSONArray codeArray = new JSONArray();
        if (codeList != null && !(codeList.isEmpty())) {
            for (String code : codeList) {
                codeArray.add(code);
            }
        }
        return codeArray;
    }

    /**
     * 根据机构编码删除金融机构
     */
    public void deleteFinanceAgencyByAgencyCode(String agencyCode) {
        try {
            financeAgencyRepository.deleteByFinanceAgencyCode(agencyCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 根据机构编码删除机构
     */
    public void deleteAgencyByAgencyCode(String agencyCode) {
        try {
            agencyRepository.deleteByAgencyCode(agencyCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 更新机构
     */
    @Transactional
    public void updateAgency(JSONObject jsonObject) {
        try {
            PbAgency agency = JSONObject.parseObject(jsonObject.toJSONString(), PbAgency.class);
            agency.setUpdateTime(new Date());
            agencyRepository.updateAgencyByAgencyCode(agency);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 更新金融机构
     */
    @Transactional
    public void updateFinanceAgency(JSONObject jsonObject) {
        try {
            PbFinanceAgency financeAgency = JSONObject.parseObject(jsonObject.toJSONString(), PbFinanceAgency.class);
            financeAgencyRepository.updateFinanceAgency(financeAgency);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
