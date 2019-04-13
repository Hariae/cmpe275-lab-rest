package jbr.springmvc.dao;

import java.util.List;
import java.util.Map;

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
		 String sql = "select * from users";
		 System.out.println("INSIDE" + jdbcTemplate);
      List<Map<String, Object>> employees = jdbcTemplate.queryForList(sql);
		    System.out.println(employees);
		 
		 
	}
}
