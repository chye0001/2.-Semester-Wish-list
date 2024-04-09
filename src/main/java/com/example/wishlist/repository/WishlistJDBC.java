package com.example.wishlist.repository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
