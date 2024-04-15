package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.util.List;

public interface CRUDOperations {

    long createWishlist(String wishlistTitle, String pictureLink, String username);

    List<Wish> getWishes (long wishlistId);

    Wish getWishFromWishId(long wishId);

    List<Wishlist> getAllWishlists (String username);

    long addWish(Wish newWish);

    boolean deleteWish(long wishId);

    boolean editWish(Wish editedWish);

    boolean deleteSelectedWishes(List<Integer> wishIdList);
}
