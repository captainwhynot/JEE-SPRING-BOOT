package com.mainapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Customer;
import com.mainapp.service.CustomerService;

@Controller
@RequestMapping("/ManageFidelityPoint")
@SessionAttributes({"user", "showAlert", "customerList", "fidelityPointList"})
public class ManageFidelityPointController {

	private CustomerService customerService;
	
	public ManageFidelityPointController(CustomerService cs) {
		this.customerService = cs;
	}
    
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
        List<Customer> customerList = customerService.getCustomerList();
		model.addAttribute("customerList",customerList);
        return "manageFidelityPoint";
	}

    @PostMapping
    public String doPost(@RequestParam("fidelityPointList") String[] fidelityPointList,
    		@RequestParam("customerList") String[] customerList,
    		Model model) {
		if (fidelityPointList != null && customerList != null) {
			Customer customer = null;
			String email = null;
			int fidelityPoint = 0;
			//Update the fidelity points of each customer
    		for (int i = 0; i < customerList.length; i++) {
    			email = customerList[i];
    			fidelityPoint = Integer.parseInt(fidelityPointList[i]);
    			customer = customerService.getCustomer(email);
    			if (!customerService.setFidelityPoint(customer, fidelityPoint - customer.getFidelityPoint())) {
    				model.addAttribute("showAlert", "<script>showAlert('Update failed.', 'error', './ManageFidelityPoint')</script>");
    			}
    		}
			model.addAttribute("showAlert", "<script>showAlert('Update completed.', 'success', './ManageFidelityPoint')</script>");
		}
        return "manageFidelityPoint";
    }
}
