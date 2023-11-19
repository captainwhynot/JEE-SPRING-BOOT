//package com.mainapp.controller;
//
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
//
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/Basket")
////@SessionAttributes("user")
////Si il y a plusieurs var en session
//@SessionAttributes({"basketList","basketId","quantity","action","cardNumber","cvv","expirationDate"})
//public class BasketController {
//	
//	//test de BDD dans Test
//    //private TestService ts;
//    //BasketService + constructeur
//    //CreditCardService + consutructeur
//    //CustomerService + constructeur
//    @Autowired
//    /*public BasketController(TestService ts) {
//    	this.ts = ts;
//    }*/
//    
//	@GetMapping
//    public String doGet( Model model) {
//		if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//        
//        //BasketService basketService = new BasketService
//		//List<Basket> basketList = basketService.getBasketList(IndexController.loginUser(model).getId());
//		//model.addAttribute("basketList",basketList);
//        
//        return "basket";
//    }
//
//    @PostMapping
//    public String doPost( Model model) {
//    	if(!IndexController.isLogged(model)) {
//        	return "index";
//        }
//    	
//    	//BasketService basketService = new BasketService
//    	
//    	String action = (String) model.getAttribute("action");
//    	
//		if (action != null) {
//			if (action.equals("checkStock")) {
//				//Check the stock to update quantity
//				try {
//					int basketId = Integer.parseInt((String) model.getAttribute("basketId"));
//					int quantity = Integer.parseInt((String) model.getAttribute("quantity"));
//
//					//Basket basket = basketService.getBasket(basketId);
//					//response.setContentType("application/json");
//					//response.setCharacterEncoding("UTF-8");
//					
//					if (/*basketService.checkStock(basketId, quantity)*/true) {
//						if (/*basketService.updateQuantity(basket.getId(), quantity - basket.getQuantity())*/true) {
//							//response.setStatus(HttpServletResponse.SC_OK); // 200 OK
//							//response.getWriter().write("{\"status\": \"Enough stock.\"}");
//							System.out.println("msg erreur stock");
//						} else {
//							//response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//							//response.getWriter().write("{\"status\": \"Update failed.\"}");
//							System.out.println("msg errer requete");
//
//						}
//					} else {
//						//response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//						//response.getWriter().write("{\"status\": \"Insufficient stock.\"}");
//						System.out.println("msg erreur stock");
//
//					}
//				} catch (Exception e) {
//					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
//					//response.getWriter().write("{\"status\": \"Internal Server Error.\"}");
//					System.out.println("msg erreur interne");
//
//				}
//			} else if (action.equals("confirmOrder")) {
//				
//				//List<Basket> basketList = basketService.getBasketList(IndexController.loginUser(model).getId());
//				//model.addAttribute("basketList", basketList);
//				
//				if (/*basketList.isEmpty()*/true) {
//					//this.getServletContext().getRequestDispatcher("/basket.jsp").include(request, response);
//					//response.getWriter().println("<script>showAlert('Your basket is empty.', 'warning', './Basket');</script>");
//					System.out.println("msg erreur basket empty");
//					return "basket";
//
//				} else {
//					//basketList = basketService.confirmOrder(loginUser.getId());
//					//model.addAttribute("basketList", basketList);
//					
//					if (/*basketList.isEmpty()*/true) {
//						//this.getServletContext().getRequestDispatcher("/basket.jsp").include(request, response);
//						//response.getWriter().println("<script>showAlert('Your basket is empty.', 'warning', './Basket');</script>");
//						System.out.println("msg erreur basket empty");
//						return "basket";
//					} else {
//						//this.getServletContext().getRequestDispatcher("/confirmOrder.jsp").include(request, response);
//						return "confirmOrder";
//					}
//				}
//			} else if (action.equals("confirmCreditCard")) {
//				
//				//this.getServletContext().getRequestDispatcher("/checkCreditCard.jsp").include(request, response);
//				return "checkCreditCard";
//			} else if (action.equals("finalizePaiement")) {
//				//Finalize paiement
//				//List<Basket> basketList = basketService.getBasketList(IndexController.loginUser(model).getId());
//				//request.setAttribute("basketList", basketList);
//				
//				//ON DOIT ACTUALISER LA PAGE ?
//				//this.getServletContext().getRequestDispatcher("/basket.jsp").include(request, response);
//
//				int cardNumber = Integer.parseInt((String) model.getAttribute("cardNumber"));
//				int cvv = Integer.parseInt((String) model.getAttribute("cvv"));
//				String expirationDateString = (String) model.getAttribute("expirationDate");
//				
//				String[] expirationDateArray = expirationDateString.split("-");
//				int year = Integer.parseInt(expirationDateArray[0]) - 1900;
//				int month = Integer.parseInt(expirationDateArray[1]) - 1;
//				int day = Integer.parseInt(expirationDateArray[2]);
//
//				Date expirationDate = new Date(year, month, day);
//				//CreditCardService creditCardService = new CreditCard...
//				
//				if (/*creditCardService.checkCreditCard(cardNumber, cvv, expirationDate)*/true) {
//					if (/*creditCardService.checkBalance(cardNumber, basketService.totalPrice(loginUser.getId()))*/true) {
//						//Mail's content
//						double totalOrderPrice = 0;
//						String container = "<span style='color: black'>Here is your paiement recapitulation :</span><br>";
//						container += "<table style='border-collapse: collapse; color: black; text-align: center;' border=1>"
//								+ "<thead>" + "<tr>" + "<th>Id</th>" + "<th>Product</th>" + "<th>Price</th>"
//								+ "<th>Quantity</th>" + "<th>Seller</th>" + "<th>Total Price</th>" + "</tr>"
//								+ "</thead>" + "<tbody>";
//
//						/*for (Basket basket : basketList) {
//							container += "<tr>" + "<td> " + basket.getId() + " </td>" + "<td>"
//									+ basket.getProduct().getName() + "</td>" + "<td>" + basket.getProduct().getPrice()
//									+ "</td>" + "<td>" + basket.getQuantity() + "</td>" + "<td>"
//									+ basket.getProduct().getUser().getUsername() + "</td>";
//
//							double totalPrice = basket.getProduct().getPrice() * basket.getQuantity();
//							totalOrderPrice += totalPrice;
//
//							container += "<td>" + String.format("%.2f", totalPrice) + " </td>" + "</tr>";
//						}*/
//						
//						//CustomerService...
//						//Customer customer = customerService.getCustomer(loginUser.getId());
//						//double fidelityPoint = customer.getFidelityPoint();
//						String discount = (/*fidelityPoint*/0 > totalOrderPrice) ? String.format("%.2f", totalOrderPrice) : String.format("%.2f", /*fidelityPoint*/0);
//						
//						container += "<tr>" + "<td colspan='5'>Total Order before discount</td>" + "<td>"
//								+ String.format("%.2f", totalOrderPrice) + "</td>" + "</tr>" + "<tr>"
//								+ "<td colspan='5'>Discount</td>" + "<td>" + discount + "</td>" + "</tr>" + "<tr>"
//								+ "<td colspan='5'>Total Order Price :</td>" + "<td>"
//								+ String.format("%.2f", totalOrderPrice) + "</td>" + "</tr>" + "</tbody>" + "</table><br>";
//						container += "<span style='color: black'>Click here to access the site : </span>";
//						container += "<a href=\"http://localhost:8080/projetJeeIng2/Index\">MANGASTORE</a>";
//
//						if (/*basketService.finalizePaiement(loginUser.getId(), cardNumber, basketService.totalPrice(loginUser.getId()), container)*/true) {
//							//response.getWriter().println("<script>showAlert('Payment completed successfully.', 'success', './Basket');</script>");
//							System.out.println("msg payment succeed");
//							return "basket";
//						} else {
//							//response.getWriter().println("<script>showAlert('Payment failed.', 'error', './Basket');</script>");
//							System.out.println("msg erreur maiement");
//							return "basket";
//						}
//					} else {
//						//response.getWriter().println("<script>showAlert('Not enough credit on the credit card.', 'error', './Basket');</script>");
//						System.out.println("msg not enough credit");
//						return "basket";
//					}
//				} else {
//					//this.getServletContext().getRequestDispatcher("/checkCreditCard.jsp").include(request, response);
//					//response.getWriter().println("<script>showAlert('The credit card is invalid, please check the informations.', 'error', '');</script>");
//					System.out.println("msg erreur credit card");
//					return "checkCreditCard";
//				}
//			} else {
//				// No action : display basket
//				doGet(model);
//			}
//		}
//		// No action : display basket
//		else {
//			doGet(model);
//		}
//		
//        return "index";
//    }
//
//  
//}
