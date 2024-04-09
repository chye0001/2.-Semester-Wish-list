package com.example.wishlist.service;

import com.example.wishlist.repository.CRUDOperations;
import com.example.wishlist.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final CRUDOperations wishlistJDBC;
    public WishlistService(WishlistJDBC wishlistJDBC){
        this.wishlistJDBC = wishlistJDBC;
    }

    public void createWishlist(String wishlistTitle, String pictureLink){
        wishlistJDBC.createWishlist(wishlistTitle, pictureLink);
    }
}
