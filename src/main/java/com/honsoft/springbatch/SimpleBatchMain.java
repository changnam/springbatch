package com.honsoft.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.honsoft.springbatch.config.SimpleBatchConfig;

public class SimpleBatchMain {
	private static Logger logger = LoggerFactory.getLogger(SimpleBatchMain.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SimpleBatchConfig.class);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		JobRepository jobRepository = (JobRepository) context.getBean("jobRepository"); 
		JobRegistry jobRegistry = (JobRegistry) context.getBean("jobRegistry");
		JobExplorer jobExplorer = (JobExplorer) context.getBean("jobExplorer");
		
		Job job = (Job) context.getBean("myJob");
		try {
			jobLauncher.run(job, new JobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("spring test");
	}

}
