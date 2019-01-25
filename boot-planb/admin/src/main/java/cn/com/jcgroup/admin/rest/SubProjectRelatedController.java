package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.SubProAmountDto;
import cn.com.jcgroup.admin.dto.SubProPaidAmountDto;
import cn.com.jcgroup.admin.manage.SubProjectManage;
import cn.com.jcgroup.service.service.SubProjectAmountService;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.com.jcgroup.admin.constant.AdminIdentifier.Url.*;

/**
 * Description: 项目相关（工程量/已付资金）
 * User: sunchangqing
 * Date: 2017-06-24
 * Time: 下午4:46
 */
@RestController
@RequestMapping(AdminIdentifier.Url.SUB_PROJECT)
public class SubProjectRelatedController {

    private static final Logger LOG = LoggerFactory.getLogger(SubProjectRelatedController.class);

    @Autowired
    private SubProjectManage subProjectManage;
    @Autowired
    private SubProjectAmountService subProjectAmountService;

    /**
     * 工程量-列表
     */
    @RequestMapping(SUB_PROJECT_AMOUNT_LIST)
    public ApiResult amountList(@RequestBody SubProAmountDto subProAmountDto) {
        try {
            JSONArray jsonArray = subProjectManage.subProAmountList(subProAmountDto.getSub_pro_code(),
                    subProAmountDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "返回工程量列表失败");
    }

    /**
     * 已付资金-列表
     */
    @RequestMapping(SUB_PROJECT_PAID_AMOUNT_LIST)
    public ApiResult paidAmountList(@RequestBody SubProPaidAmountDto subProPaidAmountDto) {
        try {
            JSONArray jsonArray = subProjectManage.subProPaidAmountList(subProPaidAmountDto.getSub_pro_code(),
                    subProPaidAmountDto.getPage());
            return new ApiResult(jsonArray);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "已付资金列表出错");
    }

    /**
     * 工程量删除
     */
    @RequestMapping(SUB_PROJECT_AMOUNT_DELETE)
    public ApiResult amountDelete(@RequestBody SubProAmountDto subProAmountDto) {
        try {
            subProjectAmountService.subProAmountDelete(subProAmountDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "工程量删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程量删除失败");
    }

    /**
     * 已付资金-删除
     */
    @RequestMapping(SUB_PROJECT_PAID_AMOUNT_DELETE)
    public ApiResult paidAmountDelete(@RequestBody SubProPaidAmountDto subProPaidAmountDto) {
        try {
            subProjectAmountService.subProPaidAmountDelete(subProPaidAmountDto.getId());
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "已付资金删除成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "已付资金删除失败");
    }

    /**
     * 工程量-添加
     */
    @RequestMapping(SUB_PROJECT_AMOUNT_ADD)
    public ApiResult amountAdd(@RequestBody SubProAmountDto subProAmountDto) {
        try {
            subProjectManage.subProAmountAdd(subProAmountDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "工程量添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程量添加失败");
    }

    /**
     * 已付资金-添加
     */
    @RequestMapping(SUB_PROJECT_PAID_AMOUNT_ADD)
    public ApiResult paidAmountAdd(@RequestBody SubProPaidAmountDto subProPaidAmountDto) {
        try {
            subProjectManage.subProPaidAmountAdd(subProPaidAmountDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "已付资金添加成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "已付资金添加失败");
    }

    /**
     * 工程量-修改
     */
    @RequestMapping(SUB_PROJECT_AMOUNT_EDIT)
    public ApiResult amountEdit(@RequestBody SubProAmountDto subProAmountDto) {
        try {
            subProjectManage.subProAmountUpdate(subProAmountDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "工程量修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "工程量修改失败");
    }

    /**
     * 已付资金-修改
     */
    @RequestMapping(SUB_PROJECT_PAID_AMOUNT_EDIT)
    public ApiResult paidAmountEdit(@RequestBody SubProPaidAmountDto subProPaidAmountDto) {
        try {
            subProjectManage.subProPaidAmountUpdate(subProPaidAmountDto);
            return new ApiResult(ResCodeEnum.SERVER_SUCCESS, "已付资金修改成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiResult(ResCodeEnum.SERVER_ERROR, "已付资金修改失败");
    }

}
