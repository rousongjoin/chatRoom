package test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/springmvc")
public class Springmvc_controller {
	@RequestMapping("/")
	public String test() {
		System.out.println("success");
		return "head";
	}

}
