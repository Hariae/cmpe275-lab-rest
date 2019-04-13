package jbr.springmvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import jbr.springmvc.Employee;

public class EmployeeDaoImpl implements EmployeeDao{

	 @Autowired
	  DataSource datasource;
	  @Autowired
	  JdbcTemplate jdbcTemplate;
	  
	public void register(Employee employee) {
		 String sql = "select * from users where 1=1";
		 
		 
	}
}
