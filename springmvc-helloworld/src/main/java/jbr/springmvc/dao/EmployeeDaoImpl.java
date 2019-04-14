package jbr.springmvc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jbr.springmvc.Employee;
import jbr.springmvc.EmployeeResult;
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

	public void addEmployee(EmployeeEntity employee) {
		// TODO Auto-generated method stub
		System.out.println("manager"+ manager);
		manager.persist(employee);
		
	}

	public EmployeeEntity getEmployee(Integer employeeId) {
		// TODO Auto-generated method stub
		
		return manager.find(EmployeeEntity.class, employeeId);
	}

	public List<EmployeeResult> getReportees(Integer managerId) {
		// TODO Auto-generated method stub
		
		List<EmployeeEntity> employees = manager.createQuery("Select a From EmployeeEntity a", EmployeeEntity.class).getResultList();
		List<EmployeeResult> reportees = new ArrayList<EmployeeResult>();
		
		for(int i=0;i<employees.size();i++) {
			if(employees.get(i).getManager() != null) {
				if(employees.get(i).getManager().equals(managerId) ) {
					EmployeeResult reportee = new EmployeeResult();
					reportee.setId(employees.get(i).getEmployeeId());
					reportee.setName(employees.get(i).getName());
					reportee.setTitle(employees.get(i).getTitle());
					reportees.add(reportee);
				}	
			}
			
		}
		
		
		return reportees;
	}
}
