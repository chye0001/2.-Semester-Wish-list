package com.example.wishlist.service;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import com.example.wishlist.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistJDBC;
    public WishlistService(WishlistJDBC wishlistJDBC){
        this.wishlistJDBC = wishlistJDBC;
    }

    public long createWishlist(String wishlistTitle, String pictureLink, String username){
        return wishlistJDBC.createWishlist(wishlistTitle, pictureLink, username);
    }

    public Wishlist getWishlistById(long id) {
        return wishlistJDBC.getWishlistById(id);
    }

    public List<Wishlist> getAllWishlists(String username) {
        return wishlistJDBC.getAllWishlists(username);
    }

    public String getWishlistNameFromWishlistId(String username, long wishlistId) {
        String wishlistName = "";

        List<Wishlist> wishlists = wishlistJDBC.getAllWishlists(username);
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getWishlistId() == wishlistId) {
                wishlistName = wishlist.getName();
                break;
            }
        }

        return wishlistName;
    }


    public void deleteWishlist(int wishlistId) {
        wishlistJDBC.deleteWishlist(wishlistId);
    }
}
