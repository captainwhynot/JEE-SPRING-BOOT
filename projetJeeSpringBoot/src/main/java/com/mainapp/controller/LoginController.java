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
//import java.io.IOException;
//
//
//@Controller
//@RequestMapping("/Login")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"email","password","user"})
//public class LoginController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //UserService + constructeur
//    
//    @Autowired
//    /*public LoginController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//        
//        return "login";
//    }
//
//    @PostMapping
//    public String doPost( Model model) {
//
//        doGet(model);
//        String email = (String) model.getAttribute("email");
//		String password = (String) model.getAttribute("password");
//		
//		//new Userservice...
//		if (/*userService.checkUserLogin(email, password)*/true) {
//			//User user = userService.getUser(email);
//			
//			//model.addAttribute("user", user);
//
//			return "index";
//		} else {
//			System.out.println("msg erreur identifiants incorrects");
//			return "login";
//		}
//			
//		
//        
//        
//    }
//
//   
//}
