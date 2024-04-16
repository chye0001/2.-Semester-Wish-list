package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface WishlistRepository {

    long createWishlist(String wishlistTitle, String pictureLink, String username);

    Wishlist getWishlistById(long wishlistId);

    List<Wishlist> getAllWishlists (String username);

    boolean deleteWishlist(int wishlistId);

    boolean checkIdAndUsernameMatches(long id,String username);

}
