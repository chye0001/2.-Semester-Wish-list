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
        long wishlistId = -1;

        try (Connection connection = dataSource.getConnection()) {
            String createWishlist = "INSERT INTO wishlist(name, picture, username) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(createWishlist, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, wishlistTitle);
            pstmt.setString(2, pictureLink);
            pstmt.setString(3, username);

            pstmt.executeUpdate();
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
    public List<Wish> getWishes(long wishlistId) {
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getWishesOnWishlistName = """
                    SELECT wish.name, wish.description, wish.link, wish.price, wish.picture, wish.reserved
                    FROM wishlist
                    JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                    WHERE wishlist.wishlist_id = ?
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getWishesOnWishlistName);
            pstmt.setLong(1, wishlistId);

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
        Wishlist newWishlist = null;
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

                newWishlist = new Wishlist();
                newWishlist.setWishlistId(wishlistId);
                newWishlist.setName(rs.getString("wishlist.name"));
                newWishlist.setPicture(wishListPicture);

                wishes = new ArrayList<>();
                wishlists.add(newWishlist);
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
    public boolean addWish(Wish newWish) {
        boolean isAdded = false;

        try (Connection connection = dataSource.getConnection()) {
            String insertNewWish = "INSERT INTO wish (name, description, link, price, picture, wishlist_id) VALUES (?, ? ,? ,? ,? ,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertNewWish);
            pstmt.setLong(1, newWish.getWishlistId());
            pstmt.setString(2, newWish.getName());
            pstmt.setString(3, newWish.getDescription());
            pstmt.setString(4, newWish.getLink());
            pstmt.setDouble(5, newWish.getPrice());
            pstmt.setString(6, newWish.getPicture());
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

    public boolean checkIdAndUsernameMatches(long id,String username) {
        String SQL = """
                SELECT wishlist.*, wish.*
                FROM wishlist
                LEFT JOIN wish ON wish.wishlist_id = wish.wishlist_id
                WHERE wishlist.username = ?
                AND (wish.wish_id = ? OR wishlist.wishlist_id = ?)
                """;
        try(Connection con = dataSource.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setLong(2, id);
            pstmt.setLong(3, id);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    @Override
    public boolean editWish(long wishId, Wish editedWish) {
        boolean isEdited = false;

        try (Connection connection = dataSource.getConnection()){
            String editWish = "UPDATE wish SET name = ?, description = ?, link = ?, price = ?, picture = ? " +
                    "WHERE wish_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(editWish);
            preparedStatement.setLong(6 , wishId);
            preparedStatement.setString(1, editedWish.getName());
            preparedStatement.setString(2, editedWish.getDescription());
            preparedStatement.setString(3, editedWish.getLink());
            preparedStatement.setDouble(4, editedWish.getPrice());
            preparedStatement.setString(5, editedWish.getPicture());
            int affectedRows = preparedStatement.executeUpdate();
            isEdited = affectedRows > 0;

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isEdited;
    }
}
