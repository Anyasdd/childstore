package org.childstore.service;

import org.childstore.model.Product;
import org.childstore.repository.ProductRepository;

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

}

