package priv.winnie.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:${spring.profiles.active}/db.properties")
@ConfigurationProperties(prefix = "db")
@Data
public class DbProperties {
	
	private String className;
	private String jdbcUrl;
	private String username;
	private String password;
	private int maximumPoolSize;
	private Long maxLifetime;

}
