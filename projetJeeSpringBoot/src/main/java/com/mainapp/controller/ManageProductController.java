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

/**
 * Controller class for managing product information and interactions.
 *
 * This controller handles both GET and POST requests related to managing products.
 */
@Controller
@RequestMapping("/ManageProduct")
@SessionAttributes({"user", "showAlert", "moderatorService", "productList", "action", "productId", "img", "name", "price", "stock", "imgFile"})
public class ManageProductController {

	private ProductService productService;
	private ModeratorService moderatorService;
	private ServletContext servletContext;

    /**
     * Constructor for ManageProductController.
     *
     * @param ps ProductService instance for product-related operations.
     * @param ms ModeratorService instance for moderator-related operations.
     * @param servletContext ServletContext for obtaining the real path of the image directory.
     */
	public ManageProductController(ProductService ps, ModeratorService ms, ServletContext servletContext) {
		this.productService = ps;
		this.moderatorService = ms;
		this.servletContext = servletContext;
	}

    /**
     * Handles GET requests for managing products.
     * Retrieves the list of products based on the user's role (Administrator or Moderator)
     * and adds it to the model.
     *
     * @param model Model object for adding attributes used by the view.
     * @return The view "manageProduct".
     */
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
		User loginUser = IndexController.loginUser(model);
		if (loginUser.getTypeUser().equals("Administrator")) {
   			// Get the list of all products for administrators
			List<Product> productList = productService.getProductList();		
		    model.addAttribute("productList", productList);
   		} else if (loginUser.getTypeUser().equals("Moderator")) {
   			// Get the list of products of the logged in moderator
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
        if (action != null) {
          	if (action.equals("updateProduct")){
          		  // Update product's informations
                  // Get all the uploaded files' data from the request
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
                  		// If no file has been uploaded : get the old fileName
                  		fileName = fileNameString.get(i);
                  		if (fileName == null || fileName.equals("")) {
                  			fileName = imgString[i];
                  		}
                  		imgFile = imgFileArray[i];
                  		
                  		// Update all the product's information if there is a change
                  		if (!product.getName().equals(name) || Double.compare(product.getPrice(), price) != 0 || Integer.compare(product.getStock(), stock) != 0) {
              				if (!productService.modifyProduct(product, name, price, stock)) {
              					model.addAttribute("showAlert", "<script>showAlert('An error has occured updating the product.', 'error', './ManageProduct')</script>");
                  			}
                  		}
                  		// Update the product's image if this is a new image
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

    /**
     * Handles POST requests to delete a product.
     * Deletes the specified product and returns a response.
     *
     * @param productId The productId parameter from the form.
     * @param model     Model object for adding attributes used by the view.
     * @return ResponseEntity with a success or failure message.
     */
    @PostMapping("/DeleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestParam("productId") int productId, Model model) {
        try {
            // Delete product
        	String savePath = this.servletContext.getRealPath("/img/Product");
            if (productService.deleteProduct(productId, savePath)) {
            	return ResponseEntity.ok("Product deleted successfully.");
            } else {
            	return ResponseEntity.badRequest().body("Failed to delete product.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product.");
        }
    }
}
