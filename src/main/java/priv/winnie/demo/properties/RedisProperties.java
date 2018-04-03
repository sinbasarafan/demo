package priv.winnie.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:${spring.profiles.active}/redis.properties")
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {
	
	private String ip;
	private Integer port;
	private int maxTotal;
	private int maxIdle;
	private int minIdle;
	private Long maxWaitMillis;
	private boolean testOnBorrow;
	private Long softMinEvictableIdleTimeMillis;

}
