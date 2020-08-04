package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbSubProjectMoneySum;
import cn.com.jcgroup.service.domain.PbSubProjectPaidItem;
import cn.com.jcgroup.service.repositories.PbSubProjectMoneySumReponsitory;
import cn.com.jcgroup.service.repositories.PbSubProjectPaidItemRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.omg.CORBA.TRANSACTION_MODE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.tree.ExpandVetoException;
import java.util.List;

/**
 * Description:工程量，已付资金
 * User: sunchangqing
 * Date: 2017-06-24
 * Time: 下午7:11
 */
@Service
public class SubProjectAmountService {

    private static final Logger LOG = LoggerFactory.getLogger(SubProjectAmountService.class);

    @Autowired
    private PbSubProjectMoneySumReponsitory subProjectMoneySumReponsitory;  //工程量
    @Autowired
    private PbSubProjectPaidItemRepository subProjectPaidItemRepository;    //已付资金


    /**
     * 工程量列表
     */
    public JSONArray subProAmountCode(String subProCode, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, 100, new Sort(Sort.Direction.DESC, "id"));
        List<PbSubProjectMoneySum> subProjectMoneySumList = subProjectMoneySumReponsitory.findAllBySubProCode(subProCode, pageable);
        if (subProjectMoneySumList != null && !subProjectMoneySumList.isEmpty()) {
            for (PbSubProjectMoneySum subProjectMoneySum : subProjectMoneySumList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(subProjectMoneySum);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }


    /**
     * 资金流水-列表
     */
    public JSONArray subProPaidAmountCode(String subProCode, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, 100, new Sort(Sort.Direction.DESC, "payTime"));
        List<PbSubProjectPaidItem> subProjectPaidItemList = subProjectPaidItemRepository.findAllBySubProCode(subProCode, pageable);
        if (subProjectPaidItemList != null && !subProjectPaidItemList.isEmpty()) {
            for (PbSubProjectPaidItem subProjectPaidItem : subProjectPaidItemList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(subProjectPaidItem);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 工程量删除
     */
    public void subProAmountDelete(int id) {
        try {
            subProjectMoneySumReponsitory.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 资金删除
     */
    public void subProPaidAmountDelete(int id) {
        try {
            subProjectPaidItemRepository.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 工程量添加
     */
    public void subProAmountAdd(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                PbSubProjectMoneySum subProjectMoneySum = JSONObject.parseObject(jsonObject.toJSONString(),
                        PbSubProjectMoneySum.class);
                subProjectMoneySum.setId(subProjectMoneySumReponsitory.findSeqId());
                subProjectMoneySumReponsitory.save(subProjectMoneySum);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 资金添加
     */
    public void subProPaidAmountAdd(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                PbSubProjectPaidItem subProjectPaidItem = JSONObject.parseObject(jsonObject.toJSONString(),
                        PbSubProjectPaidItem.class);
                subProjectPaidItem.setId(subProjectPaidItemRepository.findSeqId());
                subProjectPaidItemRepository.save(subProjectPaidItem);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 工程量-更新
     */
    @Transactional
    public void subProAmountUpdate(JSONObject jsonObject) {
        if(jsonObject != null) {
            PbSubProjectMoneySum subProjectMoneySum = JSONObject.parseObject(jsonObject.toJSONString(),
                    PbSubProjectMoneySum.class);
            subProjectMoneySumReponsitory.update(subProjectMoneySum);
        }
    }

    /**
     * 资金流-更新
     */
    @Transactional
    public void subProPaidAmountUpdate(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                PbSubProjectPaidItem subProjectPaidItem = JSONObject.parseObject(jsonObject.toJSONString(),
                        PbSubProjectPaidItem.class);
                subProjectPaidItemRepository.update(subProjectPaidItem);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
