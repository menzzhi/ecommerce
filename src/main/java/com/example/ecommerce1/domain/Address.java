package com.example.ecommerce1.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_enderecos")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long enderecoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String logradouro;

    private String cidade;

    private String cep;

    private String estado;

    public Address() {
    }

    public Address(User user, String logradouro, String cidade, String cep, String estado) {
        this.user = user;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
    }
}
