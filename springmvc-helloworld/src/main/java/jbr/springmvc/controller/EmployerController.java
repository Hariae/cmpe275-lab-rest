package jbr.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jbr.springmvc.Address;
import jbr.springmvc.Employer;
import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.dao.EmployerDao;
import jbr.springmvc.model.EmployeeEntity;
//import jbr.springmvc.model.EmployeeEntity;
import jbr.springmvc.model.EmployerEntity;

@Controller
@RequestMapping("/employer")
public class EmployerController {
	@Autowired
	public EmployerDao empdao;
	public EmployeeDao employeedao;

	@PersistenceContext
	private EntityManager manager;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public ResponseEntity<String> getEmployer(@PathVariable("id") String employerId) throws JsonProcessingException {
		EmployerEntity employer;
		/* Validation */
		try {
			employer = empdao.getEmployer(new Integer(employerId));
			if (employer == null) {
				throw new EmployerNotFoundException();
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		/* Validation */

		/* Address */
		Address address = new Address();

		String addressParts[] = employer.getAddress().split(",");
		address.setStreet(addressParts[0]);
		address.setCity(addressParts[1]);

		address.setState(addressParts[2]);
		address.setZip(addressParts[3]);
		/* Address */

		/* Employer Info */
		Employer res = new Employer();
		res.setId(employer.getEmployerId());
		res.setName(employer.getName());
		res.setAddress(address);
		res.setDescription(employer.getName());
		/* Employer Info */

		ObjectMapper obj = new ObjectMapper();
		String result = obj.writeValueAsString(res);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)

	@ResponseBody

	@Transactional
	public ResponseEntity<String> insertEmployer(@RequestParam(value = "name", required = true) String name,

			@RequestParam(value = "street", required = false) String street,

			@RequestParam(value = "city", required = false) String city,

			@RequestParam(value = "state", required = false) String state,

			@RequestParam(value = "zip", required = false) String zip,

			@RequestParam(value = "description", required = false) String description) throws Exception{
		
		/*Name validation*/
		
		List<EmployerEntity> employers = empdao.getAllEmployers();
		
		for(int i=0;i<employers.size();i++) {
			if(employers.get(i).getName().equals(name)) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		/*Name validation*/

		/* Employer Info */
		EmployerEntity employer = new EmployerEntity();

		employer.setName(name);
		/* Address */
		employer.setAddress((street != null ? street : null) + "," + (city != null ? city : null) + ", "
				+ (state != null ? state : null) + ", " + (zip != null ? zip : null));
		/* Address */
		employer.setDescription(description);
		empdao.addEmployer(employer);

		/* Employer Info */

		return getEmployer(String.valueOf(employer.getEmployerId()));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public ResponseEntity<String> updateEmployer(@PathVariable("id") String employerId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "description", required = false) String description)
			throws NumberFormatException, Exception {
		EmployerEntity employer;

		/* Validation */
		try {
			employer = empdao.getEmployer(new Integer(employerId));

			if (employer == null) {
				throw new EmployerNotFoundException();
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		/* Validation */

		/* Employer Info */
		employer.setName(name != null ? name : employer.getName());
		/* Address */
		Address address = new Address();

		String addressParts[] = employer.getAddress().split(",");
		address.setStreet(addressParts[0]);
		address.setCity(addressParts[1]);

		address.setState(addressParts[2]);
		address.setZip(addressParts[3]);
		address.setStreet(street != null ? street : addressParts[0]);
		address.setCity(city != null ? city : addressParts[1]);

		address.setState(state != null ? state : addressParts[2]);
		address.setZip(zip != null ? zip : addressParts[3]);

		/* Address */

		employer.setAddress(
				address.getStreet() + "," + address.getCity() + ", " + address.getState() + ", " + address.getZip());
		employer.setDescription(name != null ? description : employer.getDescription());
		/* Employer Info */

		empdao.updateEmployer(employer);

		/* Employer updated Info */
		Employer res = new Employer();
		res.setId(employer.getEmployerId());
		res.setName(employer.getName());
		res.setAddress(address);
		res.setDescription(employer.getName());
		/* Employer updated Info */

		ObjectMapper obj = new ObjectMapper();
		String result = obj.writeValueAsString(res);

		return new ResponseEntity<String>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)

	@ResponseBody

	@Transactional
	public ResponseEntity<String> deleteEmployer(@PathVariable("id") String employerId) throws JsonProcessingException {

		EmployerEntity employer;
		List<EmployeeEntity> list;

		/* Validation */
		try {
			employer = empdao.getEmployer(new Integer(employerId));

			if (employer == null) {
				throw new EmployerNotFoundException();
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		try {
			list = employeedao.getAllEmployees();
			for (EmployeeEntity obj : list) {
				if (String.valueOf(obj.getEmployer()) == employerId) {
					throw new EmployeeExistsException();
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		/* Validation */

		/* Is Exist */
		ResponseEntity<String> result = getEmployer(employerId);
		/* Is Exist */

		empdao.deleteEmployer(new Integer(employerId));
		System.out.println("deletion");
		return result;

	}

}
