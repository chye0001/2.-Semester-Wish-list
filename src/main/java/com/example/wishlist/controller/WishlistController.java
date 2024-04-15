package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.security.core.Authentication;
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
    public String wishlistMainPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Wishlist> wishlistList = wishlistService.getAllWishlists(username);
        model.addAttribute("wishlists", wishlistList);
        return "wishlist-main";
    }

    @GetMapping("/create")
    public String createWishlist() {
        return "wishlist-create";
    }

    @PostMapping("/create")
    public String createWishlist(@RequestParam("title") String wishlistTitle, @RequestParam String pictureLink, Authentication authentication) {
        String username = authentication.getName();
        long wishlistId = wishlistService.createWishlist(wishlistTitle, pictureLink, username);
        return "redirect:/wishlist/"+wishlistId;
    }

    @GetMapping("/{wishlistId}/addwish")
    public String showPageForAddingWish(Model model, @PathVariable long wishlistId) {
        Wish newWish = new Wish();

        model.addAttribute("addWish", newWish);
        model.addAttribute("wishlisttitle", "TODO;FIX");
        model.addAttribute("wishlistId", wishlistId);
        return "addWish";
    }

    @PostMapping("/{wishlistId}/addwish")
    public String addWishToWishlist(@ModelAttribute Wish newWish) {
        wishlistService.addWish(newWish);

        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistId}")
    public String viewWishlistByName(@PathVariable long wishlistId, Model model) {
        List<Wish> wishes = wishlistService.getWishes(wishlistId);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlistName", "TODO; FIX");

        return "viewWishlist";
    }
    //@GetMapping("/{wishlistId}/wish/{wishId}/delete")
    @GetMapping("/delete/{wish}")
    public String deleteWishFromWishlistOnName(@PathVariable("wish") String wishName) {
        wishlistService.deleteWish(wishName);

        return "redirect:/wishlist";
    }
}