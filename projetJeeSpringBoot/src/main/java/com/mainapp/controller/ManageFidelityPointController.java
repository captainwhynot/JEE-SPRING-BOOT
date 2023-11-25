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

/**
 * Controller class for managing fidelity points of customers.
 *
 * This controller handles both GET and POST requests related to managing fidelity points.
 */
@Controller
@RequestMapping("/ManageFidelityPoint")
@SessionAttributes({"user", "showAlert", "customerList", "fidelityPointList"})
public class ManageFidelityPointController {

	private CustomerService customerService;

    /**
     * Constructor for ManageFidelityPointController.
     *
     * @param cs CustomerService instance for customer-related operations.
     */
	public ManageFidelityPointController(CustomerService cs) {
		this.customerService = cs;
	}

    /**
     * Handles GET requests for managing fidelity points.
     * Retrieves the list of customers and adds it to the model.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "manageFidelityPoint".
     */
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
   		// Get the customer list
		List<Customer> customerList = customerService.getCustomerList();
		model.addAttribute("customerList",customerList);
        return "manageFidelityPoint";
	}

    /**
     * Handles POST requests for managing fidelity points.
     * Processes the form submission to update fidelity points of selected customers.
     *
     * @param fidelityPointList Array of fidelity points corresponding to selected customers.
     * @param customerList      Array of email addresses for selected customers.
     * @param model             Model object for adding attributes used by the view.
     * @return The view "manageFidelityPoint".
     */ 
    @PostMapping
    public String doPost(@RequestParam("fidelityPointList") String[] fidelityPointList,
			    		@RequestParam("customerList") String[] customerList,
			    		Model model) {
		if (fidelityPointList != null && customerList != null) {
			Customer customer = null;
			String email = null;
			double fidelityPoint = 0;
			// Update the fidelity points of each customer
    		for (int i = 0; i < customerList.length; i++) {
    			email = customerList[i];
    			fidelityPoint = Double.parseDouble(fidelityPointList[i]);
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
