package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface CRUDOperations {

    boolean createWishlist(String wishlistTitle, String pictureLink);

    List<Wish> getWishes (String wishlistName);

    List<Wishlist> getAllWishlists ();

    boolean addWish(Wish newWish, String wishlistName);
}
