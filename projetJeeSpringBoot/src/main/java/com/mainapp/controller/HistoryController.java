package com.mainapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mainapp.entity.Basket;
import com.mainapp.entity.User;
import com.mainapp.service.BasketService;

@Controller
@RequestMapping("/History")
@SessionAttributes({"user", "basketList"})
public class HistoryController {

	private BasketService basketService;
	
	public HistoryController(BasketService bs) {
		this.basketService = bs;
	}
    
	@GetMapping
    public String doGet(Model model) {
		if(!IndexController.isLogged(model)) {
        	return "index";
        }
		User loginUser = IndexController.loginUser(model);
		List<Basket> basketList = basketService.getHistoryList(loginUser.getId());
		model.addAttribute("basketList", basketList);
        return "history";
    }

    @PostMapping
    public String doPost(Model model) {
        return doGet(model);
    }
}
