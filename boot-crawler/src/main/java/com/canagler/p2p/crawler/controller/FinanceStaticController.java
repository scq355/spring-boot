package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.domain.FinanceStatic;
import com.canagler.p2p.crawler.service.FinanceStaticService;
import com.canagler.p2p.crawler.util.ExcelUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by scq on 2018-07-16 10:49:28
 */
@Controller
@RequestMapping(value = "/financeStatic")
public class FinanceStaticController extends BaseController {

	@Autowired
	private FinanceStaticService financeStaticService;

	/**
	 * 数据统计
	 */
	@GetMapping(value = "/list")
	public String financeStatistics(@RequestParam(required = false, defaultValue = "") String webName,
									@RequestParam(defaultValue = "1") Integer pageNumber,
									Model model) {
		model.addAttribute("webName", webName);
		model.addAttribute("pageNumber", pageNumber);

		Page<FinanceStatic> financeStaticPage = financeStaticService.findList(webName, pageNumber);

		if (financeStaticPage.getContent().size() > 0) {
			model.addAttribute("nowDate", financeStaticPage.getContent().get(0).getNow());
			model.addAttribute("previousDate", financeStaticPage.getContent().get(0).getPrevious());
		}
		model.addAttribute("pageNum", financeStaticPage.getTotalPages());
		model.addAttribute("count", financeStaticPage.getTotalElements());
		model.addAttribute("financeStaticList", financeStaticPage.getContent());
		model.addAttribute("hasNext", financeStaticPage.hasNext());
		model.addAttribute("hasPrevious", financeStaticPage.hasPrevious());

		return "finance-static/static";
	}


	/**
	 * 统计导表
	 */
	@GetMapping(value = "/export")
	public void financeStatistics() {
		List<FinanceStatic> financeStaticDtoList = financeStaticService.findAll();
		List<Map<String, Object>> list = new ArrayList<>();

		for (FinanceStatic finance : financeStaticDtoList) {
			Map<String, Object> map = new HashMap<>();
			map.put("webName", finance.getWebName());
			map.put("deadline", finance.getDeadline() + "天");
			map.put("totalTransfer", finance.getTotalTransfer() + "元");
			map.put("totalTransferPrevious", finance.getTotalTransferPrevious() + "元");
			map.put("diff", finance.getTotalTransfer().subtract(finance.getTotalTransferPrevious() == null ? new BigDecimal(0.00) : finance.getTotalTransferPrevious()));
			list.add(map);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("list", list);
		String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xlsx";
		ExcelUtils.exportToExcel("finance-template.xlsx", params, filename);
	}

}
