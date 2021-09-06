package com.honsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.honsoft.springbatch.domain.Person;
import com.honsoft.springbatch.domain.StudentDTO;
import com.honsoft.springbatch.processor.StudentItemProcessor;
import com.honsoft.springbatch.reader.InMemoryStudentReader;
import com.honsoft.springbatch.writer.StudentItemWriter;

@Configuration
@EnableBatchProcessing
public class StudentProcessingBatchConfig {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job myJob() {
    	return jobBuilderFactory.get("myJob").flow(step1()).end().build();
    }
    
    @Bean
    public Step step1() {
    	return stepBuilderFactory.get("step1")
    			.<StudentDTO,StudentDTO>chunk(10)
    			.reader(reader())
    			.writer(writer())
    			.processor(processor())
    			.build();
    }
    
    @Bean
    public InMemoryStudentReader reader() {
    	return new InMemoryStudentReader();
    }
    
    @Bean
    public StudentItemProcessor processor() {
    	return new StudentItemProcessor();
    }
    
    @Bean
    public StudentItemWriter writer() {
    	return new StudentItemWriter();
    }
	
}
