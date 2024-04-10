package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface CRUDOperations {

    boolean createWishlist(String wishlistTitle, String pictureLink);

    List<Wish> getWishes (String wishlistName);
}
