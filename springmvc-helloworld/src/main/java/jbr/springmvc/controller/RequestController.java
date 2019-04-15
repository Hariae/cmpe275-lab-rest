package jbr.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jbr.springmvc.Address;
import jbr.springmvc.CollaboratorResult;
import jbr.springmvc.Employee;
import jbr.springmvc.EmployeeResult;
import jbr.springmvc.Employer;
import jbr.springmvc.dao.CollaborationDao;
import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.dao.EmployerDao;
import jbr.springmvc.model.CollaborationEntity;
import jbr.springmvc.model.EmployeeEntity;
import jbr.springmvc.model.EmployerEntity;

@Controller
@RequestMapping("/employee")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;

	@Autowired
	public EmployerDao employerdao;

	@Autowired
	public CollaborationDao collabdao;

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
	public ResponseEntity<String> insertEmployee(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "managerId", required = false) String managerId,
			@RequestParam(value = "employerId", required = true) String employerId)

			throws Exception {

		/* Email -validation */
		List<EmployeeEntity> employees = empdao.getAllEmployees();

		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getEmail().equals(email)) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}

		/* Email -validation */

		EmployeeEntity employee = new EmployeeEntity();
		// String Name = name;
		System.out.println(name + "Name");
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		employee.setAddress(street + ", " + city + ", " + state + ", " + zip);
		employee.setEmployer(employerId != null ? new Integer(employerId) : null);
		employee.setManager(managerId != null ? new Integer(managerId) : null);

		/* Manager validation */
		if (managerId != null) {
			try {
				EmployeeEntity manager = empdao.getEmployee(new Integer(managerId));
				if (manager == null) {
					throw new ManagerNotFoundException();
				}
				// String result = getEmployee(managerId);
			} catch (ManagerNotFoundException e) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}

		/* Manager validation */

		if (employerId != null) {
			try {
				EmployerEntity employer = employerdao.getEmployer(new Integer(employerId));
				if (employer == null) {
					throw new EmployerNotFoundException();
				}
			} catch (EmployerNotFoundException e) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}

		}

		System.out.println("outside emp not found");
		Integer employeeId = empdao.addEmployee(employee);
		ResponseEntity<String> resultObj = getEmployee(employeeId.toString());

		return new ResponseEntity<String>(resultObj.getBody(), HttpStatus.OK);

		// return "insert-success";
	}

	/*
	 * @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 * 
	 * @ResponseBody
	 * 
	 * @Transactional public ResponseEntity<String>
	 * updateEmployee(@PathVariable("id") Integer employeeId,
	 * 
	 * @RequestParam(value = "name", required = false) String name,
	 * 
	 * @RequestParam(value = "email", required = true) String email,
	 * 
	 * @RequestParam(value = "title", required = false) String title,
	 * 
	 * @RequestParam(value = "employerId", required = false) Integer employer,
	 * 
	 * @RequestParam(value = "managerId", required = false) Integer newManager,
	 * 
	 * @RequestParam(value = "street", required = false) String street,
	 * 
	 * @RequestParam(value = "city", required = false) String city,
	 * 
	 * @RequestParam(value = "state", required = false) String state,
	 * 
	 * @RequestParam(value = "zip", required = false) String zipcode) throws
	 * Exception { try { if (empdao.getEmployee(employeeId) == null) {
	 * System.out.println("no such employee id"); return new
	 * ResponseEntity<String>(HttpStatus.BAD_REQUEST); } EmployeeEntity employee =
	 * empdao.getEmployee(employeeId);
	 * 
	 * EmployeeEntity newManagerEntity = empdao.getEmployee(newManager);
	 * ObjectMapper obj = new ObjectMapper();
	 * 
	 * if (employer == newManagerEntity.getEmployer()) {
	 * System.out.println("success"); if (name != null) employee.setName(name); if
	 * (title != null) employee.setTitle(title); // if(employer!=null)
	 * employee.setEmployer(employer); // if(newManager!=null)
	 * employee.setManager(newManager);
	 * 
	 * String newStreet = street != null ? street :
	 * employee.getAddress().split(",")[0]; String newCity = city != null ? city :
	 * employee.getAddress().split(",")[1]; String newState = state != null ? state
	 * : employee.getAddress().split(",")[2]; String newZipcode = zipcode != null ?
	 * zipcode : employee.getAddress().split(",")[3];
	 * 
	 * String newAddress = newStreet + "," + newCity + "," + newState + "," +
	 * newZipcode; System.out.println("newAddress" + newAddress);
	 * employee.setAddress(newAddress);
	 * 
	 * employee.setName(name); employee.setEmail(email); employee.setTitle(title);
	 * 
	 * Employer changes if (employer != null) employee.setEmployer(employer); //
	 * employee.setEmployer(employer);
	 * 
	 * List<EmployeeResult> reportees = empdao.getReportees(employeeId);
	 * 
	 * Integer managerId = null; if (employee.getManager() != null) { managerId =
	 * employee.getManager(); }
	 * 
	 * // edit reportees
	 * 
	 * for (int i = 0; i < reportees.size(); i++) { EmployeeEntity reportee =
	 * empdao.getEmployee(reportees.get(i).getId()); reportee.setManager(managerId);
	 * empdao.addEmployee(reportee); } if (newManager != null)
	 * employee.setManager(newManager); // employee.setManager(newManager); Employer
	 * changes
	 * 
	 * // employee.setAddress(address); empdao.addEmployee(employee);
	 * 
	 * ResponseEntity<String> resultObj = getEmployee(employeeId.toString());
	 * System.out.println("resultObj.getBody()" + resultObj.getBody()); return new
	 * ResponseEntity<String>(resultObj.getBody(), HttpStatus.ACCEPTED); } else {
	 * return new ResponseEntity<String>(HttpStatus.BAD_REQUEST); } } catch
	 * (JsonProcessingException e) { if (java.lang.Double.isNaN(employeeId)) {
	 * System.out.println("no id"); return new
	 * ResponseEntity<String>(HttpStatus.BAD_REQUEST); }
	 * 
	 * else return new ResponseEntity<String>("400", HttpStatus.BAD_REQUEST); } }
	 */
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT) 
	  @ResponseBody
	  @Transactional
	  public ResponseEntity<String> updateEmployee(
			  @PathVariable("id") Integer employeeId,
			  @RequestParam (value="name", required=false) String name,
			  @RequestParam (value="email", required=true) String email,
			  @RequestParam (value="title", required=false) String title,
			  @RequestParam (value="employerId", required=false) Integer employer,
			  @RequestParam (value="managerId", required=false) Integer newManager,
			  @RequestParam (value="street", required=false) String street,
			  @RequestParam (value="city", required=false) String city,
			  @RequestParam (value="state", required=false) String state,
			  @RequestParam (value="zip", required=false) String zipcode) throws Exception
			  {
		try {
			if(empdao.getEmployee(employeeId)==null) {
				System.out.println("no such employee id");
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		EmployeeEntity employee = empdao.getEmployee(employeeId);
						
		ObjectMapper obj = new ObjectMapper(); 
		
		boolean trueCondition = false;
		
		// manager != null; employer != null ; 
		
		//manager is present
		
		if(newManager != null) {
			EmployeeEntity checkManager = empdao.getEmployee(newManager);
			if(checkManager == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		
		if(employer != null) {
			EmployerEntity checkEmployer = employerdao.getEmployer(employer);
			if(checkEmployer == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		
		
		if(employer==null && newManager==null) {
			trueCondition = true;
		}
		if(employer!=null && newManager!=null) {
			EmployeeEntity newManagerEntity = empdao.getEmployee(newManager);
			if(employer==newManagerEntity.getEmployer()) trueCondition = true;
		}
		if(employer==null && newManager!=null) {
			EmployeeEntity newManagerEntity = empdao.getEmployee(newManager);
			if(employee.getEmployer()==newManagerEntity.getEmployer()) {
				trueCondition = true;
			}
		}
		if(employer!=null && newManager==null) {
			EmployeeEntity managerCompany = empdao.getEmployee(employee.getManager());
			if(employer==managerCompany.getEmployer()) {
				trueCondition = true;
			}
		}
		
		if(trueCondition) {
			System.out.println("success");
			if(name!=null) employee.setName(name);
			employee.setEmail(email);
			if(title!=null) employee.setTitle(title);
			//if(employer!=null) employee.setEmployer(employer);
			//if(newManager!=null) employee.setManager(newManager);
			
			String newStreet = street!=null ? street: employee.getAddress().split(",")[0];
			String newCity = city!=null ? city: employee.getAddress().split(",")[1]; 
			String newState = state!=null ? state: employee.getAddress().split(",")[2]; 
			String newZipcode = zipcode!=null ? zipcode: employee.getAddress().split(",")[3]; 
			
			String newAddress = newStreet+","+newCity+","+newState+","+newZipcode;
			System.out.println("newAddress"+newAddress);
			employee.setAddress(newAddress);
						
			
			
			/*Employer changes*/
			if(employer!=null) employee.setEmployer(employer);
			//employee.setEmployer(employer);
			
			
			List<EmployeeResult> reportees = empdao.getReportees(employeeId);
			
			Integer managerId = null;
			if(employee.getManager() != null) {
				managerId = employee.getManager();
			}
			
			//edit reportees
			
			for(int i=0;i<reportees.size();i++) {
				EmployeeEntity reportee = empdao.getEmployee(reportees.get(i).getId());
				reportee.setManager(managerId);
				empdao.addEmployee(reportee);
			}
			if(newManager!=null) employee.setManager(newManager);
			//employee.setManager(newManager);
			/*Employer changes*/
			
			//employee.setAddress(address);
			empdao.addEmployee(employee);
			
			ResponseEntity<String> resultObj = getEmployee(employeeId.toString());
				System.out.println("resultObj.getBody()"+resultObj.getBody());
				 return new ResponseEntity<String>(resultObj.getBody(), HttpStatus.ACCEPTED);
			}
		else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		}
		catch (JsonProcessingException e) {
			if(java.lang.Double.isNaN(employeeId)) {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			 
			else
			return new ResponseEntity<String>("400", HttpStatus.BAD_REQUEST);
		}
	}



	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public ResponseEntity<String> getEmployee(@PathVariable(value = "id") String employeeId) throws Exception {

		String result = "";
		EmployeeEntity employee = null;
		try {
			employee = empdao.getEmployee(new Integer(employeeId));
			System.out.println(employee);
			if (employee == null) {
				throw new EmployeeNotFoundException();
			}
		} catch (EmployeeNotFoundException e) {
			System.out.println("Inside catch");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

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

		/* Employer address */
		Address employerAddress = new Address();

		String employerAddressParts[] = employerEntity.getAddress().split(",");
		employerAddress.setStreet(employerAddressParts[0]);
		employerAddress.setCity(employerAddressParts[1]);
		employerAddress.setState(employerAddressParts[2]);
		employerAddress.setZip(employerAddressParts[3]);
		employer.setAddress(employerAddress);
		/* Employer address */
		employeeResult.setEmployer(employer);
		/* Employer Info */

		/* Manager Info */
		System.out.println("Manager ID" + employee.getManager());
		if (employee.getManager() != null) {
			EmployeeEntity managerEntity = empdao.getEmployee(employee.getManager());

			EmployeeResult manager = new EmployeeResult();
			manager.setId(employee.getManager());
			manager.setName(managerEntity.getName());
			manager.setTitle(managerEntity.getTitle());

			employeeResult.setManager(manager);

		}
		/* Manager Info */

		/* reportees info */
		employeeResult.setReports(empdao.getReportees(new Integer(employeeId)));
		/* reportees info */

		/* Collaboration info */
		List<Integer> collaborators = collabdao.getCollaborators(new Integer(employeeId));

		if (collaborators.size() > 0) {
			List<CollaboratorResult> employeeCollaborators = new ArrayList<CollaboratorResult>();

			for (int i = 0; i < collaborators.size(); i++) {

				EmployeeEntity collaboratorEntity = empdao.getEmployee(collaborators.get(i));

				CollaboratorResult collaborator = new CollaboratorResult();
				collaborator.setId(collaborators.get(i));
				collaborator.setName(collaboratorEntity.getName());
				collaborator.setTitle(collaboratorEntity.getTitle());

				/* Collaborator Employer */
				EmployerEntity collaboratorEmployerEntity = new EmployerEntity();
				collaboratorEmployerEntity = employerdao.getEmployer(collaboratorEntity.getEmployer());

				Employer collaboratorEmployer = new Employer();
				collaboratorEmployer.setId(collaboratorEntity.getEmployer());
				collaboratorEmployer.setName(collaboratorEmployerEntity.getName());
				collaboratorEmployer.setDescription(collaboratorEmployerEntity.getDescription());

				/* Collaborator Employer address */
				Address collaboratorEmployerAddress = new Address();

				String colaboratorEmployerAddressParts[] = collaboratorEmployerEntity.getAddress().split(",");
				collaboratorEmployerAddress.setStreet(colaboratorEmployerAddressParts[0]);
				collaboratorEmployerAddress.setCity(colaboratorEmployerAddressParts[1]);
				collaboratorEmployerAddress.setState(colaboratorEmployerAddressParts[2]);
				collaboratorEmployerAddress.setZip(colaboratorEmployerAddressParts[3]);
				collaboratorEmployer.setAddress(collaboratorEmployerAddress);
				/* Collaborator Employer address */

				collaborator.setEmployer(collaboratorEmployer);
				/* Collaborator Employer */

				employeeCollaborators.add(collaborator);
			}

			employeeResult.setCollaborators(employeeCollaborators);
		}
		/* Collaboration info */

		ObjectMapper obj = new ObjectMapper();
		result = obj.writeValueAsString(employeeResult);

		return new ResponseEntity<String>(result, HttpStatus.OK);
		// return result;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional
	public ResponseEntity<String> removeEmployee(@PathVariable(value = "id") String employeeId) throws Exception {

		/* Employee Not found */
		try {
			EmployeeEntity employee = empdao.getEmployee(new Integer(employeeId));
			if (employee == null) {
				throw new EmployeeNotFoundException();
			}
		} catch (EmployeeNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		/* Employee Not found */

		/* Reportees Found */
		try {
			List<EmployeeResult> reportees = empdao.getReportees(new Integer(employeeId));
			if (reportees.size() > 0) {
				throw new ReporteesFoundException();
			}
		} catch (ReporteesFoundException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		/* Reportees Found */

		ResponseEntity<String> resultObj = getEmployee(employeeId);
		String result = resultObj.getBody();

		/* Remove collaborations */
		List<CollaborationEntity> employeeCollaborations = collabdao.getEmployeeCollaborations(new Integer(employeeId));

		for (int i = 0; i < employeeCollaborations.size(); i++) {
			collabdao.removeCollaboration(employeeCollaborations.get(i));
		}
		/* Remove collaborations */

		empdao.removeEmployee(new Integer(employeeId));

		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

}
