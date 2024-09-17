package br.com.xgommiapi.dto;

import jakarta.validation.constraints.NotBlank;

public record GommiUserSimpleRequestDTO (
        Long idGommiUser,
        String login,
        String email,
        String name,
        String biography
){
}
