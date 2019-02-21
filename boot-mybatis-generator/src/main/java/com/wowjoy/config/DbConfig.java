package com.wowjoy.config;

import com.wowjoy.common.RException;
import com.wowjoy.dao.GeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置
 * @author scq
 */
@Configuration
public class DbConfig {
    @Value("${database.name}")
    private String database;
    @Autowired
    private GeneratorDao generatorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao(){
        if("mysql".equalsIgnoreCase(database)){
            return generatorDao;
        } else {
            throw new RException("不支持当前数据库：" + database);
        }
    }
}
