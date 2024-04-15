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

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public String wishlistMainPage(Model model) {
        List<Wishlist> wishlistList = wishlistService.getAllWishlists();
        model.addAttribute("wishlists", wishlistList);
        return "wishlist-main";
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

    @GetMapping("/addWish/{wishlistName}")
    public String showPageForAddingWish(Model model, @PathVariable String wishlistName) {
        Wish newWish = new Wish();

        model.addAttribute("addWish", newWish);
        model.addAttribute("wishlistTitle", wishlistName);
        return "addWish";
    }

    @PostMapping("/addWish")
    public String addWishToWishlist(@ModelAttribute Wish newWish, @RequestParam String wishlistTitle) {
        wishlistService.addWish(newWish, wishlistTitle);

        return "redirect:/wishlist";
    }

    @GetMapping("/view/{name}")
    public String viewWishlistByName(@PathVariable("name") String name, Model model) {
        List<Wish> wishes = wishlistService.getWishes(name);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlistName", name);

        return "viewWishlist";
    }

    @GetMapping("/delete/{wish}")
    public String deleteWishFromWishlistOnName(@PathVariable("wish") String wishName) {
        wishlistService.deleteWish(wishName);

        return "redirect:/wishlist";
    }
}