package com.example.ecommerce1.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Status status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private BigDecimal frete;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItem;

    @CreationTimestamp
    private LocalDateTime realizadoEm;

    public Order() {
    }

    public Order(Status status, User user, Address address, BigDecimal valorTotal, BigDecimal frete, List<OrderItem> orderItem) {
        this.status = status;
        this.user = user;
        this.address = address;
        this.valorTotal = valorTotal;
        this.frete = frete;
        this.orderItem = orderItem;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public LocalDateTime getRealizadoEm() {
        return realizadoEm;
    }

    public void setRealizadoEm(LocalDateTime realizado_em) {
        this.realizadoEm = realizado_em;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status{
        PENDENTE("Pendente"),
        CONFIRMADO("Confirmado"),
        ENVIADO("Enviado"),
        ENTREGUE("Entregue"),
        CANCELADO("Cancelado");

        public final String status;

        Status(String status) {
            this.status = status;
        }
    }

}

