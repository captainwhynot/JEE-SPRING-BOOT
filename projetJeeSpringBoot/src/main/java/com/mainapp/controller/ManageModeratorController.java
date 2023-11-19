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
//
//
//import com.mainapp.entity.*;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@Controller
//@RequestMapping("/ManageModerator")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"moderatorList","fidelityPointList","addProductList","modifyProductList","deleteProductList"})
//public class ManageModeratorController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //Moderator
//    
//    @Autowired
//    /*public ManageModeratorController(TestService ts) {
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
//        //List<Customer> moderatorList = moderatorService.getModeratorList();
//		//model.addAttribute("moderatorList",moderatorList);
//        return "manageModerator";
//	}
//
//    @PostMapping
//    public String doPost( Model model) {
//        // Your logic for handling POST request
//        doGet(model);
//        
//        String[] addProductList = (String[]) model.getAttribute("addProductList");
//		String[] modifyProductList = (String[]) model.getAttribute("modifyProductList");
//		String[] deleteProductList = (String[]) model.getAttribute("deleteProductList");
//		String[] moderatorList = (String[]) model.getAttribute("moderatorList");
//       
//		if (moderatorList != null) {
//			//moderator service....
//			Moderator moderator = null;
//			boolean allUpdated = true;
//			
//    		for (String email: moderatorList) {
//    			//moderator = moderatorService.getModerator(email);
//    			
//    			if (addProductList != null && Arrays.asList(addProductList).contains(email)) {
//    				//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "addProduct", true);
//    			} else {
//    				//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "addProduct", false);
//    			}
//    			//modifyProduct's Right
//    			if (modifyProductList != null && Arrays.asList(modifyProductList).contains(email)) {
//    				//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "modifyProduct", true);
//    			} else {
//    				//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "modifyProduct", false);
//    			}
//    			//deleteProduct's Right
//    			if (deleteProductList != null && Arrays.asList(deleteProductList).contains(email)) {
//    				//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "deleteProduct", true);
//   				} else {
//   					//allUpdated = allUpdated && moderatorService.modifyRight(moderator, "deleteProduct", false);
//				}
//    		}
//    		
//    		if (allUpdated) {
//    			System.out.println("msg succes rights got updated");
//    	        return "manageModerator";
//
//    			//response.getWriter().println("<script>showAlert('All rights updated successfully.', 'success', './ManageModerator')</script>");
//			} else {
//    			System.out.println("msg error update fail");
//    	        return "manageModerator";
//
//				//response.getWriter().println("<script>showAlert('Update failed.', 'error', './ManageModerator')</script>");
//			}
//		}
//		
//		
//        return "manageModerator";
//    }
//
//    
//}
