package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.User;
import com.mainapp.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes({"user","test"})
public class IndexController {
	
	private UserService us;
	
	public IndexController(UserService us) {
		this.us = us;
	}
	
	@GetMapping
    public String doGet(Model model) {
		User user = us.getUser("mailAdmin");
        model.addAttribute("user", user);
        model.addAttribute("test", "a");
        
        return "index";
    }

    @PostMapping
    public String doPost(Model model) {
		return doGet(model);
    }

    public static User loginUser(Model model) {
        return (User) model.getAttribute("user");
    }

    public static boolean isLogged(Model model) {
        return loginUser(model) != null && loginUser(model).getId() != 0;
    }
}
