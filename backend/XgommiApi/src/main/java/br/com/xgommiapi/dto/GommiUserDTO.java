package br.com.xgommiapi.dto;

import jakarta.validation.constraints.NotBlank;

public record GommiUserDTO(
    @NotBlank(message = "Login is required")
    String login,

    @NotBlank(message = "Password is required")
    String password,

    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Name is required")
    String name,

    String biography
) {
}
