package com.honsoft.springbatch.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.honsoft.springbatch.domain.StudentDTO;
import com.honsoft.springbatch.domain.UserDto;

@Configuration
@EnableBatchProcessing
@PropertySource(value = { "classpath:file.properties" }, ignoreResourceNotFound = true)
public class FlatFileReaderSampleJobConfig {

	@Autowired
	Environment environment;

	@Autowired
	JobBuilderFactory jobBuilder;

	@Autowired
	StepBuilderFactory stepBuilder;

	@Value(value = "${filePath}")
	private String filePath;

	@Value(value = "${fileName}")
	private String fileName;

	@Bean
	public Job myJob(Step step1) {
		return jobBuilder.get("myJob").start(step1).build();
	}

	@Bean
	public Step step1() {
		return stepBuilder.get("step1").<StudentDTO, StudentDTO>chunk(10).reader(itemReader()).processor(itemProcessor())
				.writer(itemWriter()).build();
	}

	 @Bean
	    public ItemReader<StudentDTO> itemReader() {
	        LineMapper<StudentDTO> studentLineMapper = createStudentLineMapper();
	 
	        return new FlatFileItemReaderBuilder<StudentDTO>()
	                .name("studentReader")
	                .resource(new ClassPathResource("data/students.csv"))
	                .linesToSkip(1)
	                .lineMapper(studentLineMapper)
	                .build();
	    }
	 

	@Bean
	public ItemWriter<StudentDTO> itemWriter() {
		String exportFilePath = environment.getRequiredProperty("batch.job.export.file.path");
		Resource exportFileResource = new FileSystemResource(exportFilePath+"copied");

		String exportFileHeader = environment.getRequiredProperty("batch.job.export.file.header");
		StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);

		LineAggregator<StudentDTO> lineAggregator = createStudentLineAggregator();

		return new FlatFileItemWriterBuilder<StudentDTO>().name("userItemWriter").headerCallback(headerWriter)
				.lineAggregator(lineAggregator).resource(exportFileResource).build();
	}

	@Bean
	public ItemProcessor itemProcessor() {
		return new SampleFileProcessor();

	}

	private LineAggregator<StudentDTO> createStudentLineAggregator() {
		DelimitedLineAggregator<StudentDTO> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(";");

		FieldExtractor<StudentDTO> fieldExtractor = createStudentFieldExtractor();
		lineAggregator.setFieldExtractor(fieldExtractor);

		return lineAggregator;
	}

	private FieldExtractor<StudentDTO> createStudentFieldExtractor() {
		BeanWrapperFieldExtractor<StudentDTO> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] { "name", "emailAddress", "purchasedPackage" });
		return extractor;
	}
	
	private LineMapper<StudentDTO> createStudentLineMapper() {
        DefaultLineMapper<StudentDTO> studentLineMapper = new DefaultLineMapper<>();
 
        LineTokenizer studentLineTokenizer = createStudentLineTokenizer();
        studentLineMapper.setLineTokenizer(studentLineTokenizer);
 
        FieldSetMapper<StudentDTO> studentInformationMapper =
                createStudentInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);
 
        return studentLineMapper;
    }
 
    private LineTokenizer createStudentLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames(new String[]{
                "name", 
                "emailAddress", 
                "purchasedPackage"
        });
        return studentLineTokenizer;
    }
 
    private FieldSetMapper<StudentDTO> createStudentInformationMapper() {
        BeanWrapperFieldSetMapper<StudentDTO> studentInformationMapper =
                new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(StudentDTO.class);
        return studentInformationMapper;
    }
    

}

class SampleFileProcessor implements ItemProcessor<StudentDTO, StudentDTO> {

	@Override
	public StudentDTO process(StudentDTO item) throws Exception {

		item.setName(item.getName() + " added by processor");
		return item;
	}

}

class StringHeaderWriter implements FlatFileHeaderCallback {

	private final String header;

	StringHeaderWriter(String header) {
		this.header = header;
	}

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(header);

	}
}
