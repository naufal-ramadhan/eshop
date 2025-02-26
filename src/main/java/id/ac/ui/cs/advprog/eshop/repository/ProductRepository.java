package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductRepository {
    Product create(Product product);
    Product findById(String productId);
    Boolean existById(String productId);
    Product update(Product product);
    Product delete(Product product);
    Iterator<Product> findAll();
}
