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
        testWish.setWishlistId(wishlistId);
        long wishId = jdbcWishRepository.addWish(testWish);
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

    //H2 får testen til at fejle? Virker når den bliver kørt isoleret
    @Test
    void undoReserveWish() {
        boolean expectedResult = false;

        jdbcWishRepository.reserveWish(1); // set reserve to true
        System.out.println(jdbcWishRepository.getWish(1).isReserved());

        jdbcWishRepository.reserveWish(1); // set reserve to false
        System.out.println(jdbcWishRepository.getWish(1).isReserved());

        Wish wish = jdbcWishRepository.getWish(1);
        boolean actualResult = wish.isReserved();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void reserveWish() {
        boolean expectedResult = true;

        jdbcWishRepository.reserveWish(1); // set reserve to true

        Wish wish = jdbcWishRepository.getWish(1);
        boolean actualResult = wish.isReserved();

        assertEquals(expectedResult, actualResult);
    }
}
