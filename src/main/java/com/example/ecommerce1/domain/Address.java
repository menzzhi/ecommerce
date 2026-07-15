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

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
