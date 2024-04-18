package com.example.wishlist.controller;

import com.example.wishlist.dto.WishSelectedDto;
import com.example.wishlist.dto.WishlistFormDto;
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
        model.addAttribute("activeLink","wishlist");
        String username = authentication.getName();
        List<Wishlist> wishlistList = wishlistService.getAllWishlists(username);
        model.addAttribute("wishlists", wishlistList);
        return "wishlist/wishlistOverview";
    }

    @GetMapping("/create")
    public String createWishlist(Model model) {
        model.addAttribute("activeLink","wishlist");
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
        model.addAttribute("activeLink","wishlist");
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        WishSelectedDto selectedWishes = new WishSelectedDto(List.of());
        model.addAttribute("wishes", wishlist.getWishes());
        model.addAttribute("wishlistName", wishlist.getName());
        model.addAttribute("selectedWishes", selectedWishes);
        model.addAttribute("wishlistId", wishlist.getWishlistId());
        model.addAttribute("wishlist", wishlist);

        boolean isPublic = wishlist.isPublic();

        if (isPublic) {
            String wishlistLink = "localhost:8080/wishlist/" + wishlistId + "/shared"; //bør nok ændres i fremtiden
            model.addAttribute("wishlistLink", wishlistLink);
        }
        return "wishlist/viewWishlist";
    }

    @PostMapping("/{wishlistId}/share")
    public String setIsPublicToTrueForWishlist(@PathVariable long wishlistId) {
        wishlistService.setWishlistToPublic(wishlistId);

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/{wishlistId}/delete")
    public String deleteWishlistOnId(@PathVariable long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);

        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistId}/deleteWishes")
    public String deleteAllWishesOnWishlistId(@PathVariable long wishlistId) {

        wishlistService.deleteAllWishes(wishlistId);

        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistId}/edit")
    public String createEditWishlistForm(Model model, @PathVariable long wishlistId) {
        model.addAttribute("activeLink","wishlist");
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        model.addAttribute("wishlistToEdit", wishlist);

        return "wishlist/editWishlist";
    }

    @PostMapping("/{wishlistId}/edit")
    public String editWish(@ModelAttribute Wishlist editedWishlist, @PathVariable long wishlistId) {
        wishlistService.editWishlist(editedWishlist);

        return "redirect:/wishlist";
    }
}