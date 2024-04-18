package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JdbcWishRepositoryTest {

    @Autowired
    JdbcWishRepository jdbcWishRepository;

    @Autowired
    JdbcWishlistRepository jdbcWishlistRepository;

    @Test
    void addWish() {
        boolean expectedResult = true;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        long wishlistId = jdbcWishlistRepository.createWishlist("test", "picturelink","test");
        System.out.println("wishlistId in test: " + wishlistId);
        testWish.setWishlistId(wishlistId);
        long wishId = jdbcWishRepository.addWish(testWish);
        System.out.println(wishId);
        boolean actualResult = wishId > -1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addWishToANoneExistingWishlist() {
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");
        assertThrows(RuntimeException.class, () -> jdbcWishRepository.addWish(testWish));
    }

     @Test
    void getWishFromWishId() {
        long expectedWishId = 1;

        Wish returnedWish = jdbcWishRepository.getWish(1);
        long actualWishId = returnedWish.getWishId();

        assertEquals(expectedWishId, actualWishId);
    }

    @Test
    void deleteMultipleWithNoSelected() {
        List<Long> emptyWishIdList = new ArrayList<>();

        boolean expectedResult = false;
        boolean actualResultEmpty = jdbcWishRepository.deleteSelectedWishes(emptyWishIdList);

        assertEquals(expectedResult, actualResultEmpty);
    }

    @Test
    void deleteMultipleWishes() {
        long wishlistId = jdbcWishlistRepository.createWishlist("testTitle111", "picLink111","test");

        Wish wish1 = new Wish("testName1", "testDescription1", 1, "link1", "picturelink1");
        Wish wish2 = new Wish("testName2", "testDescription2", 2, "link2", "picturelink2");
        Wish wish3 = new Wish("testName3", "testDescription3", 3, "link3", "picturelink3");

        wish1.setWishlistId(wishlistId);
        wish2.setWishlistId(wishlistId);
        wish3.setWishlistId(wishlistId);

        Long wish1Id = jdbcWishRepository.addWish(wish1);
        Long wish2Id = jdbcWishRepository.addWish(wish2);
        Long wish3Id = jdbcWishRepository.addWish(wish3);

        List<Long> selectedWishesIds = List.of(wish1Id,wish3Id);

        boolean expectedResult = true;
        boolean actualResult = jdbcWishRepository.deleteSelectedWishes(selectedWishesIds);
        assertEquals(expectedResult, actualResult);

        int expectedWishesSize = 1;
        int actualWishesSize = jdbcWishlistRepository.getWishlistById(wishlistId).getWishes().size();
        assertEquals(expectedWishesSize, actualWishesSize);


    }



}
