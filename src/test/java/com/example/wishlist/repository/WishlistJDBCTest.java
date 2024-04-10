package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod")
class WishlistJDBCTest {

    @Autowired
    WishlistJDBC wishlistJDBC;

    @Test
    void createWishlist() {
        boolean expectedResult = true;
        boolean actualResult = wishlistJDBC.createWishlist("test", "picturelink");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addWish() {
        boolean expectedResult = true;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        wishlistJDBC.createWishlist("test", "picturelink");

        boolean actualResult = wishlistJDBC.addWish(testWish, "test");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getWishes() {
        int expectedLength = 1;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        wishlistJDBC.createWishlist("test1", "picturelink");
        wishlistJDBC.addWish(testWish, "test1");

        int actualLength = wishlistJDBC.getWishes("test1").size();

        assertEquals(expectedLength, actualLength);
    }
}