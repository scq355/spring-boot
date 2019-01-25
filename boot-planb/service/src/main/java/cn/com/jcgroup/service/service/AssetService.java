package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbAssetComposition;
import cn.com.jcgroup.service.enums.AssetCompositionEnum;
import cn.com.jcgroup.service.repositories.PbAssetCompositionReponsitory;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description:资产形成
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午2:11
 */
@Service
public class AssetService {

    private final Logger LOG = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    private PbAssetCompositionReponsitory pbAssetCompositionReponsitory;

    /**
     * 析产形成-查询
     */
    public JSONArray queryAsset(String date, String proCode) {
        JSONArray jsonArray = new JSONArray();
        Date paramDate = new Date();
        try {
            paramDate = DateUtil.covertToDate("yyyy.MM.dd HH:mm:ss", date + ".01 00:00:00");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        List<PbAssetComposition> assetList = pbAssetCompositionReponsitory.findByProCodeAndRecordTime(paramDate, proCode);
        if (assetList != null && !(assetList.isEmpty())) {
            for (PbAssetComposition asset : assetList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("asset_value", asset.getAssetValue());
                jsonObject.put("asset_type", asset.getAssetType());
                String assetName = AssetCompositionEnum.convertToEnum(asset.getAssetType()).getInfo();
                jsonObject.put("asset_name", assetName);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 资产形成-修改
     */
    public boolean updateAsset(JSONObject jsonObject) {
        boolean updateFlag = false;
        PbAssetComposition assetComposition = new PbAssetComposition();
        if (jsonObject != null) {
            assetComposition.setAssetType(jsonObject.getString("asset_type"));
            assetComposition.setAssetValue(Long.parseLong(jsonObject.getString("asset_value")));
            assetComposition.setRecordTime(new Date());
            assetComposition.setProCode(jsonObject.getString("pro_code"));
            PbAssetComposition resAsset = pbAssetCompositionReponsitory.saveAndFlush(assetComposition);
            if (resAsset != null) {
                updateFlag = true;
            }
            return updateFlag;
        }
        return updateFlag;
    }
}
