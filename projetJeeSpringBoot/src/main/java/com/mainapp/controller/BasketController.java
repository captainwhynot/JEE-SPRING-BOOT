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

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.User;
import com.mainapp.service.BasketService;
import com.mainapp.service.CreditCardService;
import com.mainapp.service.CustomerService;

import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")

@Controller
@RequestMapping("/Basket")
@SessionAttributes({"user", "showAlert", "customerService", "basketList", "basketId", "quantity", "action", "cardNumber", "cvv", "expirationDate"})
public class BasketController {

	private CreditCardService creditCardService;
	private CustomerService customerService;
	private BasketService basketService;
	
	public BasketController(CreditCardService ccs, BasketService bs, CustomerService cs) {
		this.creditCardService = ccs;
		this.basketService = bs;
		this.customerService = cs;
	}
    
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }
		List<Basket> basketList = basketService.getBasketList(IndexController.loginUser(model).getId());
		model.addAttribute("basketList",basketList);
        return "basket";
    }

    @PostMapping
    public String doPost(@RequestParam("action") String action,
    		@RequestParam(value = "basketId", required = false) Integer basketId,
    		@RequestParam(value = "quantity", required = false) Integer quantity,
    		@RequestParam(value = "cardNumber", required = false) Integer cardNumber,
    		@RequestParam(value = "cvv", required = false) Integer cvv,
    		@RequestParam(value = "expirationDate", required = false) String expirationDateString,
    		Model model) {
    	if(!IndexController.isLogged(model)) {
        	return "redirect:/Index";
        }    	
		if (action != null) {
			User loginUser = IndexController.loginUser(model);
			if (action.equals("confirmOrder")) {
				//Confirm the basket to pay
				List<Basket> basketList = basketService.getBasketList(loginUser.getId());
				model.addAttribute("basketList", basketList);
				
				if (basketList.isEmpty()) {
					model.addAttribute("showAlert", "<script>showAlert('Your basket is empty.', 'warning', './Basket');</script>");
					return "basket";
				} else {
					basketList = basketService.confirmOrder(loginUser.getId());
					model.addAttribute("basketList", basketList);
					
					if (basketList.isEmpty()) {
						model.addAttribute("showAlert", "<script>showAlert('Your basket is empty.', 'warning', './Basket');</script>");
						return "basket";
					} else {
						model.addAttribute("customerService",customerService);
						return "confirmOrder";
					}
				}
			} else if (action.equals("confirmCreditCard")) {
				//Enter the credit card to pay with
				return "checkCreditCard";
			} else if (action.equals("finalizePaiement")) {
				//Finalize paiement
				List<Basket> basketList = basketService.getBasketList(loginUser.getId());
				model.addAttribute("basketList", basketList);
								
				String[] expirationDateArray = expirationDateString.split("-");
				int year = Integer.parseInt(expirationDateArray[0]) - 1900;
				int month = Integer.parseInt(expirationDateArray[1]) - 1;
				int day = Integer.parseInt(expirationDateArray[2]);

				Date expirationDate = new Date(year, month, day);
				
				if (creditCardService.checkCreditCard(cardNumber, cvv, expirationDate)) {
					if (creditCardService.checkBalance(cardNumber, basketService.totalPrice(loginUser.getId()))) {
						//Mail's content
						double totalOrderPrice = 0;
						String container = "<span style='color: black'>Here is your paiement recapitulation :</span><br>";
						container += "<table style='border-collapse: collapse; color: black; text-align: center;' border=1>"
								+ "<thead>" + "<tr>" + "<th>Id</th>" + "<th>Product</th>" + "<th>Price</th>"
								+ "<th>Quantity</th>" + "<th>Seller</th>" + "<th>Total Price</th>" + "</tr>"
								+ "</thead>" + "<tbody>";

						for (Basket basket : basketList) {
							container += "<tr>" + "<td> " + basket.getId() + " </td>" + "<td>"
									+ basket.getProduct().getName() + "</td>" + "<td>" + basket.getProduct().getPrice()
									+ "</td>" + "<td>" + basket.getQuantity() + "</td>" + "<td>"
									+ basket.getProduct().getUser().getUsername() + "</td>";

							double totalPrice = basket.getProduct().getPrice() * basket.getQuantity();
							totalOrderPrice += totalPrice;

							container += "<td>" + String.format("%.2f", totalPrice) + " </td>" + "</tr>";
						}
						
						Customer customer = customerService.getCustomer(loginUser.getId());
						double fidelityPoint = customer.getFidelityPoint();
						String discount = (fidelityPoint > totalOrderPrice) ? String.format("%.2f", totalOrderPrice) : String.format("%.2f", fidelityPoint);
						
						container += "<tr>" + "<td colspan='5'>Total Order before discount</td>" + "<td>"
								+ String.format("%.2f", totalOrderPrice) + "</td>" + "</tr>" + "<tr>"
								+ "<td colspan='5'>Discount</td>" + "<td>" + discount + "</td>" + "</tr>" + "<tr>"
								+ "<td colspan='5'>Total Order Price :</td>" + "<td>"
								+ String.format("%.2f", totalOrderPrice) + "</td>" + "</tr>" + "</tbody>" + "</table><br>";
						container += "<span style='color: black'>Click here to access the site : </span>";
						container += "<a href=\"http://localhost:8080/projetJeeIng2/Index\">MANGASTORE</a>";

						if (basketService.finalizePaiement(loginUser.getId(), cardNumber, basketService.totalPrice(loginUser.getId()), container)) {
							model.addAttribute("showAlert", "<script>showAlert('Payment completed successfully.', 'success', './Basket');</script>");
							return "basket";
						} else {
							model.addAttribute("showAlert", "<script>showAlert('Payment failed.', 'error', './Basket');</script>");
							return "basket";
						}
					} else {
						model.addAttribute("showAlert", "<script>showAlert('Not enough credit on the credit card.', 'error', './Basket');</script>");
						return "basket";
					}
				} else {
					model.addAttribute("showAlert", "<script>showAlert('The credit card is invalid, please check the informations.', 'error', '');</script>");
					return "checkCreditCard";
				}
			} else {
				// No action : display basket
				return doGet(model);
			}
		} else {
			// No action : display basket
			return doGet(model);
		}
    }

    @PostMapping("/UpdateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestParam("basketId") Integer basketId,
    		@RequestParam("quantity") Integer quantity, 
    		Model model) {
    	try {
			Basket basket = basketService.getBasket(basketId);

			if (basketService.checkStock(basketId, quantity)) {
				if (basketService.updateQuantity(basket.getId(), quantity)) {
					return ResponseEntity.ok("Stock updated successfully."); //OK 200
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update stock."); //ERROR 500
				}
			} else {
				return ResponseEntity.badRequest().body("Not enough stock."); //BAD REQUEST 400
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update stock."); //ERROR 500
		}
    }
}