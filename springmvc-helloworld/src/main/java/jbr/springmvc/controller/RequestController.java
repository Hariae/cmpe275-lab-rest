package jbr.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.model.EmployeeEntity;

 

@Controller
@RequestMapping("/employee")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;
	
	@PersistenceContext
    private EntityManager manager;
	/*
	 * public String test() { //EmployeeDaoImpl obj = new EmployeeDaoImpl();
	 * //empdao.register(null); List<EmployeeEntity> employees =
	 * empdao.getAllEmployees(); //System.out.println(employees); for(int
	 * i=0;i<employees.size();i++) { System.out.println(employees.get(i).getName());
	 * } return "test-success"; }
	 */
	
	@RequestMapping(method=RequestMethod.POST) 
	  @ResponseBody
	  @Transactional
	public String insertEmployee(@RequestParam (value="name", required=true) String name,
			@RequestParam (value="email", required=true) String email,
			@RequestParam (value="title", required=false) String title,
			@RequestParam (value="street", required=false) String street,
			@RequestParam (value="city", required=false) String city,
			@RequestParam (value="state", required=false) String state,
			@RequestParam (value="zip", required=false) String zip,
			@RequestParam (value="managerId", required=false) String managerId,
			@RequestParam (value="employerId", required=false) String employerId) {
		
		
		EmployeeEntity employee = new EmployeeEntity();
		//String Name = name;
		System.out.println(name + "Name");
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		employee.setAddress(street + "," + state + ", " + city + ", " + state + ", " + zip);
		employee.setEmployer(employerId != null? new Integer(employerId): null);
		employee.setManager(managerId != null ? new Integer(managerId): null);
		
		
		empdao.addEmployee(employee);
		return "insert-success";
	}
	
	@RequestMapping(method=RequestMethod.GET) 
	  @ResponseBody
	  @Transactional
	public String getEmployee(@RequestParam (value="id", required=true) String employeeId) throws JsonProcessingException {
		EmployeeEntity employee = empdao.getEmployee(new Integer(employeeId));
		System.out.println(employee.getEmail());
		//JsonObject result = Json.createObjectBuilder
		// Creating Object of ObjectMapper define in Jakson Api 
        ObjectMapper obj = new ObjectMapper(); 
        String result = obj.writeValueAsString(employee);
        
		return result;
	}
	
}
