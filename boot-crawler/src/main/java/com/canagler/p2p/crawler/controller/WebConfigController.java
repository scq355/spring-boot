package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.common.Result;
import com.canagler.p2p.crawler.domain.WebConfig;
import com.canagler.p2p.crawler.service.WebConfigService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网页分页配置
 * Created by scq on 2018-04-08 11:22:09
 */
@Controller
@RequestMapping(value = "/webConfig")
public class WebConfigController extends BaseController {
	@Autowired
	private WebConfigService webConfigService;

	/**
	 * 保存待爬取的网站分页配置
	 * @param pageMethod：分页方式
	 * @param webInfoId：网页ID
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveConfig(String webInfoId, String pagingUrl,
							 String name, String pageMethod, String contentDetailSelector,
							 String pageContentSelector,
							 String pagingSelector, String pageSeparator,
							 @RequestParam(required = false, defaultValue = "0") Integer totalPageNum) {

		webConfigService.saveWebConfig(webInfoId, pagingUrl, name, pageMethod,
				contentDetailSelector, pageContentSelector, pagingSelector, pageSeparator, totalPageNum);

		return Result.SUCCESS.copyThis();
	}

	/**
	 * 网页配置列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<WebConfig> webConfigList = webConfigService.list();
		model.addAttribute("webConfigList", webConfigList);
		return "web-config/index";
	}


	/**
	 * 更新网页配置
	 * @param id：ID
	 * @param pagingUrl：分页地址
	 * @param pageMethod：分页方式
	 * @param pageContentSelector：内容选择样式
	 * @param pagingSelector：分页选择样式
	 * @param pageSeparator：分页分隔符
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	public Result updateWebConfig(String id, String pagingUrl,
								  String pageMethod, String pageContentSelector, String contentDetailSelector,
								  String pagingSelector, String pageSeparator, Integer totalPageNum) {

		UpdateResult updateResult = webConfigService.updateWebConfig(id, pagingUrl, pageMethod,
				pageContentSelector, contentDetailSelector, pagingSelector, pageSeparator, totalPageNum);
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

		UpdateResult updateResult = webConfigService.updateWebConfig(id, null, null,
				null, null,null, null, null);
		Result result = Result.SUCCESS.copyThis();
		result.setContext(updateResult);
		return result;
	}
}
