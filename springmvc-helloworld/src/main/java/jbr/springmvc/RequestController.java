package jbr.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.dao.EmployeeEntity;

@Controller
@RequestMapping("/request")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;
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
}
