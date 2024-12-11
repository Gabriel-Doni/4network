package com.teste._network.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterProductDTO(

                @NotBlank(message = "Nome do produto inválido") String name,
                @Min(value = 1, message = "Preço inválido") @NotNull(message = "Preço inválido") Float price) {
}
