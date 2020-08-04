package com.wowjoy.boot.cache;

import com.wowjoy.boot.cache.bean.Employee;
import com.wowjoy.boot.cache.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    // 操作字符串
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    // 操作对象
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * String：opsForValue()
     * Set：opsForSet()
     * List：opsForList()
     * Hash：opsForHash()
     * ZSet：opsForZSet()
     */
    @Test
    public void testRedisOperation() {
        // 字符串操作
        stringRedisTemplate.opsForValue().set("msg", "Hello World!");
        String s = stringRedisTemplate.opsForValue().get("msg");
        log.info(s);

        stringRedisTemplate.opsForList().leftPush("k_list", "1");
        stringRedisTemplate.opsForList().leftPush("k_list", "2");
        stringRedisTemplate.opsForList().leftPush("k_list", "3");
        stringRedisTemplate.opsForList().leftPush("k_list", "4");
    }

    @Test
    public void testSaveObj() {
        /**
         * 默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis
         */
        Employee emp = employeeMapper.getEmpById(1);
        redisTemplate.opsForValue().set("emp4", emp);

        /**
         * 1. 自己将对象转换为json
         */
    }

}
