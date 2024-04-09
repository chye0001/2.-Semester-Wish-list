package com.example.wishlist.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class WishlistJDBCTest {

    @Autowired
    WishlistJDBC wishlistJDBC;

    @Test
    void createWishlist() {
        boolean expectedResult = true;
        boolean actualResult = wishlistJDBC.createWishlist("test", "picturelink");
        assertEquals(expectedResult, actualResult);
    }

}