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
import jbr.springmvc.model.EmployerEntity;

//import jbr.springmvc.Employee;

@Repository
@Transactional
public class EmployerDaoImpl implements EmployerDao{

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
	
	public List<EmployerEntity> getAllEmployers(){
		List<EmployerEntity> employers = manager.createQuery("Select a From EmployerEntity a", EmployerEntity.class).getResultList();
		return employers;
	}
	
	

	public void addEmployer(EmployerEntity employer) {
		// TODO Auto-generated method stub
		System.out.println("manager"+ manager);
		manager.persist(employer);
		
	}
	
	public EmployerEntity getEmployer(Integer employerId) {
		// TODO Auto-generated method stub
		
		return manager.find(EmployerEntity.class, employerId);
	}
	
	public void updateEmployer(EmployerEntity employer) {
	// TODO Auto-generated method stub
	manager.persist(employer);
}
	
	public void deleteEmployer(Integer employerId) {
		// TODO Auto-generated method stub
		EmployerEntity x=manager.find(EmployerEntity.class, employerId);
		  manager.remove(x);

	}

}
