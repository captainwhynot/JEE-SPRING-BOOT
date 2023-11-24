package com.mainapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.Product;
import com.mainapp.entity.User;
import com.mainapp.service.ModeratorService;
import com.mainapp.service.ProductService;

import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/ManageProduct")
@SessionAttributes({"user", "showAlert", "moderatorService", "productList", "action", "productId", "img", "name", "price", "stock", "imgFile"})
public class ManageProductController {

	private ProductService productService;
	private ModeratorService moderatorService;
	private ServletContext servletContext;
	
	public ManageProductController(ProductService ps, ModeratorService ms, ServletContext servletContext) {
		this.productService = ps;
		this.moderatorService = ms;
		this.servletContext = servletContext;
	}
    
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
		User loginUser = IndexController.loginUser(model);
		if (loginUser.getTypeUser().equals("Administrator")) {
			// If Administrator : get all the products' list
			List<Product> productList = productService.getProductList();		
		    model.addAttribute("productList", productList);
   		} else if (loginUser.getTypeUser().equals("Moderator")) {
			// If Moderator : get his own product list
   			List<Product> productList = productService.getSellerProducts(loginUser.getId());
		    model.addAttribute("productList", productList);
		    model.addAttribute("moderatorService", moderatorService);
   		}
        return "manageProduct";
    }

    @PostMapping
    public String doPost(@RequestParam("action") String action,
    		@RequestParam(value = "productId", required = false) Integer productId,
    		@RequestParam(value = "imgFile", required = false) MultipartFile[] imgFileArray,
    		@RequestParam(value = "img", required = false) String[] imgString,
    		@RequestParam(value = "name", required = false) String[] nameString,
    		@RequestParam(value = "price", required = false) String[] priceString,
    		@RequestParam(value = "stock", required = false) String[] stockString,
    		Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
    	System.out.println("action : " + action);
        if (action != null) {
          	if (action.equals("deleteProduct")) {
            	System.out.println("productId : " + productId);
          		this.deleteProduct(productId, model);
          	} else if (action.equals("updateProduct")){
          		  //Update product's informations
                  List<String> fileNameString = new ArrayList<>();
                  for (MultipartFile imgFile : imgFileArray) {
                      fileNameString.add(imgFile.getOriginalFilename());
                  }
                  List<Product> productList;
                  if (IndexController.loginUser(model).getTypeUser().equals("Administrator")) productList = productService.getProductList();
                  else productList = productService.getSellerProducts(IndexController.loginUser(model).getId());

                  if (productList != null && imgString != null && nameString != null && priceString != null && stockString != null && fileNameString != null) {
                  	Product product = null;
                  	String name = null;
                  	double price = 0;
                  	int stock = 0;

          	        String savePath = this.servletContext.getRealPath("/img/Product");
                  	String fileName = null;
                  	MultipartFile imgFile = null;
                  	for (int i = 0; i < productList.size(); i++) {
                  		product = productList.get(i);;
                  		name = nameString[i];
                  		price = Double.parseDouble(priceString[i]);
                  		stock = Integer.parseInt(stockString[i]);
                  		//If no file has been uploaded : get the old fileName
                  		fileName = fileNameString.get(i);
                  		if (fileName == null || fileName.equals("")) {
                  			fileName = imgString[i];
                  		}
                  		imgFile = imgFileArray[i];
                  		
                  		//Update all the product's information if there is a change
                  		if (!product.getName().equals(name) || Double.compare(product.getPrice(), price) != 0 || Integer.compare(product.getStock(), stock) != 0) {
              				if (!productService.modifyProduct(product, name, price, stock)) {
              					model.addAttribute("showAlert", "<script>showAlert('An error has occured updating the product.', 'error', './ManageProduct')</script>");
                  			}
                  		}
                  		//Update the product's image if this is a new image
                  		if (!fileName.contains("img/Product/")) {
                  			if (!productService.updateProductImg(product, imgFile, fileName, savePath)) {
              					model.addAttribute("showAlert", "<script>showAlert('An error has occured updating the product\\'s image.', 'error', './ManageProduct')</script>");
                  			}
                  		}
                  }
  				  model.addAttribute("showAlert", "<script>showAlert('All products have been updated successfully.', 'success', './ManageProduct')</script>");
                  }
          	}
	    } else {
	    	return doGet(model);
	    }
        return "manageProduct";
    }    
    
    public ResponseEntity<String> deleteProduct(int productId, Model model) {
        try {
            if (productService.deleteProduct(productId)) {
            	return ResponseEntity.ok("Product deleted successfully.");
            } else {
            	return ResponseEntity.ok("Failed to delete product.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product.");
        }
    }
}
