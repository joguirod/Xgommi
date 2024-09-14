package br.com.xgommiapi.dto;

import lombok.Builder;

@Builder
public record PostResponseDTO(
        Long idPost,
        String text,
        GommiUserSimpleResponseDTO author,
        int upvotes,
        int downvotes
){
}
