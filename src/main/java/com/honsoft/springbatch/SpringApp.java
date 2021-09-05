package com.honsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = null;
        try {
            applicationContext = new ClassPathXmlApplicationContext("classpath:spring/batch/jobs/job_xml_to_oracle.xml");
            JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
            Job job = (Job) applicationContext.getBean("gtnContractJob");
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Exit status:"+execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}