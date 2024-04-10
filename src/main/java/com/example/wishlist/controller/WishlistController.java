package com.example.wishlist.controller;

import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService){
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public String wishlistMainPage(){
        return "wishlist-main";
    }

    @GetMapping("/create")
    public String createWishlist(){
        return "wishlist-create";
    }

    @PostMapping("/create")
    public String createWishlist(@RequestParam("title") String wishlistTitle, @RequestParam String pictureLink){
        wishlistService.createWishlist(wishlistTitle, pictureLink);
        return "redirect:/wishlist";
    }


}