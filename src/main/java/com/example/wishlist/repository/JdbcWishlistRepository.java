package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcWishlistRepository implements WishlistRepository {

    private DataSource dataSource;

    public JdbcWishlistRepository(DataSource dataSource) {
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
    public Wishlist getWishlistById(long wishlistId) {
        Wishlist wishlist = null;
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getWishesOnWishlistName = """
                    SELECT wishlist.name as wl_name, wishlist.picture as wl_pic, wishlist.isPublic as wl_public, wish.name as w_name, wish.wish_id as w_id, wish.description as w_desc, wish.link as w_link, wish.price as w_price, wish.picture as w_pic, wish.reserved as w_res
                    FROM wishlist
                    LEFT JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                    WHERE wishlist.wishlist_id = ?
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getWishesOnWishlistName);
            pstmt.setLong(1, wishlistId);

            ResultSet wishesResultSet = pstmt.executeQuery();

            String wlName = null;
            String wlPic = null;
            boolean wlPublic = false;

            while (wishesResultSet.next()) {
                if (wlName == null) {
                    wlName = wishesResultSet.getString("wl_name");
                }
                if (wlPic == null) {
                    wlPic = wishesResultSet.getString("wl_pic");
                }
                if (!wlPublic) {
                    wlPublic = wishesResultSet.getBoolean("wl_public");
                }

                if (wishesResultSet.getString("w_name") != null) {
                    Wish newWish = new Wish(
                            wishesResultSet.getLong("w_id"),
                            wishesResultSet.getString("w_name"),
                            wishesResultSet.getString("w_desc"),
                            wishesResultSet.getDouble("w_price"),
                            wishesResultSet.getString("w_link"),
                            wishesResultSet.getString("w_pic"),
                            wishesResultSet.getBoolean("w_res"));

                    wishes.add(newWish);
                }
            }
            wishlist = new Wishlist(wishlistId, wlName, wlPic, wlPublic, wishes);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishlist;
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

    @Override
    public boolean editWishlist(Wishlist wishlist) {
        boolean isEdited = false;

        try (Connection connection = dataSource.getConnection()) {
            String editWishlist = "UPDATE wishlist SET name = ?, picture = ? WHERE wishlist_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(editWishlist);
            preparedStatement.setLong(3, wishlist.getWishlistId());
            preparedStatement.setString(1, wishlist.getName());
            preparedStatement.setString(2, wishlist.getPicture());

            int affectedRows = preparedStatement.executeUpdate();
            isEdited = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return isEdited;
    }

    @Override
    public boolean deleteWishlist(long wishlistId) {
        boolean isDeleted = false;

        try (Connection connection = dataSource.getConnection()) {
            String deleteWishlist = "DELETE FROM wish WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlist);
            pstmt.setLong(1, wishlistId);
            pstmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection()) {
            String deleteWishlist = "DELETE FROM wishlist WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlist);
            pstmt.setLong(1, wishlistId);
            int affectedRows = pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }

    @Override
    public boolean deleteAllWishes(long wishlistId) {
        boolean isDeleted = false;

        try (Connection connection = dataSource.getConnection()) {
            String deleteWishlist = "DELETE FROM wish WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlist);
            pstmt.setLong(1, wishlistId);
            int affectedRows = pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }


    @Override
    public boolean checkIdAndUsernameMatches(long id, String username) {
        String SQL = """
                SELECT wishlist.*, wish.*
                FROM wishlist
                LEFT JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                WHERE wishlist.username = ?
                AND (wish.wish_id = ? OR wishlist.wishlist_id = ?)
                """;
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setLong(2, id);
            pstmt.setLong(3, id);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
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
            long wishId = rs.getLong("wish_id");
            boolean reserved = rs.getBoolean("reserved");

            if (current != wishlistId) {
                current = wishlistId;

                newWishlist = new Wishlist();
                newWishlist.setWishlistId(wishlistId);
                newWishlist.setName(rs.getString("wishlist.name"));
                newWishlist.setPicture(wishListPicture);
                newWishlist.setPublic(rs.getBoolean("wishlist.isPublic"));

                wishes = new ArrayList<>();
                wishlists.add(newWishlist);
                int price = rs.getInt("price");
                if (wishName != null) {
                    Wish newWish = new Wish(wishName, description, price, link, picture);
                    newWish.setReserved(reserved);
                    newWish.setWishId(wishId);
                    wishes.add(newWish);
                }
            }
            int price = rs.getInt("price");
            if (wishName != null) {
                Wish newWish = new Wish(wishName, description, price, link, picture);
                newWish.setReserved(reserved);
                newWish.setWishId(wishId);
                assert wishes != null;
                wishes.add(newWish);
            }
        }
        return wishlists;
    }

    @Override
    public boolean setWishlistToPublic(long wishlistId) {
        boolean isUpdated = false;
        try (Connection connection = dataSource.getConnection()) {
            String setWishlistToPublic = "UPDATE wishlist SET isPublic = 1 WHERE wishlist_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(setWishlistToPublic);
            pstmt.setLong(1, wishlistId);
            int affectedRows = pstmt.executeUpdate();

            System.out.println("affected rows: " + affectedRows);
            isUpdated = affectedRows > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAllUserWishlist(String username) {
        int affectedRows = 0;
        boolean isDeleted = false;
        List<Long> wishlistIdsToDelete = getWishlistIdsByUsername(username);


        try (Connection connection = dataSource.getConnection()) { //delete wishlist wishes
            String deleteWishlistWishes = "DELETE FROM wish WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlistWishes);
            for (Long wishlistId : wishlistIdsToDelete) {
                pstmt.setLong(1, wishlistId);
                affectedRows += pstmt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection()) { // delete users wishlists
            String deleteUserWishlist = "DELETE FROM wishlist WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteUserWishlist);
            pstmt.setString(1, username);
            affectedRows += pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }

    private List<Long> getWishlistIdsByUsername(String username) {
        List<Long> wishlistIds = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            String getUserWishlists = "SELECT wishlist_id FROM wishlist WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUserWishlists);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                wishlistIds.add(rs.getLong("wishlist_id"));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return wishlistIds;
    }
}
