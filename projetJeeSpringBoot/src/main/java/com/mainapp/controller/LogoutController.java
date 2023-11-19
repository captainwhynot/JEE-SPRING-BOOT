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
//@RequestMapping("/Logout")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"user"})
//public class LogoutController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //UserService + constructeur
//    
//    @Autowired
//    /*public LogoutController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//        model.addAttribute("user", null);
//        
//        return "index";
//    }
//
//    @PostMapping
//    public String doPost( Model model) {
//
//        return doGet(model);
//        
//		    
//        
//    }
//
//   
//}
