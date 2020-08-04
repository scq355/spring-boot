package com.wowjoy.boot.cache.service;

import com.wowjoy.boot.cache.bean.Department;
import com.wowjoy.boot.cache.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "dept")
@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(key = "#id")
    public Department getById(Integer id) {
        log.info("查詢部門ID為 {} 的部門", id);
        Department department = departmentMapper.getDeptById(id);
        // 操作緩存
        Cache cache = cacheManager.getCache("dept");
        cache.put("dept-01", department);
        return department;
    }
}
