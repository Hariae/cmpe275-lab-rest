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
import jbr.springmvc.Employer;
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
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET) 
	  @ResponseBody
	  @Transactional
	public String getEmployer(@PathVariable("id") String employerId) throws JsonProcessingException {
		EmployerEntity employer = empdao.getEmployer(new Integer(employerId));
		//System.out.println(employer.getName());
		//JsonObject result = Json.createObjectBuilder
		// Creating Object of ObjectMapper define in Jakson Api 
		if(employer!=null) {
		Address address = new Address();

		String addressParts[] = employer.getAddress().split(",");
		address.setStreet(addressParts[0]);
		address.setCity(addressParts[1]);

		address.setState(addressParts[2]);
		address.setZip(addressParts[3]);
		Employer res = new Employer();
		res.setId(employer.getEmployerId());
		res.setName(employer.getName());
		res.setAddress(address);
		res.setDescription(employer.getName());			
		ObjectMapper obj = new ObjectMapper(); 
	      String result = obj.writeValueAsString(res);
	      return result;
	}
		return "unsuccessful";
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	  @ResponseBody
	  @Transactional
	public String insertEmployer(@RequestParam (value="name", required=true) String name,
			@RequestParam (value="street", required=false) String street,
			@RequestParam (value="city", required=false) String city,
			@RequestParam (value="state", required=false) String state,
			@RequestParam (value="zip", required=false) String zip,
			@RequestParam (value="description", required=false) String description) throws JsonProcessingException {
		
		
		EmployerEntity employer = new EmployerEntity();
		System.out.println(name + "Name");
		employer.setName(name);
		employer.setAddress((street != null? street : null) + "," + (city != null? city : null) + ", " + (state != null? state : null) + ", " + (zip != null? zip : null) );
		
		employer.setDescription(description);
		empdao.addEmployer(employer);
		return getEmployer(String.valueOf(employer.getEmployerId()));
	}
	
	@RequestMapping(value = "/{id}",method=RequestMethod.PUT) 
	  @ResponseBody
	  @Transactional
	public String updateEmployer(@PathVariable("id") String employerId,
			@RequestParam (value="name", required=true) String name,
			@RequestParam (value="street", required=false) String street,
			@RequestParam (value="city", required=false) String city,
			@RequestParam (value="state", required=false) String state,
			@RequestParam (value="zip", required=false) String zip,
			@RequestParam (value="description", required=false) String description) throws JsonProcessingException {
		EmployerEntity employer = empdao.getEmployer(new Integer(employerId));
        
		if(employer!=null) {
			employer.setName(name != null? name: employer.getName());
			Address address = new Address();

			String addressParts[] = employer.getAddress().split(",");
			address.setStreet(addressParts[0]);
			address.setCity(addressParts[1]);

			address.setState(addressParts[2]);
			address.setZip(addressParts[3]);
			address.setStreet(street != null? street :addressParts[0]);
			address.setCity(city != null? city :addressParts[1]);

			address.setState(state != null? state :addressParts[2]);
			address.setZip(zip != null? zip :addressParts[3]);
			employer.setAddress(address.getStreet() + "," + address.getCity() + ", " + address.getState() + ", " + address.getZip());
			employer.setDescription(name != null? description : employer.getDescription());
			empdao.updateEmployer(employer);
			Employer res = new Employer();
			res.setId(employer.getEmployerId());
			res.setName(employer.getName());
			res.setAddress(address);
			res.setDescription(employer.getName());			
			ObjectMapper obj = new ObjectMapper(); 
		      String result = obj.writeValueAsString(res);
		      
				return result;
			
		}
		
		else { 
  
		return "unsuccesful";}
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE) 
	  @ResponseBody
	  @Transactional
	public String deleteEmployer(@PathVariable("id") String employerId) throws JsonProcessingException {
		
		String result= getEmployer(employerId);
		empdao.deleteEmployer(new Integer(employerId));
		System.out.println("deletion");
  
		return result;
	}
	
}
