package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Cart;
import com.example.ecommerce1.domain.Order;
import com.example.ecommerce1.domain.OrderItem;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.OrderItemResponse;
import com.example.ecommerce1.dto.OrderResponse;
import com.example.ecommerce1.repository.CartRepository;
import com.example.ecommerce1.repository.OrderItemRepository;
import com.example.ecommerce1.repository.OrderRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(UserRepository userRepository, OrderItemRepository orderItemRepository,
                        OrderRepository orderRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public OrderResponse finishOrder(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Cart cart = user.getCart();

        AtomicReference<Double> precoTotal = new AtomicReference<>();
        precoTotal.set(0.0);

        cart.getCartItem().forEach(c -> precoTotal.set(precoTotal.get() + c.getPreco().doubleValue()));

        List<OrderItem> orderItems = cart.getCartItem().stream().map(
                c -> new OrderItem(
                        null,
                        c.getProduct(),
                        c.getQuantidade(),
                        c.getPreco())).toList();

        List<OrderItem> orderItemSaved = orderItems.stream().map(orderItemRepository::save).toList();

        Order order = new Order(
                Order.Status.CONFIRMADO,
                user,
                user.getAddress().get(0),
                BigDecimal.valueOf(precoTotal.get()),
                BigDecimal.valueOf(0.0),
                orderItemSaved);

        Order savedOrder = orderRepository.save(order);

        orderItemSaved.forEach(o -> o.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItemSaved);

        user.setCart(null);
        cartRepository.delete(cart);
        cartRepository.flush();

        List<OrderItemResponse> orderItemResponse = orderItemSaved.stream().map(
                o -> new OrderItemResponse(
                o.getProduct().getNome(),
                o.getQuantidade(),
                o.getPrecoTotal().doubleValue())).toList();

        return new OrderResponse(
                savedOrder.getStatus().status,
                savedOrder.getValorTotal().doubleValue(),
                savedOrder.getFrete().doubleValue(),
                LocalDateTime.now(),
                orderItemResponse);
    }

    public List<OrderResponse> getAll(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Order> order = user.getOrder();

        return order.stream().map(o -> new OrderResponse(
                o.getStatus().status,
                o.getValorTotal().doubleValue(),
                o.getFrete().doubleValue(),
                o.getRealizadoEm(),
                o.getOrderItem().stream().map(
                        orderItem -> new OrderItemResponse(
                                orderItem.getProduct().getNome(),
                                orderItem.getQuantidade(),
                                orderItem.getPrecoTotal().doubleValue())).toList()))
                .toList();
    }
}
