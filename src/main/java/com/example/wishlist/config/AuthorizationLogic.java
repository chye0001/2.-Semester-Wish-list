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

    //important check, since in theory a logged-in user could manipulate a request with another id,
    //and therefor, create/delete wish/wishlist for another user.
    //this checks makes sure, that the given id is 'owned' by the logged-in user.
    public boolean hasPermission(long id, Authentication authentication) {
        System.out.println("#### HAS PERMISSION ####");
        String username = authentication.getName();
        return wishlistRepository.checkIdAndUsernameMatches(id,username);
    }
}
