package com.teste._network.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthDTO(
        @NotEmpty(message = "Login inválido") @NotNull(message = "Login inválido") String name,
        @NotEmpty(message = "Senha inválida") @NotNull(message = "Senha inválida") String password) {

}
