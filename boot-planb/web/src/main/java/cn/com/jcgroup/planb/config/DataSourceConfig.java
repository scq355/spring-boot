package cn.com.jcgroup.planb.config;//package jc.it.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
///**
// * @author LiuYong on 17/5/24 下午12:01.
// */
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource() throws Exception {
//        return DataSourceBuilder.create().build();
//    }
//
//}
