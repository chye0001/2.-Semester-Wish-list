package com.example.wishlist.service;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    public WishlistService(WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    public long createWishlist(String wishlistTitle, String pictureLink, String username){
        return wishlistRepository.createWishlist(wishlistTitle, pictureLink, username);
    }

    @PreAuthorize("@authz.hasPermission(#id,authentication)")
    public Wishlist getWishlistById(long id) {
        return wishlistRepository.getWishlistById(id);
    }

    public Wishlist getWishlistByIdUnauthorized(long id) {
        return wishlistRepository.getWishlistById(id);
    }

    public List<Wishlist> getAllWishlists(String username) {
        return wishlistRepository.getAllWishlists(username);
    }

    @PreAuthorize("@authz.hasPermission(#wishlistId,authentication)")
    public String getWishlistNameFromWishlistId(String username, long wishlistId) {
        String wishlistName = "";

        List<Wishlist> wishlists = wishlistRepository.getAllWishlists(username);
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getWishlistId() == wishlistId) {
                wishlistName = wishlist.getName();
                break;
            }
        }

        return wishlistName;
    }

    @PreAuthorize("@authz.hasPermission(#wishlist.getWishlistId(),authentication)")
    public void editWishlist(Wishlist wishlist) {
        wishlistRepository.editWishlist(wishlist);
    }

    @PreAuthorize("@authz.hasPermission(#wishlistId,authentication)")
    public void deleteWishlist(long wishlistId) {
        wishlistRepository.deleteWishlist(wishlistId);
    }

    @PreAuthorize("@authz.hasPermission(#wishlistId,authentication)")
    public void setWishlistToPublic(long wishlistId) {
        wishlistRepository.setWishlistToPublic(wishlistId);
    }

    @PreAuthorize("@authz.hasPermission(#wishlistId,authentication)")
    public void deleteAllWishes(long wishlistId) {
        wishlistRepository.deleteAllWishes(wishlistId);
    }
}
