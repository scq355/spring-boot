package com.wowjoy.boot.cache.controller;

import com.wowjoy.boot.cache.bean.Department;
import com.wowjoy.boot.cache.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/dept/{id}")
    public Department getDept(@PathVariable(value = "id") Integer id) {
        return departmentService.getById(id);
    }
}
