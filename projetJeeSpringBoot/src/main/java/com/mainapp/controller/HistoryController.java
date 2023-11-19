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
//@Controller
//@RequestMapping("/History")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"basketList","test"})
//public class HistoryController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //BasketService
//    
//    @Autowired
//    /*public HistoryController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//		if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//        
//		User loginUser = IndexController.loginUser(model);
//        //Requete a ajouter dans le service
//		//basketList = nvRequete....
//		//model.addAttribute("basketList",basketList);
//        return "history";
//    }
//
//    @PostMapping
//    public String doPost( Model model) {
//        // Your logic for handling POST request
//        return doGet(model);
//    }
//
//    
//}
