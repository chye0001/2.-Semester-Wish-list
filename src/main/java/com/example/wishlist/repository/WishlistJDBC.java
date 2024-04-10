package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
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

    public WishlistJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public boolean createWishlist(String wishlistTitle, String pictureLink){
        int affectedRows = 0;
        
        try (Connection connection = dataSource.getConnection()){
            String createWishlist = "INSERT INTO wishlist (name, picture) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(createWishlist);
            pstmt.setString(1, wishlistTitle);
            pstmt.setString(2, pictureLink);

            affectedRows = pstmt.executeUpdate();

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return affectedRows > 0;
    }

    @Override
    public List<Wish> getWishes(String wishlistName) {
        List<Wish> wishes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
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

        while(wishesResultSet.next()) {
            Wish newWish = new Wish(
                    wishesResultSet.getString(1),
                    wishesResultSet.getString(2),
                    wishesResultSet.getDouble(4),
                    wishesResultSet.getString(3),
                    wishesResultSet.getString(5),
                    wishesResultSet.getBoolean(6));

            wishes.add(newWish);
        }


        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wishes;
    }

}
