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

@Controller
@RequestMapping("/Market")
@SessionAttributes({"user", "moderatorService", "userService", "productList", "search", "sellerId"})
public class MarketController {

	private ProductService productService;
	private UserService userService;
	
	public MarketController(ProductService ps, UserService us) {
		this.productService = ps;
		this.userService = us;
	}
    
	@GetMapping
    public String doGet(Model model) {
		List<Product> productList = productService.getProductList();
		model.addAttribute("productList", productList);
        return "market";
    }

    @PostMapping
    public String doPost(@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "sellerId", required = false) Integer sellerId,
    		Model model) {
    	List<Product> productList = null;
    	if (search != null) {
            productList = productService.getProductByName(search);
            model.addAttribute("search", search);
        } else if (sellerId != null) {
            productList = productService.getSellerProducts(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("userService", userService);
        } else {
            productList = productService.getProductList();
        }
    	model.addAttribute("productList", productList);
    	return "market";
    }
}
