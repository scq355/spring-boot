package com.wowjoy.boot.mongodb.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author scq
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    /**
     * 1：直接使用mongoDB客户端工具类操作
     * @return
     */
    @Bean
    public MongoOperations mongoOperations() {
        return new MongoTemplate(new MongoClient("localhost"), "github_db");
    }


    /**
     * 2：使用Spring容器提供的MongoClientFactoryBean，可将MongoDB异常转换为Spring的可移植DataAccessException层次结构中的异常
     * @return
     */
    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean() {
        MongoClientFactoryBean mongo= new MongoClientFactoryBean();
        mongo.setHost("localhost");
        mongo.setPort(27017);
        return mongo;
    }


    @Override
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(), "github_db");
    }

    /**
     * 3：注入mongoTemplate
     * @return
     */
    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "github_db");
    }

    @Override
    protected String getDatabaseName() {
        return "github_db";
    }


    @Override
    protected Collection<String> getMappingBasePackages() {
        List<String> basepackage = new ArrayList<>();
        basepackage.add("com.wowjoy.boot.mongodb.repository");
        return basepackage;
    }

    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter();
        mappingMongoConverter.setTypeMapper(customTypeMapper());
        return mappingMongoConverter;
    }

    @Bean
    public MongoTypeMapper customTypeMapper() {
        return new CustomMongoTypeMapper();
    }
}
