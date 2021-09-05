package com.honsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.honsoft.springbatch.domain.Person;
import com.honsoft.springbatch.processor.PersonItemProcessor;
import com.honsoft.springbatch.writer.SimpleWriter;
@Configuration
@EnableBatchProcessing
public class PersonProcessingBatchConfig {
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
    			.<Person,String>chunk(10)
    			.reader(reader())
    			.writer(writer())
    			.processor(processor())
    			.build();
    }
    
    @Bean
    public FlatFileItemReader<Person> reader(){
    	return new FlatFileItemReaderBuilder<Person>()
    			.name("personReader")
    			.resource(new ClassPathResource("data/person.csv"))
    			.delimited()
    			.names(new String[] {"firstName", "lastName"})
    			//.fieldSetMapper(null)
    			.targetType(Person.class)
    			.build();
    }
    
    @Bean
    public FlatFileItemWriter<String> writer(){
    	return new FlatFileItemWriterBuilder<String>()
    			.name("personWriter")
    			.resource(new FileSystemResource("target/test-outputs/greeting.txt"))
    			.lineAggregator(new PassThroughLineAggregator<>())
    			.build();
    }
    
    @Bean
	public PersonItemProcessor processor() {
      return new PersonItemProcessor();
    }

}
