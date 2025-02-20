package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.product = new Product();
        this.product.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");
        this.product.setProductName("motor");
        this.product.setProductQuantity(10);
    }

    @Test
    void createProduct() {
        Product createdProduct = productService.create(product);
        createdProduct.setProductName("mobil");
        createdProduct.setProductQuantity(20);

        Product product = productService.create(createdProduct);
        assertNotNull(product);
    }

    @Test
    void createProductNotValidQuantity() {
        Product createdProduct = productService.create(product);
        createdProduct.setProductName("mobil");
        createdProduct.setProductQuantity(-1);

        assertThrows(IllegalStateException.class, () ->{
            productService.create(createdProduct);
        });
        assertNotNull(product);
    }

    @Test
    void createProductNotValidName() {
        Product createdProduct = productService.create(product);
        createdProduct.setProductName("");
        createdProduct.setProductQuantity(100);

        assertThrows(IllegalStateException.class, () ->{
                    productService.create(createdProduct);
                });
        assertNotNull(product);
    }

    @Test
    void createProductNotValidNameAndQuantity() {
        Product createdProduct = productService.create(product);
        createdProduct.setProductName("");
        createdProduct.setProductQuantity(-1);

        assertThrows(IllegalStateException.class, () ->{
            productService.create(createdProduct);
        });
        assertNotNull(product);
    }

    /**
     * Test Update jika update ditemukan, tidak ditemukan, dan attribute tidak valid
     */
    @Test
    void updateProductTest() {
        // Inisialisasi Mock
        Product newProduct = new Product();
        newProduct.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");
        newProduct.setProductName("mobil");
        newProduct.setProductQuantity(20);
        Product newProductNotExist = new Product();
        newProductNotExist.setProductId("3aa241fc-128f-496b-b495-24407a692d54");
        newProductNotExist.setProductName("bajay");
        newProductNotExist.setProductQuantity(50);
        Product newProductNotValid = new Product();
        newProductNotValid.setProductId("3aa241fc-128f-496b-b495-24407a692d54");
        newProductNotValid.setProductName("motor");
        newProductNotValid.setProductQuantity(-1);
        Product newProductNotValid2 = new Product();
        newProductNotValid2.setProductId("3aa241fc-128f-496b-b495-24407a692d54");
        newProductNotValid2.setProductName("");
        newProductNotValid2.setProductQuantity(100);
        when(productRepository.update(product)).thenReturn(product);
        when(productRepository.create(product)).thenReturn(product);
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        // Simpan product
        product = productRepository.create(product);

        // Validasi
        assertNotNull(product);
        assertEquals("motor", product.getProductName());
        assertEquals(10, product.getProductQuantity());

        // Assert Update
        product = productService.update(newProduct);
        assertNotNull(product);
        assertEquals("mobil", product.getProductName());
        assertEquals(20, product.getProductQuantity());

        // Assert Update Not Found
        assertThrows(NoSuchElementException.class, () -> {
          productService.update(newProductNotExist);
        });
        assertNotNull(product);
        assertEquals("mobil", product.getProductName());
        assertEquals(20, product.getProductQuantity());

        // Assert Update Not Valid
        assertThrows(IllegalStateException.class, () -> {
            productService.update(newProductNotValid);
        });
        assertNotNull(product);
        assertEquals("mobil", product.getProductName());
        assertEquals(20, product.getProductQuantity());

        // Assert Update Not Valid
        assertThrows(IllegalStateException.class, () -> {
            productService.update(newProductNotValid2);
        });
        assertNotNull(product);
        assertEquals("mobil", product.getProductName());
        assertEquals(20, product.getProductQuantity());
    }

    @Test
    void deleteProductTestIfExist(){
        // Inisiasi
        when(productRepository.existById(product.getProductId())).thenReturn(true);
        when(productRepository.delete(product)).thenReturn(product);

        // Validasi Behaviour
        assertNotNull(product);
        assertEquals(true, productRepository.existById(product.getProductId()));
        assertEquals(product, productRepository.delete(product));

        // Assert Delete if Exist
        assertEquals(product, productService.delete(product));
    }

    @Test
    void deleteProductTestIfNotExist(){
        // Inisiasi
        when(productRepository.existById(product.getProductId())).thenReturn(false);

        // Validasi Behaviour
        assertNotNull(product);
        assertEquals(false, productRepository.existById(product.getProductId()));

        // Assert Delete if Not Exist
        assertThrows(NoSuchElementException.class, () -> {
            productService.delete(product);
        });
    }

    @Test
    void testFindByProductId(){
        // Inisiasi
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product newProduct = new Product();
        newProduct.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");
        newProduct.setProductName("mobil");
        newProduct.setProductQuantity(20);

        // Assert Delete if Not Exist
        Product product1 = productService.findById(product.getProductId());

        assertEquals(product1, product);
    }

    @Test
    void testFindAllProducts(){

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        // Inisiasi
        when(productRepository.findAll()).thenReturn(productList.iterator());

        // Assert Delete if Not Exist
        productList = productService.findAll();

        assertEquals(1, productList.size());
    }
}
