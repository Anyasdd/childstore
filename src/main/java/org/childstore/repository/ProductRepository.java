package org.childstore.repository;

import org.childstore.db.DatabaseManager;
import org.childstore.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public void save(Product product) {
        String sql = "INSERT INTO product (name, serial_number, price, quantity, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSerialNumber());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getCategory());

            stmt.executeUpdate();
            System.out.println("Товар добавлен в базу данных.");

        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении товара: " + e.getMessage());
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("serial_number"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка товаров: " + e.getMessage());
        }

        return products;
    }

    public List<Product> findByName(String name) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER(?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("serial_number"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                results.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка поиска по имени: " + e.getMessage());
        }

        return results;
    }

    public List<Product> findByCategory(String category) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE LOWER(category) LIKE LOWER(?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + category + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("serial_number"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                results.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка поиска по категории: " + e.getMessage());
        }

        return results;
    }

}
