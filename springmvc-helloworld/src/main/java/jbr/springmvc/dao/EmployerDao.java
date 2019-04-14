package jbr.springmvc.dao;
import jbr.springmvc.Employee;
//import jbr.springmvc.model.EmployeeEntity;
import jbr.springmvc.model.EmployerEntity;

import java.util.*;

public interface EmployerDao {
	//void register(Employee employee);
	
	public List<EmployerEntity> getAllEmployers();
	
	public void addEmployer(EmployerEntity employer);
	
	public EmployerEntity getEmployer (Integer employeeId);
	

	public void updateEmployer (EmployerEntity employer);
}
