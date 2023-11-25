package com.mainapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Basket;
import com.mainapp.entity.User;
import com.mainapp.service.BasketService;

/**
 * Controller class for managing user purchase history.
 *
 * This controller handles both GET and POST requests related to the user's purchase history.
 */
@Controller
@RequestMapping("/History")
@SessionAttributes({"user", "basketList"})
public class HistoryController {

	private BasketService basketService;

    /**
     * Constructor for HistoryController.
     *
     * @param bs BasketService instance for basket-related operations.
     */
	public HistoryController(BasketService bs) {
		this.basketService = bs;
	}

    /**
     * Handles GET requests for the user's purchase history.
     * Displays the purchase history page.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "history" if the user is logged in, otherwise redirects to the index page.
     */
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "index";
        }
		User loginUser = IndexController.loginUser(model);
		// Get the customer's purchase history
		List<Basket> basketList = basketService.getHistoryList(loginUser.getId());
		model.addAttribute("basketList", basketList);
        return "history";
    }

    /**
     * Handles POST requests for the user's purchase history.
     * Redirects to the GET method to display the purchase history page.
     *
     * @param model Model object for adding attributes used by the view.
     * @return Redirects to the "History" view.
     */
    @PostMapping
    public String doPost(Model model) {
        return doGet(model);
    }
}
