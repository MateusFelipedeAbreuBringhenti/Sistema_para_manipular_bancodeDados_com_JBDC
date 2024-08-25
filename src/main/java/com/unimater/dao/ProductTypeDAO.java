package com.unimater.dao;

import com.unimater.model.ProductType;

import java.sql.*;

public abstract class ProductTypeDAO extends GenericDAOImpl<ProductType> implements GenericDAO<ProductType> {    public ProductTypeDAO(Connection connection) {
        super(ProductType::new, connection, "product_type");
    }

    @Override
    public ProductType findById(int id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductType(rs.getInt("id"),
                            rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
        return null;
    }

    @Override
    public void upsert(ProductType productType) {
        if (productType == null) return;

        String query;
        if (productType.getId() == 0) {
            query = "INSERT INTO " + getTableName() + " (description) VALUES (?)";
        } else {
            query = "UPDATE " + getTableName() + " SET description = ? WHERE id = ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, productType.getDescription());
            if (productType.getId() != 0) {
                pstmt.setInt(2, productType.getId());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
    }

    private String getTableName() {
        return "";
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
    }
}
