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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/AddModerator")
@SessionAttributes({"user", "moderatorService", "showAlert", "userList", "transferList"})
public class AddModeratorController {

	private UserService userService;
	private ModeratorService moderatorService;
	private CustomerService customerService;
	
	public AddModeratorController(UserService us, ModeratorService ms, CustomerService cs) {
		this.userService = us;
		this.moderatorService = ms;
		this.customerService = cs;
	}
	
    @GetMapping
    public String doGet(Model model) {
    	if(!IndexController.isLogged(model)) {
    		return "redirect:/Index";
        }
    	
    	List<User> userList = userService.getAllNoAdministrator();
    	model.addAttribute("userList", userList);
    	model.addAttribute("moderatorService", moderatorService);
        return "addModerator";
    }

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
        			//Transfer the moderator into a customer
        			Moderator moderator = moderatorService.getModerator(email);
        			if (!moderatorService.transferIntoCustomer(moderator)) {
        				model.addAttribute("showAlert", "<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        				return doGet(model);
        			}
        		}
        		else if (user.getTypeUser().equals("Customer") && isChecked) {
        			//Transfer the customer into a moderator
        			Customer customer = customerService.getCustomer(email);
        			if (!customerService.transferIntoModerator(customer)) {
        				model.addAttribute("showAlert", "<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        				return doGet(model);
        			}
        		}
        	}
			model.addAttribute("showAlert", "<script>showAlert('Transfer completed.', 'success', './AddModerator')</script>");
			//model.addAttribute("showAlert", "<script>showAlert('Transfer completed.', 'success', './ManageModerator')</script>");
        }
		return doGet(model);
    }
}
