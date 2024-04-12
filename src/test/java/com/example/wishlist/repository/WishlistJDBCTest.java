package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
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

    @Test
    void checkThatTheCorrectWishDataGetsAdded() {
        String expectedName = "name";
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        wishlistJDBC.createWishlist("test", "picturelink");
        wishlistJDBC.addWish(testWish, "test");

        String actualName = wishlistJDBC.getWishes("test").get(0).getName();

        assertEquals(expectedName, actualName);
    }

}