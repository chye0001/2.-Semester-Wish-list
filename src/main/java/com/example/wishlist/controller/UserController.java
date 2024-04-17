package com.example.wishlist.controller;

import com.example.wishlist.dto.UserDto;
import com.example.wishlist.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        userService.registerUser(user);
        return "redirect:/user/login";
    }

    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getName());
        return "user/settings";
    }

    @GetMapping("/editPassword")
    public String editUserPassword(Authentication authentication, Model model) {
        model.addAttribute("user", new UserDto("Username","Old Password"));
        model.addAttribute("userEdited", new UserDto("Username","New Password"));
        return "user/editPassword";
    }

    @PostMapping("/editPassword")
    public String editUserPassword(UserDto user, UserDto userEdited) {
        userService.editUserPassword(user.password(),userEdited.password());
        return "redirect:/user/settings";
    }
}
