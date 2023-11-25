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
import com.mainapp.service.CustomerService;
import com.mainapp.service.UserService;

import jakarta.servlet.ServletContext;

/**
 * Controller class for managing user profile updates.
 *
 * This controller handles both GET and POST requests related to user profile updates, allowing users to modify their email, username, password, and profile picture.
 */
@Controller
@RequestMapping("/Profil")
@SessionAttributes({"user", "showAlert", "moderatorService", "customerService", "action", "profilInfo", "newValueInput", "passwordInput", "imgFile"})
public class ProfilController {

	private UserService userService;
	private CustomerService customerService;
	private ServletContext servletContext;

    /**
     * Constructor for ProfilController.
     *
     * @param us              UserService instance for user-related operations.
     * @param cs              CustomerService instance for customer-related operations.
     * @param servletContext  ServletContext for accessing the application's context.
     */
	public ProfilController(UserService us, CustomerService cs, ServletContext servletContext) {
		this.userService = us;
		this.customerService = cs;
		this.servletContext = servletContext;
	}

    /**
     * Handles GET requests for user profile updates.
     * Displays the user's profile information.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "profil" if the user is logged in; otherwise, redirects to "Index".
     */
	@GetMapping
    public String doGet(Model model) {
		if (!IndexController.isLogged(model)) {
			return "redirect:/Index";
		}
		model.addAttribute("customerService", customerService);
        return "profil";
    }

    /**
     * Handles POST requests for user profile updates.
     * Processes the form submission and updates the user's profile information.
     *
     * @param action         The action parameter from the form.
     * @param profilInfo     The profilInfo parameter from the form.
     * @param newValue       The newValueInput parameter from the form.
     * @param password       The passwordInput parameter from the form.
     * @param imgFile        The imgFile parameter from the form.
     * @param model          Model object for adding attributes used by the view.
     * @return The view "profil" if the user is logged in; otherwise, redirects to "Index".
     */
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
   				// Verify the password
   				if (userService.checkUserLogin(loginUser.getEmail(), password) || profilInfo.equals("deleteProfilePicture") || profilInfo.equals("profilePicture")) {
   					// Update the information
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
		return doGet(model);
    }
}
