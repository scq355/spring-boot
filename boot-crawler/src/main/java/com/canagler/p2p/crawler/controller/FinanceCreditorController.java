package com.canagler.p2p.crawler.controller;

import com.canagler.p2p.crawler.domain.FinanceCreditor;
import com.canagler.p2p.crawler.service.FinanceCreditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 债权转让
 * Created by scq on 2018-07-12 10:25:02
 */
@Controller
@RequestMapping(value = "/financeCreditor")
public class FinanceCreditorController extends BaseController {
	@Autowired
	private FinanceCreditorService financeCreditorService;

	@GetMapping(value = {"/list", ""})
	public String list(@RequestParam(required = false, defaultValue = "") String name,
					   @RequestParam(defaultValue = "1") Integer pageNumber,
					   Model model) {
		model.addAttribute("name", name);
		model.addAttribute("pageNumber", pageNumber);

		Page<FinanceCreditor> financeCreditorPage = financeCreditorService.findList(pageNumber, name);

		model.addAttribute("pageNum", financeCreditorPage.getTotalPages());
		model.addAttribute("count", financeCreditorPage.getTotalElements());
		model.addAttribute("financeCreditorList", financeCreditorPage.getContent());
		model.addAttribute("hasNext", financeCreditorPage.hasNext());
		model.addAttribute("hasPrevious", financeCreditorPage.hasPrevious());

		return "finance-creditor/index";
	}

}
