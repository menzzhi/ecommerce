package com.example.ecommerce1.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }


    public enum Values{
        ADMIN(1L),
        BASIC(2L);

        private Long roleId;

        Values(Long roleId) {
            this.roleId = roleId;
        }
    }
}
