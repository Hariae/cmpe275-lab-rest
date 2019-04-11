package edu.sjsu.cmpe275.lab2;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;;
@RestController
public class RequestController {
	
	@RequestMapping("/test")
	public String test() {
		return "test-success";
	}
}
