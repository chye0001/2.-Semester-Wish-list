package com.example.wishlist.controller;

import com.example.wishlist.dto.WishSelectedDto;
import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishService;
import com.example.wishlist.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist/{wishlistId}/wish")
public class WishController {

    private WishService wishService;
    private WishlistService wishlistService;

    public WishController(WishService wishService, WishlistService wishlistService) {
        this.wishService = wishService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public String wish(Model model, @PathVariable int wishlistId) {
        model.addAttribute("activeLink","wishlist");
        System.out.println("id: " + wishlistId);
        return "This is a tesT: " + wishlistId;
    }

    @GetMapping("/add")
    public String showPageForAddingWish(Model model, @PathVariable long wishlistId, Authentication authentication) {
        model.addAttribute("activeLink","wishlist");
        Wish newWish = new Wish();
        String username = authentication.getName();
        String wishlistName = wishlistService.getWishlistNameFromWishlistId(username, wishlistId);

        model.addAttribute("addWish", newWish);
        model.addAttribute("wishlistName", wishlistName);
        model.addAttribute("wishlistId", wishlistId);
        return "wish/addWish";
    }

    @PostMapping("/add") //wishlistId bliver automatisk på wishlistId attributten i Wish-klassen, da det hedder det samme.
    public String addWishToWishlist(@ModelAttribute Wish newWish) {
        wishService.addWish(newWish);

        return "redirect:/wishlist/"+newWish.getWishlistId();
    }

    @GetMapping("/{wishId}/delete")
    public String deleteWishFromWishlistOnWishId(@PathVariable long wishId) {
        wishService.deleteWish(wishId);

        return "redirect:/wishlist";
    }

    @PostMapping("/deleteselected")
    public String deleteWishFromWishlist(@ModelAttribute WishSelectedDto selectedWishes, @PathVariable long wishlistId) {
        System.out.println("##### IDS: " + selectedWishes);
//        System.out.println(selectedWishes.selectedWishIds().getClass());
        if (selectedWishes.selectedWishIds() != null) wishService.deleteSelecetedWishes(selectedWishes.selectedWishIds());
        return "redirect:/wishlist/"+wishlistId;
        }

    @GetMapping("/{wishId}/edit")
    public String createEditWishForm(Model model, @PathVariable long wishId) {
        model.addAttribute("activeLink","wishlist");
        Wish wish = wishService.getWishFromWishId(wishId);
        model.addAttribute("wishToEdit", wish);

        return "/wish/editWish";
    }

    @PostMapping("/{wishId}/edit")
    public String editWish(@ModelAttribute Wish editedWish, @PathVariable long wishlistId) {
        wishService.editWish(editedWish);

        return "redirect:/wishlist/" + wishlistId;
    }
}
