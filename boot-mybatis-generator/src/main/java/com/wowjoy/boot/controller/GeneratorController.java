package com.wowjoy.boot.controller;

import com.alibaba.fastjson.JSON;
import com.wowjoy.boot.common.Query;
import com.wowjoy.boot.common.Result;
import com.wowjoy.boot.service.GeneratorService;
import com.wowjoy.boot.utils.GeneratorUtil;
import com.wowjoy.boot.utils.PageUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author scq
 */
@Controller
@RequestMapping("/sys/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<Map<String, Object>> list = generatorService.queryList(query);
        int total = generatorService.queryTotal(query);

        PageUtil pageUtil = new PageUtil(list, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }


    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[]{};
        String tables = request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data = generatorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
