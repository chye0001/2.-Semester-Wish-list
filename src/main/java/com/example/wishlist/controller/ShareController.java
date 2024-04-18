package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wishlist/{wishlistId}/shared")
public class ShareController {

    private final WishlistService wishlistService;
    private final WishService wishService;
    public ShareController (WishlistService wishlistService, WishService wishService) {
        this.wishlistService = wishlistService;
        this.wishService = wishService;
    }

    @GetMapping("")
    public String viewSharedWishlist(Model model, @PathVariable long wishlistId) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        boolean isPublic = wishlist.isPublic();

        if (!isPublic) {
            return "/error/403";
        }

        String wishlistName = wishlist.getName();
        List<Wish> wishes = wishlist.getWishes();

        model.addAttribute("wishlistName", wishlistName);
        model.addAttribute("wishes", wishes);

        return "/wishlist/viewSharedWishlist";
    }

    @PostMapping("/wish/{wishId}/reserve")
    public String reserveWish(Model model, @PathVariable long wishlistId, @PathVariable long wishId) {
        wishService.reserveWish(wishId);
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        String wishlistName = wishlist.getName();
        List<Wish> wishes = wishlist.getWishes();

        model.addAttribute("wishlistName", wishlistName);
        model.addAttribute("wishes", wishes);

        return "redirect:/wishlist/viewSharedWishlist";
    }
}