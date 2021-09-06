package com.honsoft.springbatch.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.honsoft.springbatch.domain.StudentDTO;

public class StudentPreparedStatementSetter implements ItemPreparedStatementSetter<StudentDTO> {
	 
    @Override
    public void setValues(StudentDTO student, 
                          PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, student.getEmailAddress());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setString(3, student.getPurchasedPackage());
    }
}