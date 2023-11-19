//package com.mainapp.controller;
//
//import org.hibernate.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
////import com.mainapp.entity.Test;
//import com.mainapp.entity.User;
////import com.mainapp.repository.TestRepository;
////import com.mainapp.service.TestService;
//
//import com.mainapp.entity.*;
//import jakarta.servlet.http.Part;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/ManageProduct")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"productList","action","productId","img","name","price","stock"})
//public class ManageProductController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //ProductService
//    
//    @Autowired
//    /*public ManageProductController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//		if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//        //Product service...
//		User loginUser = IndexController.loginUser(model);
//		if (loginUser.getTypeUser().equals("Administrator")) {
//			//List<Product> productList = productService.getProductList();		
//		    //model.addAttribute("productList", productList);
//   		} else if (loginUser.getTypeUser().equals("Moderator")) {
//   			//Requete à add...
//   			//List<Product> productList = nvrequette();
//		    //model.addAttribute("productList", productList);
//   		}
//		
//        return "manageProduct";
//    }
//
//    @PostMapping
//    public String doPost( Model model) {
//    	if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//    	  String action = (String) model.getAttribute("action");
//          
//          if (action != null) {
//             
//          	if (action.equals("deleteProduct")) {
//                  
//  	            try {
//  	                String productIdString = (String) model.getAttribute("productId");
//  	                int productId = Integer.parseInt(productIdString);
//
//  	                //response.setContentType("application/json");
//  	                //response.setCharacterEncoding("UTF-8");
//  	
//  	                if (/*productService.deleteProduct(productId)*/true) {
//  	                    //response.setStatus(HttpServletResponse.SC_OK); // 200 OK
//  	                    //response.getWriter().write("{\"status\": \"Product deleted successfully.\"}");
//  	                	System.out.println("msg succes suppression reussie");
//  	                } else {
//  	                    //response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//  	                    //response.getWriter().write("{\"status\": \"Failed to delete product.\"}");
//  	                	System.out.println("msg erreur suppression ratee");
//
//  	                }
//  	            } catch (Exception e) {
//  	                //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
//  	                //response.getWriter().write("{\"status\": \"Internal Server Error.\"}");
//	                	System.out.println("msg succes interne");
//
//  	            }
//          	} else if (action.equals("updateProduct")){
//          		doGet(model);
//          		//Update product's informations
//                  //Collection<Part> fileParts = model.getParts();
//                  List<Part> filePartsString = new ArrayList<>();
//                  List<String> fileNameString = new ArrayList<>();
//                  
//                  /*for (Part filePart : fileParts) {
//                      if (filePart.getName().equals("imgFile")) {
//                          String fileName = ServletIndex.getSubmittedFileName(filePart);
//                          filePartsString.add(filePart);
//                          fileNameString.add(fileName);
//                      }
//                  }*/
//                  
//                  String[] imgString = (String[]) model.getAttribute("img");
//                  String[] nameString = (String[]) model.getAttribute("name");
//                  String[] priceString = (String[]) model.getAttribute("price");
//                  String[] stockString = (String[]) model.getAttribute("stock");
//
//                  //List<Product> productList = productService.getProductList();
//                  if (/*productList != null &&*/ imgString != null && nameString != null && priceString != null && stockString != null && fileNameString != null) {
//                  	Product product = null;
//                  	String name = null;
//                  	double price = 0;
//                  	int stock = 0;
//
//          	        //String savePath = this.getServletContext().getRealPath("/img/Product");
//                  	String fileName = null;
//          	        Part filePart = null;
//          	        
//                  	for (int i = 0; i < /*productList.size()*/9; i++) {
//                  		product = /*productList.get(i);*/null;
//                  		name = nameString[i];
//                  		price = Double.parseDouble(priceString[i]);
//                  		stock = Integer.parseInt(stockString[i]);
//                  		//If no file has been uploaded : get the old fileName
//                  		fileName = fileNameString.get(i);
//                  		if (fileName == null || fileName.equals("")) {
//                  			fileName = imgString[i];
//                  		}
//
//                  		filePart = filePartsString.get(i);
//                  		//Update all the product's information if there is a change
//                  		if (!product.getName().equals(name) || Double.compare(product.getPrice(), price) != 0 || Integer.compare(product.getStock(), stock) != 0) {
//              				if (/*!productService.modifyProduct(product, name, price, stock)*/true) {
//                     				//response.getWriter().println("<script>showAlert('An error has occured updating the product.', 'error', './ManageProduct')</script>");
//              					System.out.println("erreur update product");
//              					return "manageProduct";
//                  			}
//                  		}
//                  		//Update the product's image if this is a new image
//                  		if (!fileName.contains("img/Product/")) {
//                  			if (/*!productDao.updateProductImg(product, filePart, fileName, savePath)*/true) {
//                  					
//                     				//response.getWriter().println("<script>showAlert('An error has occured updating the product\\'s image.', 'error', './ManageProduct')</script>");
//                  				System.out.println("erreur update product");
//                  				return "manageProduct";
//                  			}
//                  		}
//                  	}
//                  	//response.getWriter().println("<script>showAlert('All products have been updated successfully.', 'success', './ManageProduct')</script>");
//                  	System.out.println("msg update success");
//                  	return "manageProduct";
//                  } 
//          	}
//          } else {
//      		return doGet(model);
//          }
//          return "manageProduct";
//    }
//
//    
//}
