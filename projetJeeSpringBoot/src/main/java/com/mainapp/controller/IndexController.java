package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.User;

@Controller
@RequestMapping("/Index")
@SessionAttributes({"user", "moderatorService"})
public class IndexController {
	
	@GetMapping
    public String doGet(Model model) {        
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
