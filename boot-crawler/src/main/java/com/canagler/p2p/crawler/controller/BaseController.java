package com.canagler.p2p.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by scq on 2018-04-12 13:25:54
 */
@Controller
@RequestMapping(value = "/index")
public class BaseController {


    @GetMapping(value = "/icon")
    public String icon() {
        return "index";
    }
}
