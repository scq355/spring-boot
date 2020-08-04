package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbBillPayAble;
import cn.com.jcgroup.service.domain.PbBillPayFor;
import cn.com.jcgroup.service.domain.PbMonthPaper;
import cn.com.jcgroup.service.domain.PbSubProject;
import cn.com.jcgroup.service.domain.PbSubProjectMoneySum;
import cn.com.jcgroup.service.domain.PbSubProjectPaidItem;
import cn.com.jcgroup.service.repositories.PbBillPayAbleRepository;
import cn.com.jcgroup.service.repositories.PbBillPayForRepository;
import cn.com.jcgroup.service.repositories.PbMonthPaperRepository;
import cn.com.jcgroup.service.repositories.PbSubProjectMoneySumReponsitory;
import cn.com.jcgroup.service.repositories.PbSubProjectPaidItemRepository;
import cn.com.jcgroup.service.repositories.PbSubProjectRepository;
import cn.com.jcgroup.service.util.DateUtil;
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
 * @description 工程进度
 * @author sunchangqing
 */
@Service
public class SubProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(SubProjectService.class);

    @Autowired
    private PbSubProjectRepository pbSubProjectRepository;
    @Autowired
    private PbMonthPaperRepository pbMonthPaperRepository;
    @Autowired
    private PbBillPayAbleRepository billPayAbleRepository;
    @Autowired
    private PbBillPayForRepository billPayForRepository;
    @Autowired
    private PbSubProjectPaidItemRepository pbSubProjectPaidItemRepository;
    @Autowired
    private PbSubProjectMoneySumReponsitory pbSubProjectMoneySumReponsitory;

    /**
     * 删除工程
     */
    @Transactional
    public void subProjectDelete(String subProCode) {
        pbSubProjectRepository.deleteBySubProCode(subProCode);
    }

    /**
     * 更新月度台账
     */
    @Transactional
    public void editMonthlyAccount(String isShow, int id,String paperName, String yearMonth) {
        try {
            Date yearMonthDate = DateUtil.covertToDate("yyyy.MM.dd", yearMonth);
            pbMonthPaperRepository.updateById(id, isShow, paperName, yearMonthDate);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 删除月度台账
     */
    public void deleteMonthlyAccount(int id) {
        try {
            pbMonthPaperRepository.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 新增应收台账
     */
    public PbBillPayAble insertBillPayable(JSONObject billPayAbleObject) {
        if (billPayAbleObject != null) {
            PbBillPayAble billPayAble = JSON.parseObject(billPayAbleObject.toJSONString(), PbBillPayAble.class);
            billPayAble.setPayTime(new Date());
            PbBillPayAble resBillBapyAble = billPayAbleRepository.save(billPayAble);
            return resBillBapyAble;
        } else {
            return null;
        }
    }


    /**
     * 新增应付台账
     */
    public String insertBillPayfor(JSONObject accountPayfor) {
        String contractCode = null;
        if (accountPayfor != null) {
            PbBillPayFor billPayFor = JSONObject.parseObject(accountPayfor.toJSONString(), PbBillPayFor.class);
            PbBillPayFor pbBillPayFor = billPayForRepository.save(billPayFor);
            contractCode = pbBillPayFor.getContractCode();
            return contractCode;
        }
        return contractCode;
    }

    /**
     * 根据合同编码获取应付台账
     */
    public JSONObject queryBillPayforByContractCode(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            PbBillPayFor billPayFor = billPayForRepository.findById(id);
            jsonObject = (JSONObject) JSON.toJSON(billPayFor);
            return jsonObject;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e) ;
        }
        return jsonObject;
    }


    /**
     * 根据合同编码获取应收台账
     */
    public JSONObject queryBillPayableByContractCode(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            PbBillPayAble billPayAble= billPayAbleRepository.findOne(id);
            jsonObject = (JSONObject) JSON.toJSON(billPayAble);
            return jsonObject;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return jsonObject;
    }


    /**
     * 删除应付台账
     */
    public void billPayForDelete(int id) {
        try {
            billPayForRepository.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 删除应收台账
     */
    public void billPayAbleDelete(int id) {
        try {
            billPayAbleRepository.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 修改工程
     */
    @Transactional
    public int subProjectUpdate(String subProCode, String subProName, boolean isShow, long totalMoney, String fileCode) {
        PbSubProject subProject = new PbSubProject();
        subProject.setUpdateTime(new Date());
        if (isShow) {
            subProject.setIsShow("1");
        } else {
            subProject.setIsShow("0");
        }
        subProject.setSubProjectName(subProName);
        subProject.setSubProCode(subProCode);
        subProject.setTotalMoney(totalMoney);
        subProject.setFileCode(fileCode);
        return pbSubProjectRepository.updateSubProject(subProject);
    }

    /**
     * 新建工程
     */
    public void subProjectCreate(String proCode, String subProCode, String subProName, long totalMoney, String fileCode) {
        try {
            PbSubProject subProject = new PbSubProject();
            subProject.setProCode(proCode);
            subProject.setSubProCode(subProCode);
            subProject.setSubProjectName(subProName);
            subProject.setTotalMoney(totalMoney);
            subProject.setIsShow("1");
            subProject.setCreateTime(new Date());
            subProject.setUpdateTime(new Date());
            subProject.setFileCode(fileCode);
            subProject.setId(pbSubProjectRepository.findSeqId());
            pbSubProjectRepository.save(subProject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 工程列表
     */
    public JSONArray subProjectList(String proCode, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC, "updateTime"));
        List<PbSubProject> subProjectList = pbSubProjectRepository.findAllByCodeInPage(proCode, pageable);
        if (subProjectList != null && !(subProjectList.isEmpty())) {
            for (PbSubProject subProject : subProjectList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(subProject);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据工程编码获取已付资金流水
     */
    public JSONArray queryPaidFlowBySubProCode(String subProCode, Date startDate, Date entDate) {
        JSONArray paidFlowArray = null;
        List<PbSubProjectPaidItem> paidItemList = pbSubProjectPaidItemRepository.findAllBySubProCodeAndPayTime(subProCode, startDate, entDate,new Sort(Sort.Direction.DESC,"payTime"));
        if (paidItemList != null && !paidItemList.isEmpty()) {
            int size = paidItemList.size();
            paidFlowArray = new JSONArray(size);
            for(int i = 0;i<size;i++){
                paidFlowArray.add(paidItemList.get(i));
            }
        }
        return paidFlowArray;
    }

    /**
     * 根据工程编码获取应付台账
     */
    public JSONArray queryBillPayforBySubProCode(String subProCode) {
        JSONArray jsonArray = new JSONArray();
        List<PbBillPayFor> pbBillPayForList = billPayForRepository.findAllByProCode(subProCode);
        if (pbBillPayForList != null && !(pbBillPayForList.isEmpty())) {
            for (PbBillPayFor billPayFor : pbBillPayForList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(billPayFor);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }


    /**
     * 根据工程编码获取应收台账
     */
    public JSONArray queryPayabelByPySubProCode(String subProCode) {
        JSONArray jsonArray = new JSONArray();
        List<PbBillPayAble> pbBillPayAbleList = billPayAbleRepository.findAllByProCode(subProCode);
        if (pbBillPayAbleList != null && !(pbBillPayAbleList.isEmpty())) {
            for (PbBillPayAble payAble : pbBillPayAbleList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(payAble);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
    
    

    /**
     * 根据工程编码获取应付台账
     */
    public JSONObject queryBillPayforBySubProCode(String subProCode,int page) {
        JSONObject result;
        JSONArray jsonArray = null;
        Pageable pageable = new PageRequest(page -1, ServiceIdentifier.PAGE_SIZE,new Sort(Sort.Direction.DESC,"signTime"));
        Slice<PbBillPayFor> pbBillPayForList = billPayForRepository.findAllByProCode(subProCode,pageable);
        if(pbBillPayForList != null && pbBillPayForList.hasContent()){
            int size = pbBillPayForList.getContent().size();
            jsonArray = new JSONArray(size);
            for(int i=0;i<size;i++){
                jsonArray.add(pbBillPayForList.getContent().get(i));
            }
        }
        result = new JSONObject();
        result.put("hasNext",pbBillPayForList != null && pbBillPayForList.hasNext());
        result.put("data",jsonArray);
        return result;
    }


    /**
     * 根据工程编码获取应收台账
     */
    public JSONObject queryPayabelByPySubProCode(String subProCode,int page) {
        JSONObject jsonObject;
        JSONArray jsonArray = null;
        Pageable pageable = new PageRequest(page -1, ServiceIdentifier.PAGE_SIZE,new Sort(Sort.Direction.DESC,"signTime"));
        Slice<PbBillPayAble> pbBillPayAbleList = billPayAbleRepository.findAllByProCode(subProCode,pageable);
        if(pbBillPayAbleList != null && pbBillPayAbleList.hasContent()){
            int size = pbBillPayAbleList.getContent().size();
            jsonArray = new JSONArray(size);
            for(int i=0;i<size;i++){
                jsonArray.add(pbBillPayAbleList.getContent().get(i));
            }
        }
        jsonObject = new JSONObject();
        jsonObject.put("hasNext",pbBillPayAbleList != null && pbBillPayAbleList.hasNext());
        jsonObject.put("data",jsonArray);
        return jsonObject;
    }

    /**
     * 根据工程编码获取月度台账
     */
    public JSONArray queryMonthPaperBySubProCode(String subProCode) {
        JSONArray monthPaperArray = new JSONArray();
        List<PbMonthPaper> monthPaperList = pbMonthPaperRepository.findAllByProCode(subProCode);
        if (monthPaperList != null && !(monthPaperList.isEmpty())) {
            for (PbMonthPaper monthPaper : monthPaperList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(monthPaper);
                monthPaperArray.add(jsonObject);
            }
        }
        return monthPaperArray;
    }

    /**
     * 根据项目编码获取工程列表
     */
    public JSONArray querySubProByProCode(String proCode) {
        List<PbSubProject> subProjectList = pbSubProjectRepository.findAllByProCode(proCode);
        if(subProjectList != null && !subProjectList.isEmpty()){
            JSONArray jsonArray = new JSONArray();
            for (PbSubProject subProject : subProjectList) {
                jsonArray.add(JSON.toJSON(subProject));
            }
            return jsonArray;
        }
        return null;
    }

    /**
     * 查询工程款汇总流水
     * @author LiuYong
     */
    public JSONObject querySubProjectMoneySum( String subProCode,Date reportTime){
        if(StringUtils.isBlank(subProCode) || reportTime == null){
            LOG.error("工程编号或时间为空");
            return null;
        }
        PbSubProjectMoneySum sum = pbSubProjectMoneySumReponsitory.findBySubProjectAndTime(subProCode, reportTime);
        JSONObject jsonObject = null;
        if(sum != null){
            jsonObject = new JSONObject();
            jsonObject.put("checkedMoney",sum.getCheckedMoney());
            jsonObject.put("projectProgress",sum.getProjectProgress());
            jsonObject.put("realPaidMoney",sum.getRealPaidMoney());
            jsonObject.put("totalMoney",sum.getTotalMoney());
            jsonObject.put("totalCheckedMoney",sum.getTotalCheckedMoney());
            jsonObject.put("totalRealPaidMoney",sum.getTotalRealPaidMoney());
        }
        return jsonObject;
    }

    /**
     *  根据工程编码和月份统计工程产值和累计付款
     * @author LiuYong
     */
    public JSONObject queryTotalMoneyByCodeAndTime(String subProCode,Date reportTime){
        if(StringUtils.isBlank(subProCode)){
            LOG.error("工程编号为空");
            return null;
        }
        JSONObject jsonObject = null;
        List<Object[]> list = pbSubProjectPaidItemRepository.countTotalMoneyByCodeAndTime(subProCode, reportTime);
        if(list != null && !list.isEmpty()){
            Object[] objects = list.get(0);
            jsonObject = new JSONObject();
            jsonObject.put("totalPaidMoney",objects[0]);
            jsonObject.put("totalPay",objects[1]);
        }
        return jsonObject;
    }

    /**
     * 应收台账更新
     */
    @Transactional
    public void editAccountPayAble(JSONObject jsonObject) {
        PbBillPayAble billPayAble = JSONObject.parseObject(jsonObject.toJSONString(), PbBillPayAble.class);
        billPayAbleRepository.updateById(billPayAble);
    }

    /**
     * 应付台账更新
     */
    @Transactional
    public void editAccountPayFor(JSONObject accountPayforObj) {
        try {
            PbBillPayFor billPayFor = JSONObject.parseObject(accountPayforObj.toJSONString(), PbBillPayFor.class);
            billPayForRepository.updateById(billPayFor);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 工程查询
     */
    public JSONArray subProjectSearch(String subProName) {
        List<PbSubProject> subProjectList = pbSubProjectRepository.findAllBySubProjectNameLike(subProName);
        JSONArray subProjectArray = new JSONArray();
        if (subProjectList != null && !(subProjectList.isEmpty())) {
            for (PbSubProject subProject : subProjectList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subProCode", subProject.getSubProCode());
                jsonObject.put("isShow", subProject.getIsShow());
                jsonObject.put("totalMoney", subProject.getTotalMoney());
                jsonObject.put("subProjectName", subProject.getSubProjectName());
                jsonObject.put("createTime", subProject.getCreateTime());
                jsonObject.put("updateTime", subProject.getUpdateTime());
                jsonObject.put("id", subProject.getId());
                jsonObject.put("relatedContractFileCode", subProject.getFileCode());
                subProjectArray.add(jsonObject);
            }
        }
        return subProjectArray;
    }

    /**
     * 更新月度台账
     */
    public JSONObject queryMonthPaper(int id) {
        try {
            PbMonthPaper monthPaper = pbMonthPaperRepository.findOne(id);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(monthPaper);
            return jsonObject;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

}