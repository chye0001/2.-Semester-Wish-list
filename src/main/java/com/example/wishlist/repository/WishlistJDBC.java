package com.example.wishlist.repository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Repository
public class WishlistJDBC implements CRUDOperations {

    private DataSource dataSource;

    public WishlistJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void createWishlist(){
//        try {
//            String createWishlist = ""
//            PreparedStatement pstmt = dataSource.getConnection();
//            pstmt.execute();
//        }
    }
}
