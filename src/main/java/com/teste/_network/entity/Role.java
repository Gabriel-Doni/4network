package com.teste._network.entity;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"),
    CLIENT("client");

    private String role;

    Role(String role) {
        this.role = role;
    }

}
