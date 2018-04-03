package priv.winnie.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import priv.winnie.demo.properties.DbProperties;

@Configuration
public class DbConfig {
	
	@Autowired
	private DbProperties dbProperties;
	
	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(dbProperties.getClassName());
		config.setJdbcUrl(dbProperties.getJdbcUrl());
		config.setUsername(dbProperties.getUsername());
		config.setPassword(dbProperties.getPassword());
		config.setMaximumPoolSize(dbProperties.getMaximumPoolSize());
		config.setMaxLifetime(dbProperties.getMaxLifetime());
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}

}
