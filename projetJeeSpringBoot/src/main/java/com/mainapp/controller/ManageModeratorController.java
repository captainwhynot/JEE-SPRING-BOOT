package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Moderator;
import com.mainapp.service.ModeratorService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ManageModerator")
@SessionAttributes({"user", "showAlert", "moderatorList", "addProductList", "modifyProductList", "deleteProductList"})
public class ManageModeratorController {

	private ModeratorService moderatorService;
	
	public ManageModeratorController(ModeratorService ms) {
		this.moderatorService = ms;
	}
	
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
        List<Moderator> moderatorList = moderatorService.getModeratorList();
		model.addAttribute("moderatorList", moderatorList);
        return "manageModerator";
	}

    @PostMapping
    public String doPost(@RequestParam(value = "addProductList", required = false) String[] addProductList,
    		@RequestParam(value = "modifyProductList", required = false) String[] modifyProductList,
    		@RequestParam(value = "deleteProductList", required = false) String[] deleteProductList,
    		@RequestParam("moderatorList") String[] moderatorList,
    		Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
		if (moderatorList != null) {
			Moderator moderator = null;
			boolean allUpdated = true;
			
    		for (String email: moderatorList) {
    			moderator = moderatorService.getModerator(email);

    			//addProduct's Right
    			if (addProductList != null && Arrays.asList(addProductList).contains(email)) {
    				allUpdated = allUpdated && moderatorService.modifyRight(moderator, "add_product", true);
    			} else {
    				allUpdated = allUpdated && moderatorService.modifyRight(moderator, "add_product", false);
    			}
    			//modifyProduct's Right
    			if (modifyProductList != null && Arrays.asList(modifyProductList).contains(email)) {
    				allUpdated = allUpdated && moderatorService.modifyRight(moderator, "modify_product", true);
    			} else {
    				allUpdated = allUpdated && moderatorService.modifyRight(moderator, "modify_product", false);
    			}
    			//deleteProduct's Right
    			if (deleteProductList != null && Arrays.asList(deleteProductList).contains(email)) {
    				allUpdated = allUpdated && moderatorService.modifyRight(moderator, "delete_product", true);
   				} else {
   					allUpdated = allUpdated && moderatorService.modifyRight(moderator, "delete_product", false);
				}
    		}
    		
    		if (allUpdated) {
    			model.addAttribute("showAlert", "<script>showAlert('All rights updated successfully.', 'success', './ManageModerator')</script>");
			} else {
				model.addAttribute("showAlert", "<script>showAlert('Update failed.', 'error', './ManageModerator')</script>");
			}
		}		
        return "manageModerator";
    }
}
