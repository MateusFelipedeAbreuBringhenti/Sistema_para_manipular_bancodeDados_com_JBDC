package com.unimater.dao;

import com.unimater.model.Entity;
import com.unimater.model.ProductType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    protected Connection connection;
    private final Supplier<T> supplier;
    private final String tableName;

    public GenericDAOImpl(Supplier<T> supplier, Connection connection, String tableName) {
        this.supplier = supplier;
        this.connection = connection;
        this.tableName = tableName;
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                T entity = supplier.get();
                entity.constructFromResultSet(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
        return entities;
    }

    @Override
    public T findById(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T entity = supplier.get();
                    entity.constructFromResultSet(rs);
                    return entity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
        return null;
    }

    @Override
    public void upsert(T entity) {
        if (entity == null) return;

        String query = "INSERT INTO " + tableName + " (columns) VALUES (?) " +
                "ON DUPLICATE KEY UPDATE column1 = VALUES(column1), column2 = VALUES(column2)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setStatementParameters(stmt, entity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua por um mecanismo de logging apropriado
        }
    }

    protected abstract void setStatementParameters(PreparedStatement stmt, T entity) throws SQLException;
}