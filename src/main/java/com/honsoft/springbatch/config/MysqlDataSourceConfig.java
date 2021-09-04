package com.honsoft.springbatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
public class MysqlDataSourceConfig {
	@Autowired
	Environment env;
	
	@Bean(name = "mysqlDataSource", destroyMethod = "close")
	public DataSource mysqlDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(env.getProperty("mysql.datasource.hikari.url"));
		config.setDriverClassName(env.getProperty("mysql.datasource.hikari.driver-class-name"));
		config.setUsername(env.getProperty("mysql.datasource.hikari.username"));
		config.setPassword(env.getProperty("mysql.datasource.hikari.password"));
		
		HikariDataSource dataSource = new HikariDataSource(config);
		
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager mysqlTxManager(@Qualifier("mysqlDataSource") DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

}
