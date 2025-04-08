package com.example.Demo_E_Commerce.service;

import com.example.Demo_E_Commerce.model.product.Product;
import com.example.Demo_E_Commerce.model.product.ProductDto;
import com.example.Demo_E_Commerce.repository.CategoryRepository;
import com.example.Demo_E_Commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository , CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.findById(productDto.getCategory_id())
                .orElseThrow(()-> new RuntimeException("Category not found")));
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    public void deleteProduct(int id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product Not Found"));
        product.getCategory().getProducts().remove(product);
        productRepository.deleteById(id);
    }
}