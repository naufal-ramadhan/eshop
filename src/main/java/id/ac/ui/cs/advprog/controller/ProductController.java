package id.ac.ui.cs.advprog.controller;

import id.ac.ui.cs.advprog.model.Product;
import id.ac.ui.cs.advprog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/update/{Id}")
    public String updateProductGet(@PathVariable String Id, Model model) {
        Product product = service.findById(Id);
        model.addAttribute("product", product);
        return "updateProduct";
    }

    @PostMapping("/update")
    public String updateProductPost(@ModelAttribute Product updatedProduct, Model model) {
        Product product = service.findById(updatedProduct.getProductId());
        product.setProductName(updatedProduct.getProductName());
        product.setProductQuantity(updatedProduct.getProductQuantity());
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
}