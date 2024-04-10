package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/create")
    public String createWishlist() {
        return "wishlist-create";
    }

    @PostMapping("/create")
    public String createWishlist(@RequestParam("title") String wishlistTitle, @RequestParam String pictureLink) {
        wishlistService.createWishlist(wishlistTitle, pictureLink);
        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistName}/addWish")
    public String showPageForAddingWish(Model model, @PathVariable String wishlistName) {
        List<Wish> wishlist = wishlistService.getWishes(wishlistName);
        model.addAttribute("wishes", wishlist);
        return "addWish";
    }

    @PostMapping("/addWish")
    public String addWishToWishlist() {

        return "redirect:/wishlist";
    }
}