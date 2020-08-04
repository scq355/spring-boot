package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.common.Result;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.enums.InfoTypeEnum;
import com.canagler.p2p.crawler.service.WebInfoService;
import com.google.common.base.Strings;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待检测网址信息总览
 * Created by scq on 2018-04-08 11:21:52
 */
@Controller
@RequestMapping(value = "/webInfo")
public class WebInfoController extends BaseController {
	@Autowired
	private WebInfoService webInfoService;

	/**
	 * 网址规则监控列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<WebInfo> webInfoList = webInfoService.findAllByIsShow(Constants.IS_SHOW);
		model.addAttribute("webInfoList", webInfoList);
		return "web-info/index";
	}

	/**
	 * 添加网址规则
	 * @param name:网址名称
	 * @param url:网站地址
	 * @param params:关键字
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveWebInfo(@RequestParam(name = "webName") String name,
							  @RequestParam(name = "webUrl") String url,
							  @RequestParam(name = "params", defaultValue = "") String params) {
		Integer infoType = InfoTypeEnum.INFO_DISCUSS.getType();
		if (Strings.isNullOrEmpty(params)) {
			infoType = InfoTypeEnum.INFO_FINANCE.getType();
		}
		webInfoService.save(name, url, params, infoType);
		return Result.SUCCESS.copyThis();
	}

	/**
	 * 页面“删除”操作
	 * @param isShow:显示状态
	 * @param webInfoId:网页ID
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public Result resetWebInfo(Integer isShow, String webInfoId) {
		UpdateResult updateResult = webInfoService.updateShowStatus(isShow, webInfoId);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(updateResult);
		return result;
	}

	/**
	 * 页面删除操作
	 * @param webInfoId:网页ID
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteWebInfo(String webInfoId) {
		webInfoService.deleteWebInfo(webInfoId);
		return Result.SUCCESS.copyThis();
	}

	/**
	 * 是否存在
	 * @param url：ID
	 */
	@RequestMapping(value = "/isUsable", method = RequestMethod.POST)
	@ResponseBody
	public Result isUsable(String url) {
		WebInfo webInfo = webInfoService.findById(url);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(webInfo);
		return result;
	}


	/**
	 * 更新网页信息
	 * @param name：网站名字
	 * @param params：关键字
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	public Result updateWebInfo(@RequestParam(name = "id") String id,
								@RequestParam(name = "webName") String name,
								@RequestParam(name = "params") String params) {
		webInfoService.update(id, name, params);
		return Result.SUCCESS.copyThis();
	}

	@PostMapping(value = "/lockStatus")
	@ResponseBody
	public Result lockStatus(String id) {
		webInfoService.updateProcessStatus(id, Constants.PROCESS_STATUS_INIT);
		return Result.SUCCESS.copyThis();
	}

}
