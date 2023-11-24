package com.mainapp.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.User;
import com.mainapp.service.UserService;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/Profil")
@SessionAttributes({"user", "showAlert", "moderatorService", "action", "profilInfo", "newValueInput", "passwordInput", "imgFile"})
public class ProfilController {

	private UserService userService;
	private ServletContext servletContext;
	
	public ProfilController(UserService us, ServletContext servletContext) {
		this.userService = us;
		this.servletContext = servletContext;
	}
    
	@GetMapping
    public String doGet(Model model) {
		if (!IndexController.isLogged(model)) {
			return "redirect:/Index";
		}
        return "profil";
    }

    @PostMapping
    public String doPost(@RequestParam("action") String action,
    		@RequestParam(value = "profilInfo", required = false) String profilInfo,
    		@RequestParam(value = "newValueInput", required = false) String newValue,
    		@RequestParam(value = "passwordInput", required = false) String password,
    		@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
    		Model model) {
    	if (!IndexController.isLogged(model)) {
			return "redirect:/Index";
		}
    	User loginUser = IndexController.loginUser(model);
		
   		if (action != null) {
   			if (action.equals("updateProfil")) {
   				//Verify the password
   				if (userService.checkUserLogin(loginUser.getEmail(), password) || profilInfo.equals("deleteProfilePicture") || profilInfo.equals("profilePicture")) {
   					//Update the information
   					boolean update = false;
			        String savePath = this.servletContext.getRealPath("/img/Profil");
	   				switch (profilInfo) {
	   					case "email":
	   						String newEmail = newValue;
	   						loginUser.setEmail(newEmail);
	   						update = userService.updateUser(loginUser, newEmail, loginUser.getUsername(), loginUser.getPassword());
	   						break;
	   					case "username":
	   						String newUsername = newValue;
	   						loginUser.setUsername(newUsername);
	   						update = userService.updateUser(loginUser, loginUser.getEmail(), newUsername, loginUser.getPassword());
	   						break;
	   					case "password":
	   						String newPassword = newValue;
	   						loginUser.setPassword(newPassword);
	   						update = userService.updateUser(loginUser, loginUser.getEmail(), loginUser.getUsername(), BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
	   						break;
	   					case "profilePicture":
	   				        String profilePicture = loginUser.getId() + "_" + imgFile.getOriginalFilename();
	   				        loginUser.setProfilePicture("img/Profil/" + profilePicture);
	   				        update = userService.updateProfilePicture(loginUser, imgFile, savePath);
	   						break;
	   					case "deleteProfilePicture":
	   				        loginUser.setProfilePicture("img/profilePicture.png");
	   				        update = userService.deleteProfilePicture(loginUser, savePath);
	   						break;
	   				}
	   				if (update) model.addAttribute("showAlert", "<script>showAlert('Profil updated.', 'success', './Profil');</script>");
	   				else model.addAttribute("showAlert", "<script>showAlert('Update failed.', 'error', './Profil');</script>");
   				} else {
   					model.addAttribute("showAlert", "<script>showAlert('The password is incorrect.', 'error', './Profil');</script>");
   				}
   			}
		}
		return "profil";
    }
}
