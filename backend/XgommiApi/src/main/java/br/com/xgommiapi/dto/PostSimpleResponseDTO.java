package br.com.xgommiapi.dto;

import lombok.Builder;

@Builder
public record PostSimpleResponseDTO(
        Long idPost,
        String text,
        int upvotes,
        int downvotes
){
}
