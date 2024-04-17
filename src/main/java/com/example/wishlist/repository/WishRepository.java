package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface WishRepository {
    long addWish(Wish wish);
    Wish getWish(long id);
    boolean deleteWish(long id);
    boolean editWish(Wish wish);
    boolean deleteSelectedWishes(List<Integer> wishIdList);
}
