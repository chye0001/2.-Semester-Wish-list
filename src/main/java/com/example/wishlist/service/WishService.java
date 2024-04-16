package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }
//    long addWish(Wish wish);
//    Wish getWish(long id);
//    boolean deleteWish(long id);
//    boolean editWish(Wish wish);
//    boolean deleteSelectedWishes(List<Integer> wishIdList);

    public long addWish(Wish wish) {
        return wishRepository.addWish(wish);
    }

    public Wish getWish(long id) {
        return wishRepository.getWish(id);
    }

    public boolean deleteWish(long id) {
        return wishRepository.deleteWish(id);
    }

    public boolean editWish(Wish wish) {
        return wishRepository.editWish(wish);
    }

    public boolean deleteSelecetedWishes(List<Integer> ids) {
        return wishRepository.deleteSelectedWishes(ids);
    }

    public Wish getWishFromWishId(long wishId) {
        return wishRepository.getWish(wishId);
    }


}
