package com.wowjoy.boot.task.controller;

import com.wowjoy.boot.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    @Autowired
    private AsyncService asyncService;

    @GetMapping("/info")
    public String info() {
        asyncService.outputInfo();
        return "success";
    }
}
