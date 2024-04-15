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
        boolean actualResult = wishlistJDBC.createWishlist("test", "picturelink","test");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addWish() {
        boolean expectedResult = true;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        wishlistJDBC.createWishlist("test", "picturelink","test");

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
        wishlistJDBC.createWishlist("test1", "picturelink","test");
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

        int numberOfWishlistsBeforeAdd = wishlistJDBC.getAllWishlists("test").size();
        wishlistJDBC.createWishlist("test1", "link1","test");
        wishlistJDBC.createWishlist("test2", "link2","test");
        int numberOfWishlistsAfterAdd = wishlistJDBC.getAllWishlists("test").size();

        int expectedResult = numberOfWishlistsBeforeAdd + 2;
        int actualResult = numberOfWishlistsAfterAdd;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllWishlistsData() {
        Wish wish = new Wish("item","desc",20,"link","pic-link");
        wishlistJDBC.createWishlist("test", "link","test");
        wishlistJDBC.addWish(wish, "test");

        String addedWishlistName = wishlistJDBC.getAllWishlists("test").get(0).getName();
        assertEquals("test", addedWishlistName);

//        String addedWishlistsWishName = wishlistJDBC.getAllWishlists("test").get(0).getWishes().get(0).getName();
//        assertEquals("item", addedWishlistsWishName);
    }
}