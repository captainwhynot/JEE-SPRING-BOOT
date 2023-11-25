package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.*;
import com.mainapp.service.ModeratorService;
import com.mainapp.service.ProductService;
import com.mainapp.service.UserService;

import jakarta.servlet.ServletContext;

/**
 * Controller class for adding a product.
 *
 * This controller handles both GET and POST requests related to adding a product.
 */
@Controller
@RequestMapping("/AddProduct")
@SessionAttributes({"user", "moderatorService", "showAlert", "name", "price", "stock", "sellerId"})
public class AddProductController {

	private UserService userService;
	private ProductService productService;
	private ModeratorService moderatorService;
	private ServletContext servletContext;

    /**
     * Constructor for AddProductController.
     *
     * @param us             UserService instance for user-related operations.
     * @param ps             ProductService instance for product-related operations.
     * @param ms             ModeratorService instance for moderator-related operations.
     * @param servletContext ServletContext for retrieving the real path to save images.
     */
	public AddProductController(UserService us, ProductService ps, ModeratorService ms, ServletContext servletContext) {
		this.userService = us;
		this.productService = ps;
		this.moderatorService = ms;
		this.servletContext = servletContext;
	}

    /**
     * Handles GET requests for adding a product.
     * Displays the add product page.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "addProduct" if the user is logged in, otherwise redirects to the index page.
     */
    @GetMapping
    public String doGet(Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
	    model.addAttribute("moderatorService", moderatorService);
        return "addProduct";
    }

    /**
     * Handles POST requests for adding a product.
     * Processes the addition of a new product.
     *
     * @param name      Name of the product.
     * @param price     Price of the product.
     * @param stock     Stock quantity of the product.
     * @param sellerId  ID of the seller (user) adding the product.
     * @param imgFile   MultipartFile representing the product image.
     * @param model     Model object for adding attributes used by the view.
     * @return The view "addProduct" after processing the addition of the product.
     */
    @PostMapping
    public String doPost(@RequestParam("name") String name,
			    		@RequestParam("price") double price,
			    		@RequestParam("stock") int stock,
			    		@RequestParam("sellerId") int sellerId,
			            @RequestParam("img") MultipartFile imgFile,
			            Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
    	
		User seller = userService.getUser(sellerId);
		
		// Create the product to add
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setUser(seller);

        String savePath = this.servletContext.getRealPath("/img/Product");

        // Add the product in the database
        if (productService.addProduct(product)) {
	        // Save the image in the database
			if (productService.updateProductImg(product, imgFile, "", savePath)) {
				model.addAttribute("showAlert", "<script>showAlert('The product has been added.', 'success', './ManageProduct')</script>");
			} else {
				model.addAttribute("showAlert", "<script>showAlert('The product\\'s image has not been saved.', 'warning', './ManageProduct')</script>");
			}
		} else {
			model.addAttribute("showAlert", "<script>showAlert('Error ! The product has not been added.', 'error', '')</script>");
		}
		return doGet(model);
    }

   
}
