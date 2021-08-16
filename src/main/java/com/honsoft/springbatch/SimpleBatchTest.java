package com.honsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.honsoft.springbatch.config.EndOfDayJobConfiguration;

public class SimpleBatchTest {
	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		// ClassPathXmlApplicationContext appContext = null;
		AnnotationConfigApplicationContext appContext = null;
		try {

			// appContext = new
			// ClassPathXmlApplicationContext("classpath:applicationBatchContext.xml");
			appContext = new AnnotationConfigApplicationContext(EndOfDayJobConfiguration.class);

			// get the launcher
			// JobLauncher jobLauncher = (JobLauncher) appContext.getBean("jobLauncher");
			JobLauncher jobLauncher = (JobLauncher) appContext.getBean(JobLauncher.class);
			// get the job to run
			Job job = (Job) appContext.getBean("endOfDay");
			// run
			jobLauncher.run(job, new JobParameters());
			
		}
		


		finally {
			appContext.close();
		}

	}
}
