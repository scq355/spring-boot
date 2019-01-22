package com.wowjoy.boot.cache.controller;

import com.wowjoy.boot.cache.bean.Employee;
import com.wowjoy.boot.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/emp/{id}")
    public Employee getEmployee(@PathVariable(value = "id") Integer id) {
        return employeeService.getEmp(id);
    }

    @GetMapping(value = "/emp")
    public Employee update(Employee employee) {
        return employeeService.updateEmp(employee);
    }

    @GetMapping(value = "/del/{id}")
    public String deleteEmp(@PathVariable(value = "id") Integer id) {
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping(value = "/emp/name/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable(value = "lastName") String lastName) {
        return employeeService.getByLastName(lastName);
    }
}
