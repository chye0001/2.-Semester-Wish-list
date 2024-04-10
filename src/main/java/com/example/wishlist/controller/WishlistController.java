package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/view/{name}")
    public String viewWishlistByName(@PathVariable("name") String name, Model model) {
        List<Wish> wishes = wishlistService.getWishes(name);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlistName", name);

        return "viewWishlist";

    }


}