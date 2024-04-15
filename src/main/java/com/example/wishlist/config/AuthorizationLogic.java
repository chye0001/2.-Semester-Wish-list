package com.example.wishlist.config;

import com.example.wishlist.repository.WishlistJDBC;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationLogic {

    private WishlistJDBC wishlistJDBC;

    public AuthorizationLogic(WishlistJDBC wishlistJDBC) {
        this.wishlistJDBC = wishlistJDBC;
    }


    public boolean hasPermission(long id,String username) {
        return wishlistJDBC.checkIdAndUsernameMatches(id,username);
    }
}
