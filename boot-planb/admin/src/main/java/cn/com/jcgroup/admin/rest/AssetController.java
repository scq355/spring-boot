package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.dto.AssetDto;
import cn.com.jcgroup.service.service.AssetService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.ASSET_QUERY;

/**
 * Description: 资产形成
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午1:54
 */
@RestController
@RequestMapping(AdminIdentifier.Url.PROJECT)
public class AssetController {

    @Autowired
    AssetService assetService;

    /**
     * 资产形成-查询
     */
    @RequestMapping(ASSET_QUERY)
    public ApiResult assetQuery(@RequestBody AssetDto assetDto) {
        JSONArray jsonArray = assetService.queryAsset(assetDto.getDate(), assetDto.getPro_code());
        return new ApiResult(jsonArray);
    }

//    /**
//     * 资产形成-修改
//     */
//    @RequestMapping(ASSET_UPDATE)
//    public ApiResult assetUpdate(@RequestBody AssetDto assetDto) {
//        boolean resUpdate = assetService.updateAsset(assetDto.getDate(), assetDto.getPro_code());
//        return new ApiResult(resUpdate);
//    }

}
