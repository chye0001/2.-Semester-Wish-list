package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
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

    @Test
    void setWishlistToPublic() {
        boolean expectedResult = true;

        wishlistJDBC.setWishlistToPublic(1);
        Wishlist wishlist = wishlistJDBC.getWishlistById(1);
        boolean actualResult = wishlist.isPublic();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void tete() {
        long wishlist1Id = wishlistJDBC.createWishlist("testTitle123", "picLink123","test");
        Wish wish1 = new Wish("testName1", "testDescription1", 1, "link1", "picturelink1");
        Wish wish2 = new Wish("testName2", "testDescription2", 2, "link2", "picturelink2");
        Wish wish3 = new Wish("testName3", "testDescription3", 3, "link3", "picturelink3");
        wish1.setWishlistId(wishlist1Id);
        wish2.setWishlistId(wishlist1Id);
        wish3.setWishlistId(wishlist1Id);
        wishRepositoryJDBC.addWish(wish1);
        wishRepositoryJDBC.addWish(wish2);
        wishRepositoryJDBC.addWish(wish3);

        long wishlist2Id = wishlistJDBC.createWishlist("testTitle456", "picLink456","test");
        Wish wish4 = new Wish("testName4", "testDescription4", 4, "link4", "picturelink4");
        Wish wish5 = new Wish("testName5", "testDescription5", 5, "link5", "picturelink5");
        Wish wish6 = new Wish("testName6", "testDescription6", 6, "link6", "picturelink6");
        wish4.setWishlistId(wishlist2Id);
        wish5.setWishlistId(wishlist2Id);
        wish6.setWishlistId(wishlist2Id);
        wishRepositoryJDBC.addWish(wish4);
        wishRepositoryJDBC.addWish(wish5);
        wishRepositoryJDBC.addWish(wish6);

        long wishlist3Id = wishlistJDBC.createWishlist("testTitle789", "picLink789","test");
        Wish wish7 = new Wish("testName7", "testDescription7", 7, "link7", "picturelink7");
        Wish wish8 = new Wish("testName8", "testDescription8", 8, "link8", "picturelink8");
        Wish wish9 = new Wish("testName9", "testDescription9", 9, "link9", "picturelink9");
        wish7.setWishlistId(wishlist3Id);
        wish8.setWishlistId(wishlist3Id);
        wish9.setWishlistId(wishlist3Id);
        wishRepositoryJDBC.addWish(wish7);
        wishRepositoryJDBC.addWish(wish8);
        wishRepositoryJDBC.addWish(wish9);


        boolean expectedResult = true;
        boolean actualResult = wishlistJDBC.deleteAllUserWishlist("test");
        assertEquals(expectedResult, actualResult);

        int numberOfWishlistsAfterDeleteAll = wishlistJDBC.getAllWishlists("test").size();
        assertEquals(0, numberOfWishlistsAfterDeleteAll);

    }
}