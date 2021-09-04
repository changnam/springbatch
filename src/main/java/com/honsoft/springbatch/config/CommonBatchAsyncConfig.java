package com.honsoft.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CommonBatchAsyncConfig extends DefaultBatchConfigurer{
	
	@Autowired
	@Qualifier("mysqlDataSource")
	DataSource mysqlDataSource;
	
	@Autowired
	@Qualifier("mysqlTxManager")
	PlatformTransactionManager mysqlTxManager;
	
	@Autowired
	@Qualifier("jobRepository")
	JobRepository jobRepository;
	
	@Override
	protected JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	    factory.setDataSource(mysqlDataSource);
	    factory.setTransactionManager(mysqlTxManager);
	    factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
	    //factory.setTablePrefix("BATCH_");
	    factory.setMaxVarCharLength(1000);
	    factory.afterPropertiesSet();
	    return factory.getObject();
	}
	
	@Override
	protected JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
}
