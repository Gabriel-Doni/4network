package com.teste._network.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
public class Address {

    private String cep;

    private String state;

    private String city;

    private String street;

    private Integer number;

    private String complement;
}
