package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.*;
import cn.com.jcgroup.admin.manage.AccountManage;
import cn.com.jcgroup.service.domain.PbBillPayAble;
import cn.com.jcgroup.service.service.SubProjectService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.*;

/**
 * Description: 台账(应收，应付，月度)
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午9:08
 */
@RestController
@RequestMapping(AdminIdentifier.Url.SUB_PROJECT)
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountManage accountManage;
    @Autowired
    private SubProjectService subProjectService;

    /**
     * 月度台账详情显示
     */
    @RequestMapping(SUB_PROJECT_MONTHLY_ACCOUNT_SHOW)
    public ApiResult monthlyAccountInfoShow(@RequestBody MonthlyAccountDto monthlyAccountDto) {
        JSONObject jsonObject = accountManage.monthlyAccountShow(monthlyAccountDto.getId());
        if (jsonObject != null) {
            return new ApiResult(jsonObject);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "月度台账显示异常");
        }
    }

    /**
     * 应付台账详情-编辑
     */
    @RequestMapping(value = SUB_PROJECT_ACCOUNT_PAYFOR_EDIT)
    public ApiResult accountPayForInfoEdit(@RequestBody AccountPayForDto accountPayForDto) {
        accountManage.accountPayforEdit(accountPayForDto);
        return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应付台账更新成功");
    }

    /**
     * 应收台账详情-编辑
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYABLE_EDIT)
    public ApiResult accountPayAbleInfoEdit(@RequestBody AccountPayAbleDto accountPayAbleDto) {
        try {
            accountManage.accountPayableEdit(accountPayAbleDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应收台账更新成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "应收台账更新失败");
    }

    /**
     * 月度台账编辑
     */
    @RequestMapping(SUB_PROJECT_MONTHLY_ACCOUNT_EDIT)
    public ApiResult monthlyAccountEdit(@RequestBody MonthlyAccountDto monthlyAccountDto) {
        try{
            subProjectService.editMonthlyAccount(monthlyAccountDto.getIs_show(), monthlyAccountDto.getId(),
                    monthlyAccountDto.getPaper_name(), monthlyAccountDto.getYear_month());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "月度台账更新成功");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "月度台账更新失败");
    }

    /**
     * 月度台账删除
     */
    @RequestMapping(SUB_PROJECT_MONTHLY_ACCOUNT_DELETE)
    public ApiResult monthlyAccountDelete(@RequestBody MonthlyAccountDto monthlyAccountDto) {
        try {
            subProjectService.deleteMonthlyAccount(monthlyAccountDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, " 月度台账删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "月度台账删除失败");
    }

    /**
     * 应收台账-新建
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYABLE_ADD)
    public ApiResult accountPayAbleAdd(@RequestBody AccountPayAbleDto accountPayAbleDto) {
        PbBillPayAble pbBillPayAble = accountManage.accountPayableAdd(accountPayAbleDto);
        if (pbBillPayAble != null) {
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应收台账新建成功");
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "应收台账新建失败");
        }
    }

    /**
     * 应付台账-新建
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYAFOR_ADD)
    public ApiResult accountPayForInfoAdd(@RequestBody AccountPayForDto accountPayForDto) {
        try {
            accountManage.accountPayforAdd(accountPayForDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应付台账新建成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "应付台账新建失败");
    }

    /**
     * 应付台账详情-显示
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYFOR_SHOW)
    public ApiResult accountPayForInfoShow(@RequestBody AccountPayForDto accountPayForDto) {
        JSONObject jsonObject = accountManage.accountPayforInfoShow(accountPayForDto.getId());
        if (jsonObject != null) {
            return new ApiResult(jsonObject);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "应付台账详情显示失败");
        }
    }

    /**
     * 应收台账详情-显示
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYABLE_SHOW)
    public ApiResult accountPayAbleInfoShow(@RequestBody AccountPayAbleDto accountPayAbleDto) {
        JSONObject jsonObject = accountManage.accountPayableInfoShow(accountPayAbleDto.getId());
        if (jsonObject != null) {
            return new ApiResult(jsonObject);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "应收台账详情显示异常");
        }
    }

    /**
     * 应付台账-删除
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYFOR_DELETE)
    public ApiResult accountPayForDelete(@RequestBody AccountDto accountDto) {
        try {
            accountManage.accountPayforDelete(accountDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应付台账删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "应付台账删除失败");
    }

    /**
     * 应收台账-删除
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYABLE_DELETE)
    public ApiResult accountPayAbleDelete(@RequestBody AccountDto accountDto) {
        try {
            accountManage.accountPayableDelete(accountDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "应付台账删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "应付台账删除失败");
    }

    /**
     * 月度台账列表
     */
    @RequestMapping(SUB_PROJECT_MONTHLY_ACCOUNT_LIST)
    public ApiResult monthlyAccountList(@RequestBody SubProjectDto subProjectDto) {
        JSONArray jsonArray = accountManage.monthlyAccountList(subProjectDto.getSub_pro_code(),
                subProjectDto.getPage());
        if (jsonArray != null) {
            return new ApiResult(jsonArray);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "月度台账列表显示异常");
        }
    }

    /**
     * 应付台账-列表
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYFOR_LIST)
    public ApiResult accountPayForList(@RequestBody SubProjectDto subProjectDto) {
        JSONArray jsonArray = accountManage.accountPayforList(subProjectDto.getSub_pro_code(), subProjectDto.getPage());
        if (jsonArray != null) {
            return new ApiResult(jsonArray);
        } else {
            return new ApiResult(ResCodeEnum.SERVER_ERROR, "应付台账列表显示异常");
        }
    }

    /**
     * 应收台账-列表
     */
    @RequestMapping(SUB_PROJECT_ACCOUNT_PAYABLE_LIST)
    public ApiResult accountPayAbleList(@RequestBody SubProjectDto subProjectDto) {
        JSONArray jsonArray = accountManage.accountPayableList(subProjectDto.getSub_pro_code(), subProjectDto.getPage());
        if (jsonArray != null) {
            return new ApiResult(jsonArray);
        } else {

            return new ApiResult(ResCodeEnum.SERVER_ERROR, "应收台账列表显示异常");
        }
    }
}
