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

    @GetMapping("/{id}")
    public String viewWishlistByName(@PathVariable long id, Model model) {
        List<Wish> wishes = wishlistService.getWishes(id);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlistName", "TODO; FIX");

        return "viewWishlist";
    }

    @GetMapping("/delete/{wish}")
    public String deleteWishFromWishlistOnName(@PathVariable("wish") String wishName) {
        wishlistService.deleteWish(wishName);

        return "redirect:/wishlist";
    }

    @GetMapping("/wish/{wishId}/edit")
    public String editWish(@ModelAttribute Wish editedWish, @PathVariable long wishId, @RequestParam long wishlistId) {
        wishlistService.editWish(wishId, editedWish);

        return "redirect:/wishlist/"+wishlistId;
    }
}