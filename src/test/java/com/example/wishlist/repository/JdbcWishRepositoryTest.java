package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLIntegrityConstraintViolationException;

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
}
