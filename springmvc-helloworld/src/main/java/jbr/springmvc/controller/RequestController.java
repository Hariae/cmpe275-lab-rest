package jbr.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.model.EmployeeEntity;

@Controller
@RequestMapping("/request")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;
	
	@PersistenceContext
    private EntityManager manager;
	
	@RequestMapping(method=RequestMethod.GET) 
	  @ResponseBody
	  public String test() {
		//EmployeeDaoImpl obj = new EmployeeDaoImpl();
		//empdao.register(null);
		List<EmployeeEntity> employees = empdao.getAllEmployees();
		//System.out.println(employees);
		for(int i=0;i<employees.size();i++) {
			System.out.println(employees.get(i).getName());
		}
		return "test-success";
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	  @ResponseBody
	  @Transactional
	public String insertEmployee() {
		EmployeeEntity employee = new EmployeeEntity();
		employee.setName("Nishta");
		employee.setAddress("868, S 5th St");
		employee.setEmail("nishta@sjsu.edu");
		employee.setTitle("Developer");
		
		
		empdao.addEmployee(employee);
		System.out.println(manager + "manager");
		//manager.persist(employee);
		return "insert-success";
	}
	
}
