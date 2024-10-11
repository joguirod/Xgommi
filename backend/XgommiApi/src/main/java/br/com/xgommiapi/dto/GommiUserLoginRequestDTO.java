package br.com.xgommiapi.dto;

public record GommiUserLoginRequestDTO(
        String login,
        String password
){
}