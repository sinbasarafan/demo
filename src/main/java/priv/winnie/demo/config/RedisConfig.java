package priv.winnie.demo.config;

import java.lang.reflect.Method;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import priv.winnie.demo.properties.RedisProperties;

@Configuration
@EnableCaching
public class RedisConfig {
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
	
	@Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        rcm.setDefaultExpiration(60);//秒
        return rcm;
    }
    
    @Bean
	public RedisConnectionFactory redisConnectionFactory() {
    	GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    	poolConfig.setMaxTotal(redisProperties.getMaxTotal()); // 池中最多有多少个线程
    	poolConfig.setMaxIdle(redisProperties.getMaxIdle()); // 最多拥有多少个空闲线程
    	poolConfig.setMinIdle(redisProperties.getMinIdle()); // 最少拥有多少个空闲线程
    	poolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis()); // 获取连接时的最大等待毫秒数
    	poolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow()); // 在获取连接的时候检查有效性
    	poolConfig.setSoftMinEvictableIdleTimeMillis(redisProperties.getSoftMinEvictableIdleTimeMillis()); // 对象空闲多久后逐出连接池
    	DefaultLettucePool pool = new DefaultLettucePool(redisProperties.getIp(), redisProperties.getPort(), poolConfig);
    	pool.afterPropertiesSet();
    	RedisConnectionFactory factory = new LettuceConnectionFactory(pool);
	    return factory;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
