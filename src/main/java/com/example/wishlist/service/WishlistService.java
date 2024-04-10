package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.CRUDOperations;
import com.example.wishlist.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final CRUDOperations wishlistJDBC;
    public WishlistService(WishlistJDBC wishlistJDBC){
        this.wishlistJDBC = wishlistJDBC;
    }

    public void createWishlist(String wishlistTitle, String pictureLink){
        wishlistJDBC.createWishlist(wishlistTitle, pictureLink);
    }

    public List<Wish> getWishes(String wishlistName) {
        return wishlistJDBC.getWishes(wishlistName);
    }

    public void addWish(Wish newWish, String wishlistTitle) {
        wishlistJDBC.addWish(newWish, wishlistTitle);
    }
}
