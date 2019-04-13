package jbr.springmvc.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jbr.springmvc.model.EmployeeEntity;

//import jbr.springmvc.Employee;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao{

	 @Autowired
	  DataSource datasource;
	  @Autowired
	  JdbcTemplate jdbcTemplate;
	  
	/*
	 * public void register(Employee employee) { String sql = "select * from users";
	 * System.out.println("INSIDE" + jdbcTemplate); List<Map<String, Object>>
	 * employees = jdbcTemplate.queryForList(sql); System.out.println(employees);
	 * 
	 * 
	 * }
	 */
	
	@PersistenceContext
    private EntityManager manager;
	
	public List<EmployeeEntity> getAllEmployees(){
		List<EmployeeEntity> employees = manager.createQuery("Select a From EmployeeEntity a", EmployeeEntity.class).getResultList();
		return employees;
	}
}
