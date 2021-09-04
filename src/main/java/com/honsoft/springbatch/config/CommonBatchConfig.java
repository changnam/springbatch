package com.honsoft.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CommonBatchConfig extends DefaultBatchConfigurer{
	
	@Autowired
	@Qualifier("mysqlDataSource")
	DataSource mysqlDataSource;
	
	@Autowired
	@Qualifier("mysqlTxManager")
	PlatformTransactionManager mysqlTxManager;
	
	@Autowired
	@Qualifier("jobRepository")
	JobRepository jobRepository;
	
	@Autowired
	@Qualifier("jobLauncher")
	JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("jobRegistry")
	JobRegistry jobRegistry;
	
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
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
	/**
	  * All injected dependencies for this bean are provided by the @EnableBatchProcessing
	  * infrastructure out of the box.
	  */
	 @Bean
	 public SimpleJobOperator jobOperator(JobExplorer jobExplorer,
	                                JobRepository jobRepository,
	                                JobRegistry jobRegistry) {

		SimpleJobOperator jobOperator = new SimpleJobOperator();

		jobOperator.setJobExplorer(jobExplorer);
		jobOperator.setJobRepository(jobRepository);
		jobOperator.setJobRegistry(jobRegistry);
		jobOperator.setJobLauncher(jobLauncher);

		return jobOperator;
	 }
	 
	 @Bean
	 public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
	     JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
	     postProcessor.setJobRegistry(jobRegistry);
	     return postProcessor;
	 }
}
