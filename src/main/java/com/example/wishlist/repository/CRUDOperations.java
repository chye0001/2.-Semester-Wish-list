package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface CRUDOperations {

    long createWishlist(String wishlistTitle, String pictureLink, String username);

    Wishlist getWishlistById(long wishlistId);

    List<Wishlist> getAllWishlists (String username);

    boolean addWish(Wish newWish);

    boolean deleteWish(String wishName);

    boolean deleteWishlist(int wishlistId);

    boolean deleteSelectedWishes(List<Integer> wishIdList);
}
