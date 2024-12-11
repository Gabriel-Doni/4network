package com.teste._network.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "image")
@Entity(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idImage;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    private byte[] photo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "image")
    @JsonProperty(access = Access.WRITE_ONLY)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "image")
    @JsonProperty(access = Access.WRITE_ONLY)
    private Product product;

}
