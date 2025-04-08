package com.example.Demo_E_Commerce.controller;

import com.example.Demo_E_Commerce.model.Cart;
import com.example.Demo_E_Commerce.model.product.Product;
import com.example.Demo_E_Commerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<?> addProductToCart(@PathVariable int product_id) {
        Cart cart = cartService.addProductToCart(product_id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<?> getCartProducts() {
        List<Product> productList = cartService.getCartProducts();
        return ResponseEntity.ok()
                .body(Map.of("Products",productList
                        , "Total_Price",cartService.calculateTotalPrice(productList)));
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable int product_id) {
        cartService.removeProductFromCart(product_id);
        return ResponseEntity.ok("Product removed from cart");
    }
}
