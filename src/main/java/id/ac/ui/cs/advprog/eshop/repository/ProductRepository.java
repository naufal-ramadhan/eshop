package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(final Product product) {
        productData.add(product);
        return product;
    }

    public Product findById(final String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id)).findFirst().orElse(null);
    }

    public Boolean existById(final String id) {
        return productData.stream().anyMatch(product -> product.getProductId().equals(id));
    }

    public Product update(final Product updatedProduct) {
        return updatedProduct;
    }

    public Product delete(final Product product) {
        productData.remove(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
