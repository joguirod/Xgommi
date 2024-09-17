package br.com.xgommiapi.dto;

import br.com.xgommiapi.domain.entity.Community;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GommiUserSimpleResponseDTO(
        Long idGommiUser,
        String login,
        String email,
        String name
){
}
