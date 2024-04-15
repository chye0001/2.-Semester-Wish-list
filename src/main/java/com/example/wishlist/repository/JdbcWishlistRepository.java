package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcWishlistRepository implements WishRepository{


    @Override
    public long addWish(Wish wish) {
        return 0;
    }

    @Override
    public Wish getWish(long wishId) {
        return null;
    }

    @Override
    public List<Wish> getWishes(long wishId) {
        return List.of();
    }

    @Override
    public boolean deleteWish(long wishId) {
        return false;
    }

    @Override
    public boolean updateWish(Wish updatedWish) {
        return false;
    }
}
