package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist/{wishlistId}/share")
public class ShareController {

    private WishlistService wishlistService;
    public ShareController (WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public String viewSharedWishlist(Model model, @PathVariable long wishlistId) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        String wishlistName = wishlist.getName();
        model.addAttribute("wishlistName", wishlistName);

        return "/wishlist/viewSharedWishlist";
    }
}