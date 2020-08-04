package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.PbPrivateFund;
import cn.com.jcgroup.service.enums.IsShowEnum;
import cn.com.jcgroup.service.repositories.PbPrivateFundRepository;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 私募基金同步CRM服务
 *
 * @author LiuYong on 17/6/1 下午3:07.
 */
@Service
public class PrivateFundSyncService {

    private static final Logger LOG = LoggerFactory.getLogger(PrivateFundSyncService.class);

    private static final String PRIVATEFUNDURL = "http://api.gold-finance.com.cn:8008/api/Project";

    @Autowired
    private PbPrivateFundRepository pbPrivateFundRepository;

    /**
     * 通过crm同步私募基金
     *
     * @author LiuYong
     */
    @Transactional
    public void synPrivateFund(String fundCode) {
        String url = PRIVATEFUNDURL + "?code=" + fundCode;
        JSONObject jsonObject = HttpClientUtil.httpGet(url);
        if (jsonObject == null) {
            LOG.error("[同步私募基金]基金同步失败,基金编号:{}", fundCode);
        } else {
            JSONObject dataObject = jsonObject.getJSONObject("data");
            if (dataObject != null && !dataObject.isEmpty()) {
                LOG.debug("[同步私募基金]当前同步私募基金编号:{},返回信息:{}", fundCode, JSON.toJSONString(jsonObject));
                //解析基金数据，并更新存储
                PbPrivateFund privateFund = new PbPrivateFund();
                privateFund.setFundCode(fundCode);
                privateFund.setFundManager(dataObject.getString("fundManager"));
                privateFund.setRaiseAmount(dataObject.getLongValue("issueSize") * 100);
                privateFund.setFundDuration(dataObject.getString("projectDuration"));
                privateFund.setRiskLevel(dataObject.getString("riskType"));
                privateFund.setApr(dataObject.getString("target"));
                privateFund.setCapitalUse(dataObject.getString("fundinvest"));
                privateFund.setRiskControl(dataObject.getString("riskControl"));
                privateFund.setIsShow(IsShowEnum.YES.getCode());
                privateFund.setRealAmount(dataObject.getLongValue("totalArrival"));
                privateFund.setFundName(dataObject.getString("fundName"));
                //设置基金期数
                JSONArray jsonArray = dataObject.getJSONArray("products");
                long currentTotalMoney = 0L;
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    for (Object object : jsonArray) {
                        JSONObject temp = (JSONObject) object;
                        try {
                            String esDate = temp.getString("establishDate");
                            Date establishDate = DateUtil.parseDate(esDate, "yyyy-MM-dd");
                            long productIssueScale = temp.getLongValue("productIssueScale") * 100;
                            temp.put("productIssueScale",productIssueScale);
                            if (establishDate != null) {
                                Calendar cal = Calendar.getInstance();
                                int nowYear = cal.get(Calendar.YEAR);
                                cal.setTime(establishDate);
                                int esYear = cal.get(Calendar.YEAR);
                                if (nowYear == esYear) {
                                    currentTotalMoney += productIssueScale;
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("[基金数据转换错误]", e);
                        }
                    }
                }
                privateFund.setCurrentTotalMoney(currentTotalMoney);
                privateFund.setPeriodInfo(jsonArray == null ? "" : jsonArray.toJSONString());
                int count = pbPrivateFundRepository.updatePrivateFundInfo(privateFund);
                if (count < 1) {
                    LOG.error("[同步私募基金]更新基金数据失败，基金信息:{}", jsonObject.toJSONString());
                }
            } else {
                LOG.error("[同步私募基金]未同步到基金数据，基金编号:{}", fundCode);
            }
        }
    }

    public List<String> findPrivateFundIdsWithPage(int pageNumber) {
        Pageable pageable = new PageRequest(pageNumber, ServiceIdentifier.PAGE_SIZE);
        return pbPrivateFundRepository.selectPrivateFundIds(pageable);
    }

    /**
     * 私募基金-列表
     */
    public JSONObject findPrivateFundWithPage(String fundCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            PbPrivateFund privateFund = pbPrivateFundRepository.findAllBypFundCode(fundCode);
            if (privateFund != null) {
                jsonObject = (JSONObject) JSON.toJSON(privateFund);
                return jsonObject;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私募基金单条显示
     */
    public JSONObject getPrivateFundInfo(String fundCode) {
        try {
            PbPrivateFund privateFund = pbPrivateFundRepository.findByFundCode(fundCode);
            if (privateFund != null) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(privateFund);
                return jsonObject;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私募基金更新
     */
    @Transactional
    public void updatePrivateFund(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                PbPrivateFund privateFund = JSONObject.parseObject(jsonObject.toJSONString(), PbPrivateFund.class);
                pbPrivateFundRepository.updatePrivateFund(privateFund);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
