package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.User;

/**
 * Controller class for handling the index page.
 *
 * This controller handles both GET and POST requests related to the index page.
 */
@Controller
@RequestMapping("/Index")
@SessionAttributes({"user", "moderatorService"})
public class IndexController {

    /**
     * Handles GET requests for the index page.
     * Displays the index page.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "index".
     */
	@GetMapping
    public String doGet(Model model) {        
        return "index";
    }

    /**
     * Handles POST requests for the index page.
     * Redirects to the GET method to display the index page.
     *
     * @param model Model object for adding attributes used by the view.
     * @return Redirects to the "Index" view.
     */
    @PostMapping
    public String doPost(Model model) {
		return doGet(model);
    }

    /**
     * Retrieves the logged-in user from the session.
     *
     * @param model Model object containing session attributes.
     * @return The User object representing the logged-in user.
     */
    public static User loginUser(Model model) {
        return (User) model.getAttribute("user");
    }

    /**
     * Checks if a user is logged in.
     *
     * @param model Model object containing session attributes.
     * @return True if a user is logged in, false otherwise.
     */
    public static boolean isLogged(Model model) {
        return loginUser(model) != null && loginUser(model).getId() != 0;
    }
}
