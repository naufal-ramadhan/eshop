package id.ac.ui.cs.advprog.eshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private Car car;

    @MockitoBean
    private CarServiceImpl carService;

    @Autowired
    private ObjectMapper objectMapper; // Untuk konversi Java Object ke JSON

    @Test
    void testListProductGet() throws Exception {
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"));
    }

    @Test
    void testCreateProductGet() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"));
    }

    @Test
    public void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");
        product.setProductName("Product A");
        product.setProductQuantity(-1);

        // Mock
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:list"));
    }

    @Test
    public void testCreateProductPostWithNegativeQuantity() throws Exception {
        Product product = new Product();
        product.setProductName("Product A");
        product.setProductQuantity(-1);

        // Mock
        doThrow(new IllegalStateException("Product quantity cannot be less than 0"))
                .when(productService).create(any(Product.class));

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attribute("error", "Quantity Cannot be Negative or Name cannot be empty"));
    }

    @Test
    void testUpdateProductGet() throws Exception {
        when(productService.findById(anyString())).thenReturn(new Product());

        String productId = "3893edc6-47bf-46f9-b127-6cc993acd374";

        mockMvc.perform(get("/product/update/{Id}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProduct"));
    }

    @Test
    void testUpdateProductPost() throws Exception {
        Product product = new Product();
        product.setProductName("Product A");
        product.setProductQuantity(-1);
        product.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");

        mockMvc.perform(post("/product/update")
                    .flashAttr("product", product))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:list"));
    }

    @Test
    void testUpdateProductPostNotFound() throws Exception {
        Product product = new Product();
        product.setProductName("Product A");
        product.setProductQuantity(-1);
        product.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");

        doThrow(NoSuchElementException.class).when(productService).update(any(Product.class));

        mockMvc.perform(post("/product/update")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProduct"));
    }

    @Test
    void testUpdateProductPostNotValid() throws Exception {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(-1);
        product.setProductId("3893edc6-47bf-46f9-b127-6cc993acd374");

        doThrow(IllegalStateException.class).when(productService).update(any(Product.class));

        mockMvc.perform(post("/product/update")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProduct"));
    }

    @Test
    void testDeleteProductPost() throws Exception {
        mockMvc.perform(post("/product/delete")
                        .param("productId", "3893edc6-47bf-46f9-b127-6cc993acd374"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:list"));
    }

    @Test
    void testDeleteProductPostNotFound() throws Exception {
        when(productService.findById(anyString())).thenReturn(new Product());
        doThrow(NoSuchElementException.class).when(productService).delete(any(Product.class));

        mockMvc.perform(post("/product/delete")
                        .param("productId", "3893edc6-47bf-46f9-b127-6cc993acd374"))
                .andExpect(status().isFound()) // Ensure the status is a redirect (HTTP 302)
                .andExpect(view().name("redirect:list")); // Ensure the redirection goes to "list"

        verify(productService, times(1)).delete(any(Product.class));
        verify(productService).delete(any(Product.class));
    }

}
