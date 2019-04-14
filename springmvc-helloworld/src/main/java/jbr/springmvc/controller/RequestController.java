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

import jbr.springmvc.Address;
import jbr.springmvc.Employee;
import jbr.springmvc.EmployeeResult;
import jbr.springmvc.Employer;
import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.dao.EmployerDao;
import jbr.springmvc.model.EmployeeEntity;
import jbr.springmvc.model.EmployerEntity;

@Controller
@RequestMapping("/employee")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;

	@Autowired
	public EmployerDao employerdao;

	@PersistenceContext
	private EntityManager manager;
	/*
	 * public String test() { //EmployeeDaoImpl obj = new EmployeeDaoImpl();
	 * //empdao.register(null); List<EmployeeEntity> employees =
	 * empdao.getAllEmployees(); //System.out.println(employees); for(int
	 * i=0;i<employees.size();i++) { System.out.println(employees.get(i).getName());
	 * } return "test-success"; }
	 */

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String insertEmployee(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "managerId", required = false) String managerId,
			@RequestParam(value = "employerId", required = false) String employerId) {

		EmployeeEntity employee = new EmployeeEntity();
		// String Name = name;
		System.out.println(name + "Name");
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		employee.setAddress(street + ", " + city + ", " + state + ", " + zip);
		employee.setEmployer(employerId != null ? new Integer(employerId) : null);
		employee.setManager(managerId != null ? new Integer(managerId) : null);

		empdao.addEmployee(employee);
		return "insert-success";
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getEmployee(@RequestParam(value = "id", required = true) String employeeId)
			throws JsonProcessingException {

		EmployeeEntity employee = empdao.getEmployee(new Integer(employeeId));

		Employee employeeResult = new Employee();
		employeeResult.setId(employee.getEmployeeId());
		employeeResult.setName(employee.getName());
		employeeResult.setEmail(employee.getEmail());
		employeeResult.setTitle(employee.getTitle());

		/* Address */
		Address address = new Address();

		String addressParts[] = employee.getAddress().split(",");
		address.setStreet(addressParts[0]);
		address.setCity(addressParts[1]);

		address.setState(addressParts[2]);
		address.setZip(addressParts[3]);

		employeeResult.setAddress(address);
		/* Address */

		/* Employer Info */
		EmployerEntity employerEntity = new EmployerEntity();
		employerEntity = employerdao.getEmployer(employee.getEmployer());

		Employer employer = new Employer();
		employer.setId(employee.getEmployer());
		employer.setName(employerEntity.getName());
		employer.setDescription(employerEntity.getDescription());

		employeeResult.setEmployer(employer);
		/* Employer Info */
		
		/*Manager Info*/
		System.out.println("Manager ID" + employee.getManager());
		if(employee.getManager() != null) {
			EmployeeEntity managerEntity = empdao.getEmployee(employee.getManager());
			
			EmployeeResult manager = new EmployeeResult();
			manager.setId(employee.getManager());
			manager.setName(managerEntity.getName());
			manager.setTitle(managerEntity.getTitle());
			
			employeeResult.setManager(manager);
				
		}
		/*Manager Info*/
		
		/*reportees info*/
		
		employeeResult.setReports(empdao.getReportees(new Integer(employeeId)));
		
		/*reportees info*/

		ObjectMapper obj = new ObjectMapper();
		String result = obj.writeValueAsString(employeeResult);

		return result;
	}

}
