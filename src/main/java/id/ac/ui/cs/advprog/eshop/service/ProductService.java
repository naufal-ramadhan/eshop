package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;
import java.util.NoSuchElementException;

public interface ProductService {
    public Product create(Product product) throws IllegalArgumentException;
    public Product update(Product product) throws IllegalStateException, NoSuchElementException;
    public Product delete(Product product) throws NoSuchElementException;
    public  Product findById(String productId) ;
    public List<Product> findAll();
}