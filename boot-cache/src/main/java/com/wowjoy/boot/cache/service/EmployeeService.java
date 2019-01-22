package com.wowjoy.boot.cache.service;

import com.wowjoy.boot.cache.bean.Employee;
import com.wowjoy.boot.cache.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;


@CacheConfig(cacheNames = "emp")
@Slf4j
@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 将方法结果进行缓存
     * condition = "#id > 0"
     * unless = "#result == null"
     * key = "#root.args[0]"
     * ======================================================================================
     * CacheConfigurationImportSelector
     *      CacheConfigurationImportSelector
     *          selectImports(AnnotationMetadata importingClassMetadata)
     *
     * SimpleCacheConfiguration:在容器中注册一个CacheManager(ConcurrentMapCacheManager)
     * 可以获取和创建ConcurrentMapCacheManager类型的缓存组件，将数据保存在ConcurrentMap中
     *
     * 方法执行之前，先去查询缓存组件Cache,按照CacheNames指定的名字获取（CacheManager获取相应的缓存）
     * 从Cache中查找缓存的内容，默认通过方法参数获取。key是按照某种策略生成（默认使用SimpleKeyGenerator生成）
     * SimpleKeyGenerator：
     *      如果没有参数，key = new SimpleKey();
     *      如果有一个参数，key = 参数的值
     *      如果有多个参数，key = new SimpleKey(params);
     * 若未查到缓存则调用目标方法
     * 将目标方法返回结果放入缓存
     *
     *
     * 1.使用CacheManager[ConcurrentMapCacheManager]按名字得到Cache[ConcurrentMapCache]组件
     * 2.key使用KeyGenerator[SimpleKeyGenerator]生成
     * ======================================================================================
     * cacheNames：指定缓存组件名字，将方法结果放在哪个缓存中，数组方式，可同时指定放入多个缓存
     * key：缓存数据使用的关键字，默认使用方法参数的值
     * keyGenerator：key的生成器，可以自定义，与key二选一
     * condition：符合指定条件会缓存，condition = "#a0 > 1"：第一个参数值大于1时，会缓存
     * unless：否定缓存，当unless指定条件为true，结果不会被缓存
     * sync：是否使用异步模式，unless情况下不支持
     * ======================================================================================
     */
    /*@Cacheable(cacheNames = {"emp"}, key = "#root.methodName + '[' + #id + ']'")*/
    /*@Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator", condition = "#a0 > 1", unless = "#a0 == 2")*/
    @Cacheable(cacheNames = {"emp"})
    public Employee getEmp(Integer id) {
        log.info("查询"  + id + "号员工");
        return employeeMapper.getEmpById(id);
    }

    /**
     * @CachePut： 既调用方法又更新缓存数据
     * 修改了数据库的某个数据，同时更新缓存
     *
     * 机制：
     * 1.先调用目标方法
     * 2.将目标方法的结果缓存起来
     *
     * 操作步骤：
     * 1.查询1号员工，结果放入缓存：[key:1, value: lastName=***]
     * 2.以后查询1号员工
     * 3.更新1号员工：[key：employee， value：employee]， #employee.id
     *      @Cacheable 中的key不可以使用result
     * 4.查询1号员工？未更新之前的数据
     */
    /*@CachePut(cacheNames = "emp", key = "#result.id")*/
    @CachePut(key = "#result.id")
    public Employee updateEmp(Employee employee) {
        log.info("更新员工：{}", employee.toString());
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * 缓存清空
     *
     * key = "#id"：清除指定缓存
     * allEntries = true：清除所有emp中的缓存
     * beforeInvocation = false：默认在方法执行之后清除缓存,如果出现异常，缓存不会被清除
     */
    /*@CacheEvict(cacheNames = "emp", key = "#id")*/
    /*@CacheEvict(cacheNames = "emp", allEntries = true)*/
    /*@CacheEvict(cacheNames = "emp", beforeInvocation = true)*/
    @CacheEvict(beforeInvocation = true)
    public void deleteEmp(Integer id) {
        log.info("删除员工：{}", id);
//        employeeMapper.deleteEmpById(id);
        int i = 10/0;
    }


    @Caching(
            cacheable = {
                    @Cacheable(value = "emp", key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp", key = "#result.id"),
                    @CachePut(value = "emp", key = "#result.email")
            }
    )
    public Employee getByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
