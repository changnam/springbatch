package com.honsoft.springbatch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.honsoft.springbatch.domain.StudentDTO;

public class StudentItemProcessor implements ItemProcessor<StudentDTO, StudentDTO>{
	private static Logger logger = LoggerFactory.getLogger(StudentItemProcessor.class);
	
	@Override
	public StudentDTO process(StudentDTO item) throws Exception {
		logger.info(item.getName());
		item.setName(item.getName()+" processed item");
		return item;
	}

}
