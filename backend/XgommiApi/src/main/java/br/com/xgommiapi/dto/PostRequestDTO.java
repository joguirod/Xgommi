package br.com.xgommiapi.dto;

import jakarta.validation.constraints.NotBlank;

public record PostRequestDTO(
    @NotBlank(message = "Post cannot be empty")
    String text,

    @NotBlank(message = "Author id is required")
    Long gommiUserId
){
}
