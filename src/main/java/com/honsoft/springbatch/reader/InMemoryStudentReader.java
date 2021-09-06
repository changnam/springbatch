package com.honsoft.springbatch.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.honsoft.springbatch.domain.StudentDTO;

public class InMemoryStudentReader implements ItemReader<StudentDTO> {
	private static Logger logger = LoggerFactory.getLogger(InMemoryStudentReader.class);
	
	private int nextStudentIndex;

	private List<StudentDTO> studentsList = new ArrayList<StudentDTO>();

	public InMemoryStudentReader() {
		initialize();
	}

	@Override
	public StudentDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		logger.info("student reader called");
				
		StudentDTO nextStudent = null;

		if (nextStudentIndex < studentsList.size()) {
			nextStudent = studentsList.get(nextStudentIndex);
			nextStudentIndex++;

		} else {
			nextStudentIndex = 0;
		}

		return nextStudent;
	}

	private void initialize() {
		
		for (int i = 0; i < 20; i++) {
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setName("student " + i);
			studentDTO.setEmailAddress("student " + i+"@mail");
			studentDTO.setPurchasedPackage("master " + i);
			studentsList.add(studentDTO);
		}
		nextStudentIndex = 0;
	}

	private void initialize1() {
		StudentDTO tony = new StudentDTO();
		tony.setEmailAddress("tony@gmail");
		tony.setName("tony");
		// tony.setPurchasedPackage("master");

		studentsList = Collections.unmodifiableList(Arrays.asList(tony));
		nextStudentIndex = 0;
	}
}
