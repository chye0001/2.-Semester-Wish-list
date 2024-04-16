package com.example.wishlist.config;

import com.example.wishlist.repository.JdbcWishlistRepository;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationLogic {

    private JdbcWishlistRepository wishlistJDBC;

    public AuthorizationLogic(JdbcWishlistRepository wishlistJDBC) {
        this.wishlistJDBC = wishlistJDBC;
    }


    public boolean hasPermission(long id,String username) {
        return wishlistJDBC.checkIdAndUsernameMatches(id,username);
    }
}
