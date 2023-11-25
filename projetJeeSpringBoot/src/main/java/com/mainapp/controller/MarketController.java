package com.mainapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Product;
import com.mainapp.service.ProductService;
import com.mainapp.service.UserService;

/**
 * Controller class for managing the market and product-related interactions.
 *
 * This controller handles both GET and POST requests related to displaying the market, searching for products, and filtering products by seller.
 */
@Controller
@RequestMapping("/Market")
@SessionAttributes({"user", "moderatorService", "userService", "productList", "search", "sellerId"})
public class MarketController {

	private ProductService productService;
	private UserService userService;

    /**
     * Constructor for MarketController.
     *
     * @param ps ProductService instance for product-related operations.
     * @param us UserService instance for user-related operations.
     */
	public MarketController(ProductService ps, UserService us) {
		this.productService = ps;
		this.userService = us;
	}

    /**
     * Handles GET requests for displaying the market.
     * Retrieves the list of all products and adds it to the model.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "market".
     */
	@GetMapping
    public String doGet(Model model) {
		// Get the list of all products for display
		List<Product> productList = productService.getProductList();
		model.addAttribute("productList", productList);
        return "market";
    }

    /**
     * Handles POST requests for market interactions.
     * Processes the form submission, and if the action is to search for products or
     * filter products by seller, updates the displayed product list accordingly.
     *
     * @param search   The search parameter from the form.
     * @param sellerId The sellerId parameter from the form.
     * @param model    Model object for adding attributes used by the view.
     * @return The view "market".
     */
    @PostMapping
    public String doPost(@RequestParam(value = "search", required = false) String search,
			    		@RequestParam(value = "sellerId", required = false) Integer sellerId,
			    		Model model) {
    	List<Product> productList = null;
    	if (search != null) {
            // If a search parameter exists, filter products based on the search criteria
    		productList = productService.getProductByName(search);
            model.addAttribute("search", search);
        } else if (sellerId != null) {
            // If a seller parameter exists, filter products based on the seller's ID
        	productList = productService.getSellerProducts(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("userService", userService);
        } else {
            // If no search or seller parameter exists, get the list of all products for display
        	productList = productService.getProductList();
        }
    	model.addAttribute("productList", productList);
    	return "market";
    }
}
