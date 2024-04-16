package com.example.wishlist.config;

import com.example.wishlist.repository.JdbcWishlistRepository;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationLogic {

    private WishlistRepository wishlistRepository;

    public AuthorizationLogic(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public boolean hasPermission(long id, Authentication authentication) {
        String username = authentication.getName();
        return wishlistRepository.checkIdAndUsernameMatches(id,username);
    }
}
