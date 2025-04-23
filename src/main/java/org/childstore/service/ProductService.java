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
}

