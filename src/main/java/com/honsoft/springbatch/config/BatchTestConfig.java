package com.honsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.honsoft.springbatch.writer.SimpleWriter;
@Configuration
@EnableBatchProcessing
public class BatchTestConfig {
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
    			.<String,String>chunk(10)
    			.reader(reader())
    			.writer(new SimpleWriter())
    			.build();
    }
    
    @Bean
    public ItemReader<String> reader(){
    	return new FlatFileItemReaderBuilder<String>().name("ipReader")
    			.resource(new ClassPathResource("data/iplist.txt"))
    			//.delimited()
    			//.names(new String[] {})
    			//.fieldSetMapper(null)
    			.lineMapper(new PassThroughLineMapper())
    			.targetType(String.class)
    			.build();
    }
}
