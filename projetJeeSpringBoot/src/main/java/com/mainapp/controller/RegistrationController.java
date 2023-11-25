package com.mainapp.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Customer;
import com.mainapp.service.UserService;

/**
 * Controller class for managing user registration and account creation.
 *
 * This controller handles both GET and POST requests related to user registration.
 */
@Controller
@RequestMapping("/Registration")
@SessionAttributes({"user", "moderatorService", "showAlert", "email", "password", "username"})
public class RegistrationController {

	private UserService userService;

    /**
     * Constructor for RegistrationController.
     *
     * @param us UserService instance for user-related operations.
     */
	public RegistrationController(UserService us) {
		this.userService = us;
	}

    /**
     * Handles GET requests for user registration.
     * Displays the registration form.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "registration".
     */
	@GetMapping
    public String doGet(Model model) {
        return "registration";
    }

    /**
     * Handles POST requests for user registration.
     * Processes the form submission and creates a new user account.
     *
     * @param email    The email parameter from the form.
     * @param password The password parameter from the form.
     * @param username The username parameter from the form.
     * @param model    Model object for adding attributes used by the view.
     * @return The view "registration".
     */
    @PostMapping
    public String doPost(@RequestParam("email") String email,
			    		@RequestParam("password") String password,
			    		@RequestParam("username") String username,
			    		Model model) {
    	// Create a new Customer user with hashed password
    	Customer user = new Customer(email, BCrypt.hashpw(password, BCrypt.gensalt(12)), username);		
		if(userService.saveUser(user)) {
			// Store the user in the session
			model.addAttribute("user", user);
			
			// Mail's content
			String container = "Your account has been created successfully.<br>";
			container += "<div style='color: black'>"+
					      "Your identifiants are : <br>";
			container += "<ul>";
			container += "<li>e-mail : " + email + "<br></li>";
			container += "<li>password : " + password + "<br></li>";
			container += "<li>username : " + username + "<br></li>";
			container += "</ul>";
			container += "Go to the site : ";
			container += "<a href=\"http://localhost:8080/Index\">MANGASTORE</a>" +
					     "</div>";
			// Send registration confirmation mail
			if (userService.sendMail(email, "MANGASTORE : Registration", container)) {
				model.addAttribute("showAlert", "<script>showAlert('Your account has been successfully created.', 'success', './Index')</script>");
			} else {
				model.addAttribute("showAlert", "<script>showAlert('Confirmation mail didn\\'t send well.', 'warning', './Index');</script>");
			}
		} else {
			model.addAttribute("showAlert", "<script>showAlert('This e-mail is already taken.', 'error', '');</script>");
		}
		return "registration";
    }
}
