package jbr.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class HelloController {

	
	  @RequestMapping(method=RequestMethod.POST) 
	  @ResponseBody
	  public String hello() { 
		System.out.println("Hello");
		return "hello";
		
		}
		  //return new
	  //ModelAndView("welcome", "message", "Welcome to Spring MVC using Maven!!!"); }
	 
	/*
	 * @RequestMapping(method= RequestMethod.GET) public String test() { return
	 * "test"; }
	 */
}


