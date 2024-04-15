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
    public String createWishlist(@RequestParam String wishName, @RequestParam String pictureLink, Authentication authentication) {
        String username = authentication.getName();
        long wishlistId = wishlistService.createWishlist(wishName, pictureLink, username);

        return "redirect:/wishlist/"+wishlistId;
    }

    @GetMapping("/{wishlistId}/addwish")
    public String showPageForAddingWish(Model model, @PathVariable long wishlistId) {
        Wish newWish = new Wish();

        model.addAttribute("addWish", newWish);
        model.addAttribute("wishlistName", "TODO;FIX");
        model.addAttribute("wishlistId", wishlistId);
        return "addWish";
    }

    @PostMapping("/{wishlistId}/addwish") //wishlistId bliver automatisk på wishlistId attributten i Wish-klassen, da det hedder det samme.
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
    @GetMapping("/{wishlistId}/wish/{wishId}/delete")
    public String deleteWishFromWishlistOnWishId(@PathVariable long wishId) {
        wishlistService.deleteWish(wishId);

        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistId}/wish/{wishId}/edit")
    public String createEditWishForm(Model model, @PathVariable long wishId) {
        Wish wish = wishlistService.getWishFromWishId(wishId);
        model.addAttribute("wishToEdit", wish);

        return "editWish";
    }

    @PostMapping("/{wishlistId}/wish/{wishId}/edit")
    public String editWish(@ModelAttribute Wish editedWish) {
        wishlistService.editWish(editedWish);

        return "redirect:/wishlist/"+editedWish.getWishlistId();
    }
}