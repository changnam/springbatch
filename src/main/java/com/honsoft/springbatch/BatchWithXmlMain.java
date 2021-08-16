package com.honsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchWithXmlMain { 
	   public static void main(String[] args)throws Exception { 
	  
	      // System.out.println("hello"); 
	      String[] springConfig  =  {"spring/batch/jobs/job_hello_world.xml"};  
	      
	      // Creating the application context object  
	      ApplicationContext context = new ClassPathXmlApplicationContext(springConfig); 
	      
	      // Creating the job launcher 
	      JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher"); 
	  
	      // Creating the job 
	      Job job = (Job) context.getBean("helloWorldJob"); 
	  
	      // Executing the JOB 
	      JobExecution execution = jobLauncher.run(job, new JobParameters()); 
	      System.out.println("Exit Status : " + execution.getStatus()); 
	   }    
	}
