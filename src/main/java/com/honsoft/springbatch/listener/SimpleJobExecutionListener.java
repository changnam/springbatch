package com.honsoft.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class SimpleJobExecutionListener implements JobExecutionListener{
	private static Logger logger = LoggerFactory.getLogger(SimpleJobExecutionListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("beforejob called");
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("afterjob called");
		
	}

}
