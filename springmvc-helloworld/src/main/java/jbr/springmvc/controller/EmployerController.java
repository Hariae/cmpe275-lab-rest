package jbr.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jbr.springmvc.dao.EmployerDao;
//import jbr.springmvc.model.EmployeeEntity;
import jbr.springmvc.model.EmployerEntity;

@Controller
@RequestMapping("/employer")
public class EmployerController {
	@Autowired
	public EmployerDao empdao;
	
	@PersistenceContext
    private EntityManager manager;
	
	@RequestMapping(method=RequestMethod.GET) 
	  @ResponseBody
	  @Transactional
	public String getEmployer(@RequestParam (value="id", required=true) String employerId) throws JsonProcessingException {
		EmployerEntity employer = empdao.getEmployer(new Integer(employerId));
		System.out.println(employer.getName());
		//JsonObject result = Json.createObjectBuilder
		// Creating Object of ObjectMapper define in Jakson Api 
      ObjectMapper obj = new ObjectMapper(); 
      String result = obj.writeValueAsString(employer);
      
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	  @ResponseBody
	  @Transactional
	public String insertEmployer(@RequestParam (value="name", required=true) String name,
			@RequestParam (value="street", required=false) String street,
			@RequestParam (value="city", required=false) String city,
			@RequestParam (value="state", required=false) String state,
			@RequestParam (value="zip", required=false) String zip,
			@RequestParam (value="description", required=false) String description) {
		
		
		EmployerEntity employer = new EmployerEntity();
		System.out.println(name + "Name");
		employer.setName(name);
		employer.setAddress(street + "," + state + ", " + city + ", " + state + ", " + zip);
		employer.setDescription(description);
		empdao.addEmployer(employer);
		return "insert-success";
	}
	
}
