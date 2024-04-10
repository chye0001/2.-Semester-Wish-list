package com.example.wishlist.controller;

import com.example.wishlist.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto("Username","Password"));
        return "user/register";
    }

    @PostMapping("/register")
    public String register(UserDto user) {
        System.out.println("user: " + user);
        return "redirect:/user/settings";
    }


}
