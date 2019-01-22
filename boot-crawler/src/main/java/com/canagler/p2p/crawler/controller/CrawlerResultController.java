package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.common.Result;
import com.canagler.p2p.crawler.domain.CrawlerResult;
import com.canagler.p2p.crawler.service.CrawlerResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 爬取结果
 * Created by scq on 2018-04-08 11:22:20
 */
@Controller
@RequestMapping(value = {"/crawler", ""})
public class CrawlerResultController extends BaseController {
	@Autowired
	private CrawlerResultService crawlerResultService;

	@RequestMapping(value = {"/list", ""})
	public String list(@RequestParam(required = false, defaultValue = "") String name,
					   @RequestParam(required = false, defaultValue = "0") Integer status,
					   @RequestParam(defaultValue = "1") Integer pageNumber,
					   Model model) {
		model.addAttribute("name", name);
		model.addAttribute("pageNumber", pageNumber);

		Page<CrawlerResult> crawlerResultPage = crawlerResultService.findList(status, pageNumber, name);

		model.addAttribute("pageNum", crawlerResultPage.getTotalPages());
		model.addAttribute("count", crawlerResultPage.getTotalElements());
		model.addAttribute("crawlerResultList", crawlerResultPage.getContent());
		model.addAttribute("hasNext", crawlerResultPage.hasNext());
		model.addAttribute("hasPrevious", crawlerResultPage.hasPrevious());
		return "crawler-result/index";
	}

	/**
	 * 更新抓取结果状态
	 * @param crawlerResultId：ID
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateCrawlerResult(@RequestParam(name = "id") String crawlerResultId) {
		CrawlerResult crawlerResult = crawlerResultService.findById(crawlerResultId);
		Integer resultNum = 0;
		if (crawlerResult != null) {
			resultNum = crawlerResultService.updateStatus(crawlerResultId);
		}
		Result result = Result.SUCCESS.copyThis();
		result.setContext(resultNum);
		return result;
	}
}
