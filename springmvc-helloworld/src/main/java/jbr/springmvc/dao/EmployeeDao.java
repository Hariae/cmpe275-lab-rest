package jbr.springmvc.dao;
import jbr.springmvc.Employee;
import jbr.springmvc.model.EmployeeEntity;

import java.util.*;

public interface EmployeeDao {
	//void register(Employee employee);
	
	public List<EmployeeEntity> getAllEmployees();
}
