package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface CRUDOperations {

    long createWishlist(String wishlistTitle, String pictureLink, String username);

    List<Wish> getWishes (long id);

    List<Wishlist> getAllWishlists (String username);

    boolean addWish(Wish newWish, String wishlistName);

}
