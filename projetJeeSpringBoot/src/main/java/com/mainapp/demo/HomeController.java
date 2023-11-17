package com.mainapp.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	//@ResponseBody
	@GetMapping("/welcome")
	public String welcome() {
		return "index";
	}
}
