package jbr.springmvc.dao;
import jbr.springmvc.Employee;
import jbr.springmvc.EmployeeResult;
import jbr.springmvc.model.EmployeeEntity;

import java.util.*;

public interface EmployeeDao {
	//void register(Employee employee);
	
	public List<EmployeeEntity> getAllEmployees();
	
	public void addEmployee(EmployeeEntity employee);
	
	public EmployeeEntity getEmployee(Integer employeeId);
	
	public List<EmployeeResult> getReportees(Integer employeeId);
}
