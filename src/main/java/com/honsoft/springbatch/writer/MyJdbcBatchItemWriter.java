package com.honsoft.springbatch.writer;

import java.util.List;

import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.honsoft.springbatch.domain.StudentDTO;

public class MyJdbcBatchItemWriter extends JdbcBatchItemWriter<StudentDTO>{
@Override
public void write(List<? extends StudentDTO> items) throws Exception {
	logger.info("write called");
	super.write(items);
}
}
