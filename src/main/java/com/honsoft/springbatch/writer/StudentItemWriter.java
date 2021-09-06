package com.honsoft.springbatch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.honsoft.springbatch.domain.StudentDTO;

public class StudentItemWriter implements ItemWriter<StudentDTO>{
	private static Logger logger = LoggerFactory.getLogger(StudentItemWriter.class);
	
	@Override
	public void write(List<? extends StudentDTO> items) throws Exception {
		logger.info("write called");
		for (StudentDTO studentDTO : items) {
			logger.info(studentDTO.getName());
		}
	}

}
