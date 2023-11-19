package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.User;


import java.io.IOException;

import com.mainapp.entity.*;

@Controller
@RequestMapping("/AddProduct")
//@SessionAttributes("user")
//Si il y a plusieurs var en session
@SessionAttributes({"name","price","stock","sellerId"})
public class AddProductController {

    @GetMapping
    public String doGet( Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "index";
        }

        return "addProduct";
    }

    @PostMapping
    public String doPost( Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "index";
        }
    	String name = (String) model.getAttribute("name");
    	double price = Double.parseDouble((String) model.getAttribute("price"));
		int stock = Integer.parseInt((String) model.getAttribute("stock"));
		int sellerId = Integer.parseInt((String) model.getAttribute("sellerId"));
		
		//Histoire de part et de file ?????
		//Part filePart = request.getPart("img");
        //String fileName = ServletIndex.getSubmittedFileName(filePart);
		
		
		//UserService userService = new UserService;
		//User seller = userDao.getUser(sellerId);
		
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		//product.setUser(seller);

		//ProductService productService = new ProductService;
        //String savePath = this.getServletContext().getRealPath("/img/Product");

		if (/*productDao.addProduct(product)*/true) {
	        //Save the image in the database
			if (/*productDao.updateProductImg(product, filePart, fileName, savePath)*/true) {
				System.out.println("msg validation");
				return "manageProduct";
				//response.getWriter().println("<script>showAlert('The product has been added!', 'success', './ManageProduct');</script>");
			} else {
				System.out.println("msg erreur");
				return "manageProduct";
				//response.getWriter().println("<script>showAlert('The product\\'s image has not been saved', 'warning', './ManageProduct');</script>");
			}
		} else {
			System.out.println("msg erreur2");
			return "manageProduct";
			//response.getWriter().println("<script>showAlert('Error ! The product has not been added', 'error', '');</script>");
		}
    }

   
}
