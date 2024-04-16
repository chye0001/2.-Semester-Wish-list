package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
//        testWish.setWishlistId(1);
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
        boolean expectedResult = false;
        Wish testWish = new Wish("name", "description", 2, "link", "picturelink");

        boolean actualResult = 0 < jdbcWishRepository.addWish(testWish);

        assertEquals(expectedResult, actualResult);
    }
}
