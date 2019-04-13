package jbr.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jbr.springmvc.dao.EmployeeDao;
import jbr.springmvc.dao.EmployeeDaoImpl;

@Controller
@RequestMapping("/request")
public class RequestController {
	@Autowired
	public EmployeeDao empdao;
	@RequestMapping(method=RequestMethod.GET) 
	  @ResponseBody
	  public String test() {
		//EmployeeDaoImpl obj = new EmployeeDaoImpl();
		empdao.register(null);
		return "test-success";
	}
}
