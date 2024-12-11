package com.teste._network.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseProductDTO {

        private Long idProduct;
        private UUID clientId;
        private String name;
        private float price;
        private String photo;
        private LocalDateTime createdAt;
}
