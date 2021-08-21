package com.honsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.honsoft.springbatch.listener.ApplicationContextAwareBean;
import com.honsoft.springbatch.service.SimpleService;

@Configuration
@EnableBatchProcessing
public class SimpleBatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ApplicationContextAwareBean applicationContextAwareBean() {
		return new ApplicationContextAwareBean();
	}

	@Bean
	public Job myJob() {
		return jobBuilderFactory.get("myJob").start(myJobStep1()).build();
	}
	
	@Bean
	public Step myJobStep1() {
		return stepBuilderFactory.get("myJobStep1").tasklet(myTasklet()).build();
	}
	
	@Bean
	public MethodInvokingTaskletAdapter  myTasklet() {
		MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

		adapter.setTargetObject(fooDao());
		adapter.setTargetMethod("updateFoo");

		return adapter;
	}
	
	@Bean
	public SimpleService fooDao() {
		return new SimpleService() ;
	}
	
}
