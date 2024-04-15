package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
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

    public long createWishlist(String wishlistTitle, String pictureLink, String username){
        return wishlistJDBC.createWishlist(wishlistTitle, pictureLink, username);
    }

    public List<Wish> getWishes(long wishlistId) {
        return wishlistJDBC.getWishes(wishlistId);
    }

    public void addWish(Wish newWish) {
        wishlistJDBC.addWish(newWish);
    }

    public List<Wishlist> getAllWishlists(String username) {
        return wishlistJDBC.getAllWishlists(username);
    }

    public void deleteWish(long wishId) {
        wishlistJDBC.deleteWish(wishId);
    }

    public void editWish(Wish editedWish) {
        wishlistJDBC.editWish(editedWish);
    }
}
