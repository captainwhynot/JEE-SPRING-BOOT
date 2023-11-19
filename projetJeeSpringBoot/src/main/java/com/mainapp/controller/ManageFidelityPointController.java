//package com.mainapp.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
////import com.mainapp.entity.Test;
//import com.mainapp.entity.User;
////import com.mainapp.repository.TestRepository;
////import com.mainapp.service.TestService;
//
//import com.mainapp.entity.*;
//
//import java.io.IOException;
//
//@Controller
//@RequestMapping("/ManageFidelityPoint")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"customerList","fidelityPointList","customerList"})
//public class ManageFidelityPointController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //Customer
//    
//    @Autowired
//    /*public ManageFidelityPointController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//		if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//        
//		
//        //List<Customer> customerList = customerService.getCustomerList();
//		//model.addAttribute("customerList",customerList);
//        return "manageFidelityPoint";
//	}
//
//    @PostMapping
//    public String doPost( Model model) {
//        // Your logic for handling POST request
//        doGet(model);
//        
//        String[] fidelityPointList = (String[]) model.getAttribute("fidelityPointList");
//		String[] customerList = (String[]) model.getAttribute("customerList");
//        //customer service...
//		if (fidelityPointList != null && customerList != null) {
//			Customer customer = null;
//			String email = null;
//			int fidelityPoint = 0;
//			//Update the fidelity points of each customer
//    		for (int i = 0; i < customerList.length; i++) {
//    			email = customerList[i];
//    			fidelityPoint = Integer.parseInt(fidelityPointList[i]);
//    			//customer service...
//    			if (/*!customerService.setFidelityPoint(customer, fidelityPoint - customer.getFidelityPoint())*/true) {
//    				//response.getWriter().println("<script>showAlert('Update failed.', 'error', './ManageFidelityPoint')</script>");
//    				System.out.println("msg erreur update fail");
//    				return "manageFidelityPoint";
//    			}
//    		}
//    		//response.getWriter().println("<script>showAlert('Update completed.', 'success', './ManageFidelityPoint')</script>");
//			System.out.println("msg validation update réussie");
//			return "manageFidelityPoint";
//
//		}
//		
//        return "manageFidelityPoint";
//    }
//
//    
//}
