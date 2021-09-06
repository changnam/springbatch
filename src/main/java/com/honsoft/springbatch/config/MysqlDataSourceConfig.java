package com.honsoft.springbatch.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
@EnableJpaRepositories(basePackages = "com.honsoft.springbatch.repository", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlJpaTransactionManager")
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

	@Bean(name = "mysqlJpaTransactionManager")
	public PlatformTransactionManager mysqlJpaTransactionManager() {
		EntityManagerFactory factory = mysqlEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}
	
	// jpa
	@PersistenceContext(unitName = "mysqlUnit")
	@Bean(name = "mysqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(mysqlDataSource());
		factory.setPackagesToScan(new String[] { "com.honsoft.springbatch.entity" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);

		return factory;
	}
}
