package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistJDBC implements CRUDOperations {

    private DataSource dataSource;

    public WishlistJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createWishlist(String wishlistTitle, String pictureLink, String username) {
        int affectedRows = 0;
        long wishlistId = -1;

        try (Connection connection = dataSource.getConnection()) {
            String createWishlist = "INSERT INTO wishlist(name, picture, username) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(createWishlist, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, wishlistTitle);
            pstmt.setString(2, pictureLink);
            pstmt.setString(3, username);

            affectedRows = pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                wishlistId = generatedKeys.getLong(1);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishlistId;
    }

    @Override
    public List<Wish> getWishes(long id) {
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getWishesOnWishlistName = """
                    SELECT wish.name, wish.description, wish.link, wish.price, wish.picture, wish.reserved
                    FROM wishlist
                    JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                    WHERE wishlist.wishlist_id = ?
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getWishesOnWishlistName);
            pstmt.setLong(1, id);

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
    public List<Wishlist> getAllWishlists(String username) {

        List<Wishlist> wishlists = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getAllWishlists = """
                    SELECT wishlist.*,wish.* FROM wishlist
                    LEFT JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                    WHERE wishlist.username = ?;
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getAllWishlists);
            pstmt.setString(1, username);


            ResultSet wishesResultSet = pstmt.executeQuery();

            wishlists = resultSetToWishlistList(wishesResultSet);


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishlists;

    }

    private List<Wishlist> resultSetToWishlistList(ResultSet rs) throws SQLException {
        List<Wishlist> wishlists = new ArrayList<>();
        int current = -1;
        Wishlist wishlist = null;
        List<Wish> wishes = null;
        while (rs.next()) {
            int wishlistId = rs.getInt("wishlist.wishlist_id");
            String wishListPicture = rs.getString("wishlist.picture");
            String wishName = rs.getString("wish.name");
            String picture = rs.getString("wish.picture");
            String description = rs.getString("description");
            String link = rs.getString("link");
            boolean reserved = rs.getBoolean("reserved");

            if (current != wishlistId) {
                current = wishlistId;
                wishlist = new Wishlist();
                wishlist.setName(rs.getString("wishlist.name"));
                wishlist.setPicture(wishListPicture);
                wishes = new ArrayList<>();
                wishlists.add(wishlist);
                int price = rs.getInt("price");
                if (wishName != null) {
                    Wish newWish = new Wish(wishName, description, price, link, picture);
                    newWish.setReserved(reserved);
                    wishes.add(newWish);
                }
            }
            int price = rs.getInt("price");
            if (wishName != null) {
                Wish newWish = new Wish(wishName, description, price, link, picture);
                newWish.setReserved(reserved);
                assert wishes != null;
                wishes.add(newWish);
            }
        }
        return wishlists;
    }


    @Override
    public boolean addWish(Wish newWish, String wishlistName) {
        boolean isAdded = false;

        try (Connection connection = dataSource.getConnection()) {
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isAdded;
    }

    @Override
    public boolean deleteWish(String wishName) {
        boolean isDeleted = false;

        try (Connection connection = dataSource.getConnection()) {
            String deleteWish = "DELETE FROM wish WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWish);
            pstmt.setString(1, wishName);
            int affectedRows = pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }
}
