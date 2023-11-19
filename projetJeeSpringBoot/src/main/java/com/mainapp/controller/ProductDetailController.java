package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.Product;
import com.mainapp.entity.User;
import com.mainapp.service.BasketService;
import com.mainapp.service.CustomerService;
import com.mainapp.service.ProductService;

@Controller
@RequestMapping("/Product")
@SessionAttributes({"user", "showAlert", "productId", "action"})
public class ProductDetailController {

	private ProductService productService;
	private CustomerService customerService;
	private BasketService basketService;
	
	public ProductDetailController(ProductService ps, BasketService bs, CustomerService cs) {
		this.productService = ps;
		this.basketService = bs;
		this.customerService = cs;
	}
    
	@GetMapping
    public String doGet(@RequestParam("productId") Integer productId,
    		Model model) {
		model.addAttribute("productId", productId);
        return "product";
    }

    @PostMapping
    public String doPost(@RequestParam(value = "action", required = false) String action,
    		@RequestParam("productId") Integer productId,
    		Model model) {		
		if (action != null) {
			if (action.equals("addOrder")) {
				//Add the product to the basket
				if (IndexController.isLogged(model)) {
					User user = IndexController.loginUser(model);
					if (user.getTypeUser().equals("Customer")) {
						Customer customer = customerService.getCustomer(user.getId());
		
						Product product = productService.getProduct(productId);
						
						Basket basket = new Basket(product, 1, customer);
						if (basketService.addOrder(basket, user.getId(), 1)) {
          					model.addAttribute("showAlert", "<script>showAlert('Product successfully added to the basket.', 'success', '')</script>");
						} else {
          					model.addAttribute("showAlert", "<script>showAlert('Failed to add the product to the basket.', 'error', '')</script>");
						}
					}
					else {
      					model.addAttribute("showAlert", "<script>showAlert('You must be logged in with a Customer account to add a product to your basket.', 'warning', '')</script>");
					}
				} else {
  					model.addAttribute("showAlert", "<script>showAlert('You must be logged in to add a product to your basket.', 'warning', './Login')</script>");
				}
				return "product";
			}
		}
		return doGet(productId, model);
    }
}
