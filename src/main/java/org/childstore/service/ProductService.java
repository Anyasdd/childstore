package org.childstore.service;

import org.childstore.model.Product;
import org.childstore.repository.ProductRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.util.List;

public class ProductService {
    private final ProductRepository repository = new ProductRepository();

    public void addProduct(Product product) {
        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProductById(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = org.childstore.db.DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Товар удалён.");
            } else {
                System.out.println("Товар с таким ID не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    public List<Product> searchByName(String name) {
        return repository.findByName(name);
    }

    public List<Product> searchByCategory(String category) {
        return repository.findByCategory(category);
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, serial_number = ?, price = ?, quantity = ?, category = ? WHERE id = ?";
        try (Connection conn = org.childstore.db.DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSerialNumber());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getCategory());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении товара: " + e.getMessage());
        }
    }

    public void sellUpdate(String name, int quantity) {
        String sql = "UPDATE product SET quantity = quantity - ? WHERE name = ?";
        try (Connection conn = org.childstore.db.DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setString(2, name);

            stmt.executeUpdate();
            System.out.println("Test.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product searchBySerial(String serial) {
        return repository.findBySerial(serial);
    }


}

