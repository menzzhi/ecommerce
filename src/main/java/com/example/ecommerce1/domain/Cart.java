package com.example.ecommerce1.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "tb_carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal precoTotalCarrinho;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItem;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    public Double pegarPrecoTotal(List<CartItem> cartItem){
        AtomicReference<Double> precoTotal = new AtomicReference<>(0.0);
        cartItem.stream().forEach(
                        cartItems -> precoTotal.set(precoTotal.get() + cartItems.getPreco().doubleValue()));
        this.precoTotalCarrinho = BigDecimal.valueOf(precoTotal.get());
        return precoTotal.get();
    }

    public BigDecimal getPrecoTotalCarrinho() {
        return precoTotalCarrinho;
    }

}
