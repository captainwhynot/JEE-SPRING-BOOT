package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;
import com.mainapp.entity.User;
import com.mainapp.service.CustomerService;
import com.mainapp.service.ModeratorService;
import com.mainapp.service.UserService;

import jakarta.servlet.ServletContext;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for managing the addition and transfer of moderators.
 *
 * This controller handles both GET and POST requests related to adding and transferring moderators. It uses the UserService, ModeratorService, and CustomerService to interact with the database and perform the necessary operations. The corresponding view is "addModerator.html".
 **/
@Controller
@RequestMapping("/AddModerator")
@SessionAttributes({"user", "moderatorService", "showAlert", "userList", "transferList"})
public class AddModeratorController {

	private UserService userService;
	private ModeratorService moderatorService;
	private CustomerService customerService;
	private ServletContext servletContext;

    /**
     * Constructor for the AddModeratorController.
     *
     * @param us               The UserService for managing users.
     * @param ms               The ModeratorService for managing moderators.
     * @param cs               The CustomerService for managing customers.
     * @param servletContext   The ServletContext for accessing the application's context.
     */
	public AddModeratorController(UserService us, ModeratorService ms, CustomerService cs, ServletContext servletContext) {
		this.userService = us;
		this.moderatorService = ms;
		this.customerService = cs;
		this.servletContext = servletContext;
	}

    /**
     * Handles the HTTP GET request for the "/AddModerator" endpoint.
     *
     * @param model The Model object to which attributes are added.
     * @return The logical view name ("addModerator") to render the response.
     */
    @GetMapping
    public String doGet(Model model) {
    	if(!IndexController.isLogged(model)) {
    		return "redirect:/Index";
        }

        // Get the list of users excluding administrators
    	List<User> userList = userService.getAllNoAdministrator();
    	model.addAttribute("userList", userList);
    	model.addAttribute("moderatorService", moderatorService);
        return "addModerator";
    }

    /**
     * Handles the HTTP POST request for the "/AddModerator" endpoint.
     *
     * @param transferList The array of email addresses representing users selected for transfer.
     * @param userList     The array of email addresses representing all users.
     * @param model        The Model object to which attributes are added.
     * @return The logical view name ("addModerator") to render the response.
     */
    @PostMapping
    public String doPost(@RequestParam(value = "transferList", required = false) String[] transferList,
			    		@RequestParam("userList") String[] userList,
			    		Model model) {        
        if (userList != null) {
    		for (String email: userList) {
            	boolean isChecked = false;
            	if (transferList != null) isChecked = Arrays.asList(transferList).contains(email);
        		User user =  userService.getUser(email);
        		if (user.getTypeUser().equals("Moderator") && !isChecked) {
        			// Transfer the moderator into a customer
          	        String savePath = this.servletContext.getRealPath("/img/Product");
        			Moderator moderator = moderatorService.getModerator(email);
        			if (!moderatorService.transferIntoCustomer(moderator, savePath)) {
        				model.addAttribute("showAlert", "<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        				return doGet(model);
        			}
        		}
        		else if (user.getTypeUser().equals("Customer") && isChecked) {
        			// Transfer the customer into a moderator
        			Customer customer = customerService.getCustomer(email);
        			if (!customerService.transferIntoModerator(customer)) {
        				model.addAttribute("showAlert", "<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        				return doGet(model);
        			}
        		}
        	}
			model.addAttribute("showAlert", "<script>showAlert('Transfer completed.', 'success', './ManageModerator')</script>");
        }
		return doGet(model);
    }
}
