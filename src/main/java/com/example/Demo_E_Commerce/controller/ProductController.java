package com.example.Demo_E_Commerce.controller;

import com.example.Demo_E_Commerce.model.product.Product;
import com.example.Demo_E_Commerce.model.product.ProductDto;
import com.example.Demo_E_Commerce.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping()
    public Product addProduct(@RequestBody ProductDto product){
        return productService.addProduct(product);
    }
    @GetMapping
    public List<Product> getAllProducts(){

        return productService.getAllProduct();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}
