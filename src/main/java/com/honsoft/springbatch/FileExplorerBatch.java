package com.honsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.honsoft.springbatch.config.FileExplorerBatchConfig;

public class FileExplorerBatch {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(FileExplorerBatchConfig.class);

		JobLauncher jobLauncher = (JobLauncher) context.getBean(JobLauncher.class);

		Job fileExplorerJob = (Job) context.getBean("fileExplorerJob");

		try {
			JobExecution jobExecution = (JobExecution) jobLauncher.run(fileExplorerJob, new JobParameters());
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
	}
}
