package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void addWishToANoneExistingWishlist() {
        boolean expectedResult = false;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");

        boolean actualResult = wishlistJDBC.addWish(testWish, "addingToWishlistThatDoesNotExist");

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

    @Test
    void getWishesFromNotExistingWishlist() {
        int expectedLength = 0;

        int actualLength = wishlistJDBC.getWishes("getWishesFromNotExistingWishlist").size();

        assertEquals(expectedLength, actualLength);
    }


    @Test
    void getAllWishlistsSize() {

        int numberOfWishlistsBeforeAdd = wishlistJDBC.getAllWishlists().size();
        wishlistJDBC.createWishlist("test1", "link1");
        wishlistJDBC.createWishlist("test2", "link2");
        int numberOfWishlistsAfterAdd = wishlistJDBC.getAllWishlists().size();

        int expectedResult = numberOfWishlistsBeforeAdd + 2;
        int actualResult = numberOfWishlistsAfterAdd;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllWishlistsData() {
        Wish wish = new Wish("item","desc",20,"link","pic-link");
        wishlistJDBC.createWishlist("test", "link");
        wishlistJDBC.addWish(wish, "test");

        String addedWishlistName = wishlistJDBC.getAllWishlists().get(0).getName();
        assertEquals("test", addedWishlistName);

        String addedWishlistsWishName = wishlistJDBC.getAllWishlists().get(0).getWishes().get(0).getName();
        assertEquals("item", addedWishlistsWishName);
    }
}