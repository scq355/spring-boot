package cn.com.jcgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class PlanbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanbApplication.class, args);
	}

	@Bean
    @Primary
    @ConfigurationProperties(prefix="planb.datasource")
    public DataSource dataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }
}
