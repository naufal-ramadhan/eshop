package id.ac.ui.cs.advprog.service;

import id.ac.ui.cs.advprog.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
}