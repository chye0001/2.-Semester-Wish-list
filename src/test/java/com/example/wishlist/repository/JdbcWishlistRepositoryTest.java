package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JdbcWishlistRepositoryTest {

    @Autowired
    JdbcWishlistRepository wishlistJDBC;

    @Autowired
    JdbcWishRepository wishRepositoryJDBC;

    @Test
    void createWishlist() {
        boolean expectedResult = true;
        boolean actualResult = 0 < wishlistJDBC.createWishlist("test", "picturelink","test");
        assertEquals(expectedResult, actualResult);
    }





    @Test
    void getWishlistById() {
        int expectedLength = 1;
        long wishlistId = wishlistJDBC.createWishlist("test1", "picturelink","test");
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        testWish.setWishlistId(wishlistId);
        wishRepositoryJDBC.addWish(testWish);
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
        long wishId = wishRepositoryJDBC.addWish(testWish);

        boolean actualResult = wishRepositoryJDBC.deleteWish(wishId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteWishThatDoesNotExist() {
        boolean expectedResult = false;

        boolean actualResult = wishRepositoryJDBC.deleteWish(-1);

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
        long id = wishlistJDBC.createWishlist("test", "link","test");
        wish.setWishlistId(id);
        wishRepositoryJDBC.addWish(wish);

        String addedWishlistName = wishlistJDBC.getAllWishlists("test").get(0).getName();
        assertEquals("test", addedWishlistName);
    }

    @Test
    void editWish() {
        boolean expectedResult = true;

        wishlistJDBC.createWishlist("test", "picturelink","test");
        Wish editedWish = new Wish("name", "description", 2, "link", "picturelink");
        editedWish.setWishlistId(1);
        long wishId = wishRepositoryJDBC.addWish(editedWish);
        editedWish.setWishId(wishId);
        editedWish.setName("editedName");
        boolean actualResult = wishRepositoryJDBC.editWish(editedWish);

        assertEquals(expectedResult, actualResult);
    }

}