package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.common.Result;
import com.canagler.p2p.crawler.domain.WebToken;
import com.canagler.p2p.crawler.service.WebTokenService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录凭证
 * Created by scq on 2018-04-10 17:36:50
 */
@Controller
@RequestMapping(value = "/webToken")
public class WebTokenController extends BaseController {
	@Autowired
	private WebTokenService webTokenService;

	@GetMapping("/list")
	public String webTokenList(Model model) {
		List<WebToken> webTokenList = webTokenService.list();
		model.addAttribute("webTokenList", webTokenList);
		return "web-token/index";
	}

	/**
	 * 保存网页凭证
	 * @param url：ID
	 * @param name：网站名
	 * @param userName：用户名
	 * @param password：密码
	 * @param certificate：凭证
	 */
	@PostMapping("/save")
	@ResponseBody
	public Result webTokenSave(@RequestParam(name = "webUrl") String url,
							   @RequestParam(name = "webName") String name,
							   @RequestParam(name = "userName") String userName,
							   @RequestParam(name = "password") String password,
							   @RequestParam(name = "certificate") String certificate) {
		webTokenService.save(url, name, userName, password, certificate);
		return Result.SUCCESS.copyThis();
	}


	/**
	 * 更新网页配置
	 * @param id：ID
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	public Result updateWebToken(String id, String webName,
								  String userName, String password,
								  String certificate) {

		UpdateResult updateResult = webTokenService.updateWebToken(id, webName,
				userName, password, certificate);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(updateResult);
		return result;
	}


	/**
	 * 重置网页配置
	 * @param id：ID
	 */
	@PostMapping(value = "/reset")
	@ResponseBody
	public Result resetWebConfig(String id) {
		UpdateResult updateResult = webTokenService.updateWebToken(id, null, null, null, null);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(updateResult);
		return result;
	}

}
