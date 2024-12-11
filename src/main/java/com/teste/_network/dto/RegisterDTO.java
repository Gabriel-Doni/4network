package com.teste._network.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(

                @CPF String cpf,
                @NotBlank(message = "Nome inválido") @NotNull(message = "Nome inválido") String name,
                @NotBlank(message = "Senha inválida") @NotNull(message = "Senha inválida") String password,
                @NotBlank(message = "CEP inválido") @NotNull(message = "CEP inválido") String cep,
                @NotBlank(message = "Estado inválido") @NotNull(message = "Estado inválido") String state,
                @NotBlank(message = "Cidade inválida") @NotNull(message = "Cidade inválida") String city,
                @NotBlank(message = "Rua inválida") @NotNull(message = "Rua inválida") String street,
                @Min(value = 1, message = "Senha inválida") @NotNull(message = "Número inválido") Integer number,
                String complement) {
}
