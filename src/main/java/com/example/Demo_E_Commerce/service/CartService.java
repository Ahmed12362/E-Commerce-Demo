package com.example.Demo_E_Commerce.service;

import com.example.Demo_E_Commerce.model.Cart;
import com.example.Demo_E_Commerce.model.Role;
import com.example.Demo_E_Commerce.model.Users;
import com.example.Demo_E_Commerce.model.product.Product;
import com.example.Demo_E_Commerce.repository.CartRepository;
import com.example.Demo_E_Commerce.repository.ProductRepository;
import com.example.Demo_E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    @Autowired
    CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public Cart addProductToCart(int product_id) {
        Users user = getCurrentUser();
        Cart cart = cartRepository.findByUsers_Id(user.getId());

        if (user.getRole() != Role.USER) {
            throw new RuntimeException("Admins are not allowed to have carts");
        }
        if (cart == null) {
            cart = new Cart();
            cart.setUsers(userRepository
                    .findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found")));
            user.setCart(cart);
        }
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProductList().add(product);
        cart.setTotalPrice(calculateTotalPrice(cart.getProductList()));
        return cartRepository.save(cart);
    }

    public List<Product> getCartProducts() {
        Users user = getCurrentUser();
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        return cart.getProductList();
    }

    public void removeProductFromCart( int product_id) {
        Users user = getCurrentUser();
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProductList().removeIf(p -> p.getId() == product_id);
        cart.setTotalPrice(calculateTotalPrice(cart.getProductList()));
        cartRepository.save(cart);
    }

    private Users getCurrentUser() {
        return (Users) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public float calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(0f, Float::sum);
    }
}
