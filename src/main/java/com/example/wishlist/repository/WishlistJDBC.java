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
    public Wishlist getWishlistById(long wishlistId) {
        Wishlist wishlist = null;
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String getWishesOnWishlistName = """
                    SELECT wishlist.name as wl_name, wishlist.picture as wl_pic, wish.name as w_name, wish.wish_id as w_id, wish.description as w_desc, wish.link as w_link, wish.price as w_price, wish.picture as w_pic, wish.reserved as w_res
                    FROM wishlist
                    JOIN wish ON wishlist.wishlist_id = wish.wishlist_id
                    WHERE wishlist.wishlist_id = ?
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getWishesOnWishlistName);
            pstmt.setLong(1, wishlistId);

            ResultSet wishesResultSet = pstmt.executeQuery();

            String wlName = null;
            String wlPic = null;

            while (wishesResultSet.next()) {
                if (wlName == null) {wlName = wishesResultSet.getString("wl_name");}
                if (wlPic == null) {wlPic = wishesResultSet.getString("wl_pic");}
                Wish newWish = new Wish(
                        wishesResultSet.getInt("w_id"),
                        wishesResultSet.getString("w_name"),
                        wishesResultSet.getString("w_desc"),
                        wishesResultSet.getDouble("w_price"),
                        wishesResultSet.getString("w_link"),
                        wishesResultSet.getString("w_pic"),
                        wishesResultSet.getBoolean("w_res"));
                wishes.add(newWish);
            }
            wishlist = new Wishlist(wishlistId, wlName, wlPic, wishes);

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
                wishlist.setWishlistId(wishlistId);
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
    public boolean addWish(Wish newWish) {
        boolean isAdded = false;

        try (Connection connection = dataSource.getConnection()) {
//            String getWishlistIDOnWishlistName = "SELECT wishlist_id FROM wishlist WHERE name = ?";
//            PreparedStatement pstmtGetWishlistID = connection.prepareStatement(getWishlistIDOnWishlistName);
//            pstmtGetWishlistID.setString(1, wishlistName);
//            ResultSet wishlistIDResultSet = pstmtGetWishlistID.executeQuery();
//            wishlistIDResultSet.next();
//            int wishlistID = wishlistIDResultSet.getInt(1);
            System.out.println("newWish.getWishlistId()" + newWish.getWishlistId());
            String insertNewWish = "INSERT INTO wish (wishlist_id, name, description, link, price, picture) VALUES (?, ? ,? ,? ,? ,?)";
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

    @Override
    public boolean deleteWishlist(int wishlistId) {
        boolean isDeleted = false;

        try (Connection connection = dataSource.getConnection()) {
            String deleteWishlist = "DELETE FROM wish WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlist);
            pstmt.setInt(1, wishlistId);
            pstmt.executeUpdate();

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection()) {
            String deleteWishlist = "DELETE FROM wishlist WHERE wishlist_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWishlist);
            pstmt.setInt(1, wishlistId);
            int affectedRows = pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }

    public boolean deleteSelectedWishes(List<Integer> wishIdList) {
        boolean madeChanges = false;
        if (wishIdList.isEmpty()) {return madeChanges;}

        String idsString = wishIdList.get(0).toString();
        for (int i = 1; i < wishIdList.size(); i++) {
            idsString += ("," + wishIdList.get(i).toString());
        }
        try (Connection connection = dataSource.getConnection()) {
            String deleteWish = "DELETE FROM wish WHERE wish_id IN (?)";
            PreparedStatement pstmt = connection.prepareStatement(deleteWish);
            pstmt.setString(1, idsString);

            madeChanges = pstmt.executeUpdate() > 0;


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return madeChanges;
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
}
