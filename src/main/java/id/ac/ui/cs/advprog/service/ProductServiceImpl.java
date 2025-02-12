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
    public Product create(Product product) throws IllegalStateException{
        product.setProductId(UUID.randomUUID().toString());

        if (product.getProductQuantity() < 0 || product.getProductName().isEmpty()){
            throw new IllegalStateException("Product quantity cannot be less than 0");
        }

        productRepository.create(product);
        return product;
    }

    @Override
    public Product update(Product updatedProduct) throws IllegalStateException, NoSuchElementException{
        if (updatedProduct.getProductQuantity() < 0 || updatedProduct.getProductName().isEmpty()){
            throw new IllegalStateException("Product quantity cannot be less than 0");
        }

        Product product = productRepository.findById(updatedProduct.getProductId());
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }

        product.setProductQuantity(updatedProduct.getProductQuantity());
        product.setProductName(updatedProduct.getProductName());
        return productRepository.update(product);
    }

    @Override
    public Product delete(Product product) throws NoSuchElementException {
        if (productRepository.existById(product.getProductId()) == false){
            throw new NoSuchElementException("Product not found");
        }
        return productRepository.delete(product);
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