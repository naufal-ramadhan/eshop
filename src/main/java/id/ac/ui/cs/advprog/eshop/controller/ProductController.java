package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(final Model model) {
        final Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute final Product product, final Model model) {
        String viewName = "redirect:list";
        try {
            service.create(product);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
            viewName = "createProduct";
        }

        return viewName;  // Single exit point
    }

    @GetMapping("/update/{productId}")
    public String updateProductGet(@PathVariable final String productId, final Model model) {
        final Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "updateProduct";
    }

    @PostMapping("/update")
    public String updateProductPost(@ModelAttribute final Product updatedProduct, final Model model) {
        String viewName = "redirect:list";
        try{
            service.update(updatedProduct);
        } catch (IllegalStateException e){
            model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
            viewName = "updateProduct";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Product not found");
            viewName = "updateProduct";
        }
        return viewName;
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") final String productId, final Model model) {
        final Product product = service.findById(productId);
        try{
            service.delete(product);
        } catch (NoSuchElementException e){
            model.addAttribute("error", "Product not found");
        }

        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(final Model model){
        final List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
}
