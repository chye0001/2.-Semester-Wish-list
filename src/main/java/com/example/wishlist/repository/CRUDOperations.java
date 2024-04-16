package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface CRUDOperations {

    long createWishlist(String wishlistTitle, String pictureLink, String username);

    Wishlist getWishlistById(long wishlistId);

    Wish getWishFromWishId(long wishId);


    List<Wishlist> getAllWishlists (String username);

    long addWish(Wish newWish);

    boolean deleteWish(long wishId);

    boolean editWish(Wish editedWish);

    boolean deleteWishlist(int wishlistId);

    boolean deleteSelectedWishes(List<Integer> wishIdList);

    boolean setWishlistToPublic(long wishlistId);
}
