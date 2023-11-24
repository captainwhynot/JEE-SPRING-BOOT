package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.mainapp.entity.User;
import com.mainapp.service.ModeratorService;
import com.mainapp.service.UserService;

@Controller
@RequestMapping("/Login")
@SessionAttributes({"user", "moderatorService", "showAlert", "email", "password"})
public class LoginController {

	private UserService userService;
	private ModeratorService moderatorService;
	
	public LoginController(UserService us, ModeratorService ms) {
		this.userService = us;
		this.moderatorService = ms;
	}
    
	@GetMapping
    public String doGet( Model model) {
        return "login";
    }

    @PostMapping
    public String doPost(@RequestParam("email") String email,
    		@RequestParam("password") String password,
    		Model model) {
		//Check the login's information then log in
		if (userService.checkUserLogin(email, password)) {
			User user = userService.getUser(email);
			model.addAttribute("user", user);
			model.addAttribute("moderatorService", moderatorService);
			return "redirect:/Index";
		} else {
			model.addAttribute("showAlert", "<script>showAlert('Invalid identifiants.', 'error', '')</script>");
			return "login";
		}  
    }
}
