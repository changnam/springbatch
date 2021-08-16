package com.honsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.honsoft.springbatch.tasklet.FileExplorerTasklet;

@Configuration
@EnableBatchProcessing
public class FileExplorerBatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job fileExplorerJob() {
		return jobBuilderFactory.get("fileExplorerJob").start(step1()).build();
	}
	
	//tasklet 에 tasklet 인터페이스를 구현한 클래스를 넣는다. (프레임워크에서 execute 호출함)
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet(fileExplorerTasklet())
				.build();
	}
	
	@Bean 
	public Tasklet fileExplorerTasklet() {
		return new FileExplorerTasklet();
	}

}
