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

import jbr.springmvc.dao.CollaborationDao;
import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.model.CollaborationEntity;
import jbr.springmvc.model.EmployeeEntity;

@Controller
@RequestMapping("/collaborators")
public class CollaborationContoller {
	
	@Autowired
	public CollaborationDao collabdao;
	
	@Autowired
	public EmployeeDao empdao;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<String> addCollaboration(@RequestParam(value = "id1", required = true) String collaborator_1, @RequestParam(value = "id2", required = true) String collaborator_2 ) throws NumberFormatException, Exception {
		
		
		/*Validation*/
		try {
			
		
		EmployeeEntity employee_1 = empdao.getEmployee(new Integer(collaborator_1));
		EmployeeEntity employee_2 = empdao.getEmployee(new Integer(collaborator_2));
		
		if(employee_1 == null || employee_2 == null) {
			throw new EmployeeNotFoundException();
		}
		}
		catch(EmployeeNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		/*Validation*/
		
		CollaborationEntity collaboration = new CollaborationEntity();
		collaboration.setCollab_1(new Integer(collaborator_1));
		collaboration.setCollab_2(new Integer(collaborator_2));
		
		/*Is Exist*/
		CollaborationEntity collab = collabdao.getCollaboration(collaboration);
		/*Is Exist*/
		
		if(collab == null) {
			Integer collborationId  = collabdao.addCollaboration(collaboration);
		}
		else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Add Collaboration Success", HttpStatus.OK);
	}
}
