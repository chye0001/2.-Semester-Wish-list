package com.example.wishlist.controller;

import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist/{id}/wish")
public class WishController {

    private WishlistService wishlistService;

    public WishController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public String wish(Model model, @PathVariable int id) {
        System.out.println("id: " + id);
        return "This is a tesT: " + id;
    }

}
