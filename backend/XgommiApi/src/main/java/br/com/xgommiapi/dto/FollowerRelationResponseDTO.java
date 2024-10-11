package br.com.xgommiapi.dto;

import lombok.Builder;

@Builder
public record FollowerRelationResponseDTO(
        Long idFollowRelation,
        GommiUserSimpleResponseDTO follower,
        GommiUserSimpleResponseDTO followed
){
}
