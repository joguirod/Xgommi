package br.com.xgommiapi.dto;

import lombok.Builder;

@Builder
public record FollowerResponseDTO (
        Long idFollowRelation,
        GommiUserSimpleResponseDTO follower
){
}
