package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Cart;
import com.example.ecommerce1.domain.CartItem;
import com.example.ecommerce1.domain.Product;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.CartItemResponse;
import com.example.ecommerce1.dto.CartResponse;
import com.example.ecommerce1.repository.CartItemRepository;
import com.example.ecommerce1.repository.CartRepository;
import com.example.ecommerce1.repository.ProductRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository,
                       ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void createCart(Long userId,
                           Long productId,
                           Integer quantity) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Cart cart = new Cart(user);

        cartRepository.save(cart);

        CartItem cartItem = new CartItem(cart, product, quantity, BigDecimal.valueOf(quantity * product.getPreco().doubleValue()));

        cartItemRepository.save(cartItem);
    }

    public CartResponse getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Cart cart = user.getCart();
        List<CartItemResponse> cartItemList = cart.getCartItem().stream().map(i -> new CartItemResponse(
                i.getProduct().getNome(),
                i.getQuantidade(),
                i.getPreco().doubleValue())).toList();

        AtomicReference<Double> precoTotal = new AtomicReference<>((double) 0);
        cart.getCartItem().forEach(c -> precoTotal.set(precoTotal.get() + c.getPreco().doubleValue()));


        return new CartResponse(cartItemList, precoTotal.get());
    }

    public void updateCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Cart cart = user.getCart();

        CartItem cartItem = new CartItem(cart, product, quantity, BigDecimal.valueOf(quantity * product.getPreco().doubleValue()));

        cartItemRepository.save(cartItem);

        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Cart cart = user.getCart();
        user.setCart(null);

        cartRepository.delete(cart);
        cartRepository.flush();
    }
}
