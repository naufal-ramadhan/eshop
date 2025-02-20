package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;
import java.util.NoSuchElementException;

public interface ProductService {
    Product create(Product product) throws IllegalArgumentException;
    Product update(Product product) throws IllegalStateException, NoSuchElementException;
    Product delete(Product product) throws NoSuchElementException;
    Product findById(String productId) ;
    List<Product> findAll();
}