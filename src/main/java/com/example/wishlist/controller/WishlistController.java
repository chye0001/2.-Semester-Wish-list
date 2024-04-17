package com.example.wishlist.controller;

import com.example.wishlist.dto.WishlistFormDto;
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
        return "wishlist/wishlistOverview";
    }

    @GetMapping("/create")
    public String createWishlist(Model model) {
        WishlistFormDto emptyWishlist = new WishlistFormDto("wishlistName", "pictureLink");
        model.addAttribute("wishlist", emptyWishlist);

        return "wishlist/createWishlist";
    }

    @PostMapping("/create")
    public String createWishlist(@ModelAttribute WishlistFormDto wishlistFormDto, Authentication authentication) {
        String username = authentication.getName();
        String wishlistName = wishlistFormDto.wishlistName();
        String pictureLink = wishlistFormDto.pictureLink();
        long wishlistId = wishlistService.createWishlist(wishlistName, pictureLink, username);

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/{wishlistId}")
    public String viewWishlistById(@PathVariable long wishlistId, Model model) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        model.addAttribute("wishes", wishlist.getWishes());
        model.addAttribute("wishlistName", wishlist.getName());

        return "wishlist/viewWishlist";
    }



    @GetMapping("/{wishlistId}/delete")
    public String deleteWishlistOnId(@PathVariable int wishlistId) {
        wishlistService.deleteWishlist(wishlistId);

        return "redirect:/wishlist";
    }
}