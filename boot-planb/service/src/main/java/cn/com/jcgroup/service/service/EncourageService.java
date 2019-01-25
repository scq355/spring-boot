package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbEncourage;
import cn.com.jcgroup.service.domain.PbEncourageFile;
import cn.com.jcgroup.service.repositories.PbEncourageFileRepository;
import cn.com.jcgroup.service.repositories.PbEncourageRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * Description: 激励相关
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午4:23
 */
@Service
public class EncourageService {

    private static final Logger LOG = LoggerFactory.getLogger(EncourageService.class);

    @Autowired
    private PbEncourageFileRepository pbEncourageFileRepository;
    @Autowired
    private PbEncourageRepository pbEncourageRepository;

    /**
     * 激励文件信息修改
     */
    @Transactional
    public void updateEncourageFile(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                PbEncourageFile encourageFile = new PbEncourageFile();
                encourageFile.setFileCode(jsonObject.getString("fileCode"));
                encourageFile.setFileName(jsonObject.getString("fileName"));
                encourageFile.setIsShow(jsonObject.getString("isShow"));
                encourageFile.setUpdateTime(new Date());
                encourageFile.setFileExt(jsonObject.getString("fileExt"));
                pbEncourageFileRepository.updateEncourageFile(encourageFile);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 激励情况－查询
     */
    public JSONArray queryEncourageByProCode(String proCode, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.PAGE_SIZE, new Sort(Sort.Direction.DESC,"preDate"));
        List<PbEncourage> encourageList = pbEncourageRepository.findByProjectCodeInPage(proCode, pageable);
        if (encourageList != null && !(encourageList.isEmpty())) {
            for (PbEncourage encourage : encourageList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("stage", encourage.getStage());
                jsonObject.put("realStatus", encourage.getRealStatus());
                jsonObject.put("realPercent", encourage.getRealPercent());
                jsonObject.put("moneyFlag", encourage.getMoneyFlag());
                jsonObject.put("preDate", encourage.getPreDate());
                jsonObject.put("prePercent", encourage.getPrePercent());
                jsonObject.put("realMoney", encourage.getRealMoney());
                jsonObject.put("id", encourage.getId());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 激励-修改
     */
    @Transactional
    public void updateEncourage(JSONObject jsonObject) {
        try {
            PbEncourage encourage = JSONObject.parseObject(jsonObject.toJSONString(), PbEncourage.class);
            pbEncourageRepository.updateEncourage(encourage);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 激励文件－查询
     */
    public JSONArray queryEncourageFileByProCode(String proCode, int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pageable = new PageRequest(page - 1, 100, new Sort(Sort.Direction.DESC,"proCode"));
        List<PbEncourageFile> encourageList = pbEncourageFileRepository.findByProjectCodeInPage(proCode, pageable);
        if (encourageList != null && !(encourageList.isEmpty())) {
            for (PbEncourageFile encourageFile : encourageList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(encourageFile);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }


    /**
     * 激励添加
     */
    public void insertEncourage(JSONObject jsonObject, Date preDate) {
        try {
            PbEncourage encourage = JSONObject.parseObject(jsonObject.toJSONString(), PbEncourage.class);
            encourage.setPreDate(preDate);
            encourage.setId(pbEncourageRepository.findSeqId());
            pbEncourageRepository.save(encourage);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 激励删除
     */
    public void deleteEncourage(int id) {
        try {
            pbEncourageRepository.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


}
