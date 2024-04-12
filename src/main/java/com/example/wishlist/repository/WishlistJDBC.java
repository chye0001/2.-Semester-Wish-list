package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistJDBC implements CRUDOperations {

    private DataSource dataSource;

    public WishlistJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createWishlist(String wishlistTitle, String pictureLink) {
        int affectedRows = 0;

        try (Connection connection = dataSource.getConnection()) {
            String createWishlist = "INSERT INTO wishlist (name, picture) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(createWishlist);
            pstmt.setString(1, wishlistTitle);
            pstmt.setString(2, pictureLink);

            affectedRows = pstmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return affectedRows > 0;
    }

    @Override
    public List<Wish> getWishes(String wishlistName) {
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getWishesOnWishlistName =
                    "SELECT " +
                            "w.name, " +
                            "w.description, " +
                            "w.link, " +
                            "w.price, " +
                            "w.picture, " +
                            "w.reserved " +
                            "FROM wishlist wl " +
                            "JOIN wish w " +
                            "ON wl.wishlist_id = w.wishlist_id " +
                            "WHERE wl.name = ?";

            PreparedStatement pstmt = connection.prepareStatement(getWishesOnWishlistName);
            pstmt.setString(1, wishlistName);

            ResultSet wishesResultSet = pstmt.executeQuery();

            while (wishesResultSet.next()) {
                Wish newWish = new Wish(
                        wishesResultSet.getString(1),
                        wishesResultSet.getString(2),
                        wishesResultSet.getDouble(4),
                        wishesResultSet.getString(3),
                        wishesResultSet.getString(5),
                        wishesResultSet.getBoolean(6));

                wishes.add(newWish);
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishes;
    }

    @Override
    public List<Wishlist> getAllWishlists() {

        List<Wishlist> wishlists = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getAllWishlists =
                    "SELECT * FROM wishlist_db.wishlist;";

            PreparedStatement pstmt = connection.prepareStatement(getAllWishlists);


            ResultSet wishesResultSet = pstmt.executeQuery();

            while (wishesResultSet.next()) {

                int wishlistId = wishesResultSet.getInt(1);
                String wishlistName = wishesResultSet.getString(2);
                String picture = wishesResultSet.getString(3);
                List<Wish> wishes = getWishes(wishlistName);

                Wishlist wishlist = new Wishlist(wishlistId, wishlistName, picture, wishes);
                wishlists.add(wishlist);
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishlists;

    }

    @Override
    public boolean addWish(Wish newWish, String wishlistName) {
        boolean isAdded = false;

        try (Connection connection = dataSource.getConnection()){
            String getWishlistIDOnWishlistName = "SELECT wishlist_id FROM wishlist WHERE name = ?";
            PreparedStatement pstmtGetWishlistID = connection.prepareStatement(getWishlistIDOnWishlistName);
            pstmtGetWishlistID.setString(1, wishlistName);
            ResultSet wishlistIDResultSet = pstmtGetWishlistID.executeQuery();
            wishlistIDResultSet.next();
            int wishlistID = wishlistIDResultSet.getInt(1);

            String insertNewWish = "INSERT INTO wish (name, description, link, price, picture, wishlist_id) VALUES (?, ? ,? ,? ,? ,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertNewWish);
            pstmt.setString(1, newWish.getName());
            pstmt.setString(2, newWish.getDescription());
            pstmt.setString(3, newWish.getLink());
            pstmt.setDouble(4, newWish.getPrice());
            pstmt.setString(5, newWish.getPicture());
            pstmt.setInt(6, wishlistID);
            int affectedRows = pstmt.executeUpdate();

            isAdded = affectedRows > 0;
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isAdded;
    }
}
