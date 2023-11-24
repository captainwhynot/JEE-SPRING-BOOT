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

@Controller
@RequestMapping("/AddProduct")
@SessionAttributes({"user", "moderatorService", "showAlert", "name", "price", "stock", "sellerId"})
public class AddProductController {

	private UserService userService;
	private ProductService productService;
	private ModeratorService moderatorService;
	private ServletContext servletContext;

	public AddProductController(UserService us, ProductService ps, ModeratorService ms, ServletContext servletContext) {
		this.userService = us;
		this.productService = ps;
		this.moderatorService = ms;
		this.servletContext = servletContext;
	}
    @GetMapping
    public String doGet(Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
	    model.addAttribute("moderatorService", moderatorService);
        return "addProduct";
    }

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
		
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setUser(seller);

        String savePath = this.servletContext.getRealPath("/img/Product");

		if (productService.addProduct(product)) {
	        //Save the image in the database
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
