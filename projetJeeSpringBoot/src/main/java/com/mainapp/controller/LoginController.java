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

/**
 * Controller class for handling user login.
 *
 * This controller handles both GET and POST requests related to user login.
 */
@Controller
@RequestMapping("/Login")
@SessionAttributes({"user", "moderatorService", "showAlert", "email", "password"})
public class LoginController {

	private UserService userService;
	private ModeratorService moderatorService;

    /**
     * Constructor for LoginController.
     *
     * @param us UserService instance for user-related operations.
     * @param ms ModeratorService instance for moderator-related operations.
     */
	public LoginController(UserService us, ModeratorService ms) {
		this.userService = us;
		this.moderatorService = ms;
	}

    /**
     * Handles GET requests for user login.
     * Displays the login form.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "login".
     */
	@GetMapping
    public String doGet( Model model) {
        return "login";
    }

    /**
     * Handles POST requests for user login.
     * Checks the login information and logs in the user if valid.
     *
     * @param email    The email parameter from the form.
     * @param password The password parameter from the form.
     * @param model    Model object for adding attributes used by the view.
     * @return Redirects to the "Index" view if login is successful; otherwise, redirects to the "login" view with an error message.
     */
    @PostMapping
    public String doPost(@RequestParam("email") String email,
			    		@RequestParam("password") String password,
			    		Model model) {
		// Check the login's information then log in
		if (userService.checkUserLogin(email, password)) {
			// If the login is successful, store the user in the session
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
