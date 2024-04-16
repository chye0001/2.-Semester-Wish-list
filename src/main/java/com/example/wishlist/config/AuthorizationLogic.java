package com.example.wishlist.config;

import com.example.wishlist.repository.JdbcWishlistRepository;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationLogic {

    private WishlistRepository wishlistRepository;

    public AuthorizationLogic(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public boolean hasPermission(long id,String username) {
        return wishlistRepository.checkIdAndUsernameMatches(id,username);
    }
}
