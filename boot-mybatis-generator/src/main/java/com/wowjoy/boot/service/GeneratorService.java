package com.wowjoy.boot.service;

import com.wowjoy.boot.dao.GeneratorDao;
import com.wowjoy.boot.utils.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author scq
 */
@Slf4j
@Service
public class GeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return generatorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return generatorDao.queryTotal(map);
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColums(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (String tableName : tableNames) {
                // 查询表信息
                Map<String, String> table = queryTable(tableName);
                // 查询列信息
                List<Map<String, String>> columns = queryColumns(tableName);
                // 生成代码
                GeneratorUtil.generateCode(table, columns, zipOutputStream);
            }
        } catch (IOException e) {
            log.error("代码生成出错={}", e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

}
