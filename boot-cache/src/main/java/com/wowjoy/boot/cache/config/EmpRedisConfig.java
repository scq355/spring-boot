package com.wowjoy.boot.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * 引入redis-starter之后容器中保存的是RedisCacheManager
 * RedisCacheManager 通过创建 RedisCache 来作为缓存组件，RedisCache通过操作redis缓存数据
 * 默认保存数据[k-v]都是Object，利用序列化保存，RedisCacheManager操作redis的时候使用RedisTemplate<Object, Object>
 * RedisTemplate<Object, Object> 默认使用JDK序列化机制
 * RedisCacheConfiguration
 */
@Configuration
public class EmpRedisConfig {

    /**
     * 自定义redisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    /**
     * 自定义序列化方式
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        RedisSerializationContext.SerializationPair pair = RedisSerializationContext
                .SerializationPair
                .fromSerializer(serializer);

        return RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(pair);
    }
}
