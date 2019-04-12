package jbr.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/request")
public class RequestController {
	
	@RequestMapping(method=RequestMethod.GET) 
	  @ResponseBody
	  public String test() {
		return "test-success";
	}
}
