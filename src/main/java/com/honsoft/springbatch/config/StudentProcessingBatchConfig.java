package com.honsoft.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.honsoft.springbatch.domain.Person;
import com.honsoft.springbatch.domain.StudentDTO;
import com.honsoft.springbatch.processor.StudentItemProcessor;
import com.honsoft.springbatch.reader.InMemoryStudentReader;
import com.honsoft.springbatch.writer.StudentItemWriter;
import com.honsoft.springbatch.writer.StudentPreparedStatementSetter;

@Configuration
@EnableBatchProcessing
@Import({ MysqlDataSourceConfig.class })
public class StudentProcessingBatchConfig {

	private static final String QUERY_INSERT_STUDENT = "INSERT "
			+ "INTO students(email_address, name, purchased_package) " + "VALUES (?, ?, ?)";

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job myJob(@Qualifier("step2")Step step2) {
		return jobBuilderFactory.get("myJob").start(step1()).next(step2).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<StudentDTO, StudentDTO>chunk(10).reader(reader()).writer(writer())
				.processor(processor()).build();
	}

	@Bean
	public Step step2(@Qualifier("csvFileDatabaseItemWriter")ItemWriter jdbcItemWriter) {
		return stepBuilderFactory.get("step2").<StudentDTO, StudentDTO>chunk(10).reader(reader())
				.writer(jdbcItemWriter).processor(processor()).build();
	}

	@Bean
	public ItemReader<StudentDTO> reader() {
		return new InMemoryStudentReader();
	}

	@Bean
	public ItemProcessor<StudentDTO, StudentDTO> processor() {
		return new StudentItemProcessor();
	}

	@Bean
	public ItemWriter<StudentDTO> writer() {
		return new StudentItemWriter();
	}

	@Bean
	public ItemWriter<StudentDTO> csvFileDatabaseItemWriter(@Qualifier("mysqlDataSource") DataSource dataSource,
			NamedParameterJdbcTemplate jdbcTemplate) {
		JdbcBatchItemWriter<StudentDTO> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(jdbcTemplate);

		databaseItemWriter.setSql(QUERY_INSERT_STUDENT);

		ItemPreparedStatementSetter<StudentDTO> valueSetter = new StudentPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	@Bean
	NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
