package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class JdbcWishRepository implements WishRepository {

    private DataSource dataSource;

    public JdbcWishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long addWish(Wish wish) {
        long addedWishId = -1;
        System.out.println("WISH ID IN ADDWISH JDBC " + wish.getWishId());
        System.out.println("WishlistId in JDBC: " + wish.getWishlistId());
        try (Connection connection = dataSource.getConnection()) {
            String insertNewWish = "INSERT INTO wish (wishlist_id, name, description, link, price, picture) VALUES (?, ? ,? ,? ,? ,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertNewWish, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, wish.getWishlistId());
            pstmt.setString(2, wish.getName());
            pstmt.setString(3, wish.getDescription());
            pstmt.setString(4, wish.getLink());
            pstmt.setDouble(5, wish.getPrice());
            pstmt.setString(6, wish.getPicture());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                addedWishId = generatedKeys.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addedWishId;
    }

    @Override
    public Wish getWish(long wishId) {
        Wish wish = null;

        try (Connection connection = dataSource.getConnection()) {
            String getWishOnId = """
                    SELECT * FROM wish
                    WHERE wish_id = ?;
                    """;

            PreparedStatement pstmt = connection.prepareStatement(getWishOnId);
            pstmt.setLong(1, wishId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                wish = new Wish(
                        rs.getLong("wishlist_id"),
                        rs.getInt("wish_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("link"),
                        rs.getString("picture"),
                        rs.getBoolean("reserved"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return wish;
    }

    @Override
    public boolean deleteWish(long wishId) {
        boolean isDeleted = false;

        try (Connection connection = dataSource.getConnection()) {
            String deleteWish = "DELETE FROM wish WHERE wish_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteWish);
            pstmt.setLong(1, wishId);
            int affectedRows = pstmt.executeUpdate();

            isDeleted = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isDeleted;
    }

    @Override
    public boolean deleteSelectedWishes(List<Integer> wishIdList) {
        boolean madeChanges = false;
        if (wishIdList.isEmpty()) {
            return madeChanges;
        }

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

    @Override
    public boolean editWish(Wish editedWish) {
        boolean isEdited = false;

        try (Connection connection = dataSource.getConnection()) {
            String editWish = "UPDATE wish SET name = ?, description = ?, link = ?, price = ?, picture = ? " +
                    "WHERE wish_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(editWish);
            preparedStatement.setLong(6, editedWish.getWishId());
            preparedStatement.setString(1, editedWish.getName());
            preparedStatement.setString(2, editedWish.getDescription());
            preparedStatement.setString(3, editedWish.getLink());
            preparedStatement.setDouble(4, editedWish.getPrice());
            preparedStatement.setString(5, editedWish.getPicture());
            int affectedRows = preparedStatement.executeUpdate();
            isEdited = affectedRows > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return isEdited;
    }

    @Override
    public void reserveWish(long wishId) {

        try (Connection connection = dataSource.getConnection()){
            boolean reserved = false;

            String getWishReserveStatus = "SELECT wish.reserved FROM wish WHERE wish_id = ?";
            PreparedStatement pstmtSelect = connection.prepareStatement(getWishReserveStatus);
            pstmtSelect.setLong(1, wishId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                reserved = rs.getBoolean("reserved");
            }

          updateReserveStatusOnWish(connection, wishId, reserved);

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private int updateReserveStatusOnWish(Connection connection, long wishId, boolean reserved) throws SQLException {
        String reserveWish = "UPDATE wish SET reserved = ? WHERE wish_id = ?";
        PreparedStatement pstmtUpdate = connection.prepareStatement(reserveWish);

        pstmtUpdate.setBoolean(1, !reserved);
        pstmtUpdate.setLong(2, wishId);

        return pstmtUpdate.executeUpdate();
    }
}