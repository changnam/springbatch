package com.honsoft.springbatch.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class JDBCBaseDao {
    private DataSource dataSource;
    private NamedParameterJdbcOperations namedJdbcTemplate;

    public JDBCBaseDao() {    
    }
    
    public void setDataSource(DataSource ds) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        this.dataSource = ds;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public NamedParameterJdbcOperations getNamedJdbcTemplate() {
        return namedJdbcTemplate;
    }

    @Autowired
    @Qualifier("namedParamJdbcTemplate")
    public void setNamedJdbcTemplate(NamedParameterJdbcOperations namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

}