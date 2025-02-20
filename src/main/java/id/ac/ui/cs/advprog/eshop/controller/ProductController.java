package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
        try {
            service.create(product);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
            return "createProduct";
        }
        return "redirect:list";
    }

    @GetMapping("/update/{productId}")
    public String updateProductGet(@PathVariable String productId, Model model) {
        Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "updateProduct";
    }

    @PostMapping("/update")
    public String updateProductPost(@ModelAttribute Product updatedProduct, Model model) {
        try{
            service.update(updatedProduct);
        } catch (IllegalStateException e){
            model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
            return "updateProduct";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Product not found");
            return "updateProduct";
        }
        return "redirect:list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") String productId,Model model) {
        Product product = service.findById(productId);
        try{
            service.delete(product);
        } catch (NoSuchElementException e){
            model.addAttribute("error", "Product not found");
        }

        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
}