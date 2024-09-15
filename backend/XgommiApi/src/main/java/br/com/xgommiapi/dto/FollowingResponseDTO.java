package br.com.xgommiapi.dto;

import lombok.Builder;

@Builder
public record FollowingResponseDTO (
        Long idFollowRelation,
        GommiUserSimpleResponseDTO followed
){
}
