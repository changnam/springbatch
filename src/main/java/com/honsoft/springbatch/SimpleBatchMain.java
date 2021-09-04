package com.honsoft.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.honsoft.springbatch.config.CommonBatchConfig;
import com.honsoft.springbatch.config.MysqlDataSourceConfig;
import com.honsoft.springbatch.config.SimpleBatchConfig;

public class SimpleBatchMain {
	private static Logger logger = LoggerFactory.getLogger(SimpleBatchMain.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SimpleBatchConfig.class);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		JobRepository jobRepository = (JobRepository) context.getBean("jobRepository"); 
		JobRegistry jobRegistry = (JobRegistry) context.getBean("jobRegistry");
		JobExplorer jobExplorer = (JobExplorer) context.getBean("jobExplorer");
		JobOperator jobOperator = (JobOperator) context.getBean("jobOperator");
		
		SimpleBatchConfiguration sbc =  (SimpleBatchConfiguration) context.getBean(SimpleBatchConfiguration.class);
		
		 JobParametersBuilder jobBuilder= new JobParametersBuilder();
         jobBuilder.addString("passstudentfilename", "PassedStudents");
         jobBuilder.addString("marksfileName","StudentReport");
         JobParameters jobParameters =jobBuilder.toJobParameters();
      
         
		logger.debug(sbc.toString());
		Job job = (Job) context.getBean("myJob");
		
		// 일반 jobLauncher 인경우 강제로 runidincremeter 를 호출해서 parameter 를 unique 하게 만들어야 한다.
		//RunIdIncrementer rii = new RunIdIncrementer();
		//JobParameters nextParameters = R.getNextJobParameters(job);
	    //     Map<String, JobParameter> map = new HashMap<String, JobParameter>(nextParameters.getParameters());
	    //     map.putAll(jobParameters.getParameters());
	    //     jobParameters = new JobParameters(map);
	         
		try {
			//jobLauncher.run(job, jobParameters);
			jobOperator.startNextInstance("myJob");
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
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnexpectedJobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("spring test");
	}

}
