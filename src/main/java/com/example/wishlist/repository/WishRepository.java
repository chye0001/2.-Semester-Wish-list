package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface WishRepository {

    long addWish(Wish wish);

    Wish getWish(long wishId);

    List<Wish> getWishes(long wishId);

    boolean deleteWish(long wishId);

    boolean updateWish(Wish updatedWish);

}
