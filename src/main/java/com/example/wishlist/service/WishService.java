package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @PreAuthorize("@authz.hasPermission(#wish.wishlistId,authentication)")
    public long addWish(Wish wish) {
        return wishRepository.addWish(wish);
    }
    @PreAuthorize("@authz.hasPermission(#id,authentication)")
    public Wish getWish(long id) {
        return wishRepository.getWish(id);
    }

    @PreAuthorize("@authz.hasPermission(#id,authentication)")
    public boolean deleteWish(long id) {
        return wishRepository.deleteWish(id);
    }

    @PreAuthorize("@authz.hasPermission(#wish.wishId,authentication)")
    public boolean editWish(Wish wish) {
        return wishRepository.editWish(wish);
    }

    public boolean deleteSelecetedWishes(List<Integer> ids) {
        return wishRepository.deleteSelectedWishes(ids);
    }

    @PreAuthorize("@authz.hasPermission(#wishId,authentication)")
    public Wish getWishFromWishId(long wishId) {
        return wishRepository.getWish(wishId);
    }


}
