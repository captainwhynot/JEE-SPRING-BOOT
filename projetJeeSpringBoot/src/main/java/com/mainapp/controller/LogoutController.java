package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Controller class for handling user logout.
 *
 * This controller handles both GET and POST requests related to user logout.
 */
@Controller
@RequestMapping("/Logout")
@SessionAttributes({"user"})
public class LogoutController {

    /**
     * Handles GET requests for user logout.
     * Clears the user attribute from the session and completes the session status.
     *
     * @param model         Model object for adding attributes used by the view.
     * @param sessionStatus SessionStatus object for managing the status of the session.
     * @return Redirects to the "Index" view.
     */
	@GetMapping
    public String doGet(Model model, SessionStatus sessionStatus) {
		// Get the session and remove the user attribute to log them out
		model.addAttribute("user", null);
        sessionStatus.setComplete();
        return "redirect:/Index";
    }

    /**
     * Handles POST requests for user logout.
     * Calls the doGet method for handling the logout operation.
     *
     * @param model         Model object for adding attributes used by the view.
     * @param sessionStatus SessionStatus object for managing the status of the session.
     * @return Redirects to the "Index" view.
     */
    @PostMapping
    public String doPost(Model model, SessionStatus sessionStatus) {
        return doGet(model, sessionStatus);
    }
}
