package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.User;



import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/AddModerator")

//@SessionAttributes({"userList","test"})
@SessionAttributes("userList")
public class AddModeratorController {

    @GetMapping
    public String doGet(Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "index";
        }
    	
    	//Faire requete 
    	//userList = requete
    	//model.addAttribute("userList",userList)
        return "addModerator";
    }

    @PostMapping
    public String doPost( Model model) {
        // Your logic for handling POST request
        String[] transferList = (String[]) model.getAttribute("transferList");
        String[]userList = (String[]) model.getAttribute("userList");
        //userService = new UserService
        
        if (userList != null) {
    		for (String email: userList) {
            	boolean isChecked = false;
            	if (transferList != null) isChecked = Arrays.asList(transferList).contains(email);
        		//User user =  userService.getUser(email)
        		if (/*user.getTypeUser().equals("Moderator") &&*/ !isChecked) {
        			
        			//moderatorService = new moderatorService
        			//Moderator moderator = moderatorServoce.getModerator(email)
        			
        			if (/*!moderatorService.transferIntoCustomer(moderator)*/true) {
        				System.out.println("Msg erreur");
        				return "AddModerator";
        				//response.getWriter().println("<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        			}
        		}
        		else if (/*user.getTypeUser().equals("Customer") &&*/ isChecked) {
        			
        			//CustomerService customerService = new CustomerService
        			//Customer customer = customerService.getCustomer(email);
        			if (/*!customerService.transferIntoModerator(customer)*/true) {
        				System.out.println("Msg erreur");
        				return "AddModerator";

        				//response.getWriter().println("<script>showAlert('Transfer failed.', 'error', './AddModerator')</script>");
        			}
        		}
        	}
    		System.out.println("Msg validé");
    		return "manageModerator";
			//response.getWriter().println("<script>showAlert('Transfer completed.', 'success', './ManageModerator')</script>");
			
        }
		//return null;
		return "AddModerator";
        
        
    }

   
}
