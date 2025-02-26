package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    @Override
    public Product create(final Product product) throws IllegalStateException{
        product.setProductId(UUID.randomUUID().toString());

        if (product.getProductQuantity() < 0 || product.getProductName().isEmpty()){
            throw new IllegalStateException("Product quantity cannot be less than 0");
        }

        productRepositoryImpl.create(product);
        return product;
    }

    @Override
    public Product update(final Product updatedProduct) throws IllegalStateException, NoSuchElementException{
        if (updatedProduct.getProductQuantity() < 0 || updatedProduct.getProductName().isEmpty()){
            throw new IllegalStateException("Product quantity cannot be less than 0");
        }

        final Product product = productRepositoryImpl.findById(updatedProduct.getProductId());
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }

        product.setProductQuantity(updatedProduct.getProductQuantity());
        product.setProductName(updatedProduct.getProductName());
        return productRepositoryImpl.update(product);
    }

    @Override
    public Product delete(final Product product) throws NoSuchElementException {
        if (productRepositoryImpl.existById(product.getProductId()) == false){
            throw new NoSuchElementException("Product not found");
        }
        return productRepositoryImpl.delete(product);
    }

    @Override
    public Product findById(final String productId){
        return productRepositoryImpl.findById(productId);
    }

    @Override
    public List<Product> findAll() {
        final Iterator<Product> productIterator = productRepositoryImpl.findAll();
        final List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}