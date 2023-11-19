package com.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/Logout")
@SessionAttributes({"user"})
public class LogoutController {

	@GetMapping
    public String doGet(Model model, SessionStatus sessionStatus) {
        model.addAttribute("user", null);
        sessionStatus.setComplete();
        return "redirect:/Index";
    }

    @PostMapping
    public String doPost(Model model, SessionStatus sessionStatus) {
        return doGet(model, sessionStatus);
    }
}
