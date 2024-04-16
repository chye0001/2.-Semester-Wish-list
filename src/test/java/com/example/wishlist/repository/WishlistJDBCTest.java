package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WishlistJDBCTest {

    @Autowired
    WishlistJDBC wishlistJDBC;
    @Test
    void createWishlist() {
        boolean expectedResult = true;
        boolean actualResult = 0 < wishlistJDBC.createWishlist("test", "picturelink","test");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addWish() {
        boolean expectedResult = true;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
//        testWish.setWishlistId(1);
        long wishlistId = wishlistJDBC.createWishlist("test", "picturelink","test");
        System.out.println("wishlistId in test: " + wishlistId);
        testWish.setWishlistId(wishlistId);
        long wishId = wishlistJDBC.addWish(testWish);
        System.out.println(wishId);
        boolean actualResult = wishId > -1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addWishToANoneExistingWishlist() {
        boolean expectedResult = false;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");

        boolean actualResult = 0 < wishlistJDBC.addWish(testWish);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getWishlistById() {
        int expectedLength = 1;
        long wishlistId = wishlistJDBC.createWishlist("test1", "picturelink","test");
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        testWish.setWishlistId(wishlistId);
        wishlistJDBC.addWish(testWish);
        int actualLength = wishlistJDBC.getWishlistById(wishlistId).getWishes().size();

        assertEquals(expectedLength, actualLength);
    }

    @Test
    void getWishesFromNotExistingWishlist() {
        int expectedLength = 0;

        int actualLength = wishlistJDBC.getWishlistById(-1).getWishes().size();

        assertEquals(expectedLength, actualLength);
    }

    @Test
    void deleteWishFromWishlistOnWishId() {
        boolean expectedResult = true;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        long wishlistId = wishlistJDBC.createWishlist("test1", "picturelink", "test");
        testWish.setWishlistId(wishlistId);
        long wishId = wishlistJDBC.addWish(testWish);

        boolean actualResult = wishlistJDBC.deleteWish(wishId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteWishThatDoesNotExist() {
        boolean expectedResult = false;

        boolean actualResult = wishlistJDBC.deleteWish(-1);

        assertEquals(expectedResult, actualResult);
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
        wishlistJDBC.addWish(wish);

        String addedWishlistName = wishlistJDBC.getAllWishlists("test").get(0).getName();
        assertEquals("test", addedWishlistName);
    }

    @Test
    void editWish() {
        boolean expectedResult = true;

        wishlistJDBC.createWishlist("test", "picturelink","test");
        Wish editedWish = new Wish("name", "description", 2, "link", "picturelink");
        editedWish.setWishlistId(1);
        long wishId = wishlistJDBC.addWish(editedWish);
        editedWish.setWishId(wishId);
        editedWish.setName("editedName");
        boolean actualResult = wishlistJDBC.editWish(editedWish);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getWishFromWishId() {
        long expectedWishId = 1;

        Wish returnedWish = wishlistJDBC.getWishFromWishId(1);
        long actualWishId = returnedWish.getWishId();

        assertEquals(expectedWishId, actualWishId);
    }
}