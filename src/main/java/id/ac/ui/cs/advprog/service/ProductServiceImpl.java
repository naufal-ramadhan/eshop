package id.ac.ui.cs.advprog.service;

import id.ac.ui.cs.advprog.model.Product;
import id.ac.ui.cs.advprog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productRepository.create(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        return product;
    }

    @Override
    public Product findById(String Id){
        return productRepository.findById(Id);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}