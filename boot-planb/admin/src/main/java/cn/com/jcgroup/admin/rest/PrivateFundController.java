package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.CompanyDto;
import cn.com.jcgroup.admin.dto.PrivateFundDto;
import cn.com.jcgroup.admin.manage.CompanyManage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.*;

/**
 * Description: 私募基金
 * User: sunchangqing
 * Date: 2017-06-26
 * Time: 下午8:15
 */
@RestController
@RequestMapping(AdminIdentifier.Url.COMPANY)
public class PrivateFundController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private CompanyManage companyManage;

    /**
     * 私募基金列表
     */
    @RequestMapping(COMPANY_PRIVATE_FUND_LIST)
    public ApiResult privateFundList(@RequestBody CompanyDto companyDto) {
        JSONArray jsonArray = companyManage.buildPrivateFundList(companyDto.getCompany_code());
        if (jsonArray != null) {
            return new ApiResult(jsonArray);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "私募基金列表显示异常");
        }
    }


    /**
     * 私募基金显示
     */
    @RequestMapping(COMPANY_PRIVATE_FUND_SHOW)
    public ApiResult privateFundShow(@RequestBody PrivateFundDto privateFundDto) {
        try {
            JSONObject jsonObject = companyManage.privateFundShow(privateFundDto.getFundCode());
            return new ApiResult(jsonObject);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return new ApiResult(ResCodeEnum.SERVER_ERROR, "私募基金列表显示异常");
    }

    /**
     * 私募基金修改
     */
    @RequestMapping(COMPANY_PRIVATE_FUND_UPDATE)
    public ApiResult privateFundEdit(@RequestBody PrivateFundDto privateFundDto) {
        try {
            companyManage.privateFundUpdate(privateFundDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "私募基金修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "私募基金修改失败");
    }
}
