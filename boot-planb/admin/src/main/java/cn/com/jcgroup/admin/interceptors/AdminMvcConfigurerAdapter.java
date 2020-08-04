package cn.com.jcgroup.admin.interceptors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * spring mvc 配置中心
 *
 * @author LiuYong
 */
@Configuration
public class AdminMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Value("${server.dev.mode}")
    private boolean serverDevMode;
    
    @Autowired
    private CrossInterceptor crossInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截器
        registry.addInterceptor(crossInterceptor);
        super.addInterceptors(registry);
    }

    /**
     * 初始化验证器
     *
     * @author LiuYong
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * 将null字段返回为空字符串
     *
     * @author LiuYong
     */
    @Bean
    public MappingJackson2HttpMessageConverter customJacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeString("");
            }
        });
        converter.setObjectMapper(objectMapper);
        return converter;
    }

}
