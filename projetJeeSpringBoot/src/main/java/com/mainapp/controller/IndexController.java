package com.mainapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

//import com.mainapp.entity.Test;
import com.mainapp.entity.User;
//import com.mainapp.repository.TestRepository;
//import com.mainapp.service.TestService;

import java.io.IOException;

@Controller
@RequestMapping("/")
//@SessionAttributes("user")
//Si il y a plusieurs var en session
@SessionAttributes({"user","test"})
public class IndexController {
	
	//test de BDD dans Test
    
    
    
   

	@GetMapping("/")
    public String doGet( Model model) {
        model.addAttribute("test", "marche");
       
        /*Test test = ts.findById(1);
        System.out.println("AFFICHAGE OBJET TEST : "+test.getPrice());*/
        
        return "index";
    }

    @PostMapping
    public String doPost( Model model) {
        // Your logic for handling POST request
        model.addAttribute("test", "marche");

        return "index";
    }

    // This method is similar to your "loginUser" method
    public static User loginUser(Model model) {
        return (User) model.getAttribute("user");
    }

    // This method is similar to your "isLogged" method
   static boolean isLogged(Model model) {
        return loginUser(model) != null && loginUser(model).getId() != 0;
    }

    // You can use MultipartFile instead of Part for file uploading
    private String getSubmittedFileName(MultipartFile file) {
        // Your logic for getting submitted file name
        return null;
    }
}
