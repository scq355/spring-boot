package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.common.Result;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.service.WebInfoOptionService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待抓取网页分页方式，登录要求相关配置
 * Created by scq on 2018-04-11 17:00:18
 */
@Controller
@RequestMapping(value = "/webInfoOption")
public class WebInfoOptionController extends BaseController {

	@Autowired
	private WebInfoOptionService webInfoOptionService;

	@GetMapping(value = "/list")
	public String list(Model model) {
		List<WebInfoOption> webInfoOptionList = webInfoOptionService.list();
		model.addAttribute("webInfoOptionList", webInfoOptionList);
		return "web-info-option/index";
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public Result saveWebInfoOption(Integer pagingType,
									Integer loginRequired,
									Integer requestType,
									@RequestParam(name = "webUrl") String id,
									@RequestParam(name = "webName") String name) {
		webInfoOptionService.save(id, requestType, pagingType, loginRequired, name);
		return Result.SUCCESS.copyThis();
	}

	@PostMapping(value = "/delete")
	@ResponseBody
	public Result deleteWebInfoOption(String id) {
		webInfoOptionService.deleteById(id);
		return Result.SUCCESS.copyThis();
	}

	@PostMapping(value = "/update")
	@ResponseBody
	public Result updateWebInfoOption(String id, Integer requestType, Integer pagingType, Integer loginRequired, String name) {
		UpdateResult updateResult = webInfoOptionService.updateWebInfoOption(id, requestType, pagingType, loginRequired, name);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(updateResult);
		return result;
	}
}
