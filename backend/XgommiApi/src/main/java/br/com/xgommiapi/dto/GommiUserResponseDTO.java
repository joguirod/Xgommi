package br.com.xgommiapi.dto;

import br.com.xgommiapi.domain.entity.Community;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GommiUserResponseDTO (
        Long idGommiUser,
        String login,
        String email,
        String name,
        String biography,
        LocalDateTime registrationDate,
        List<PostSimpleResponseDTO> posts,
        List<FollowingResponseDTO> following,
        List<FollowingResponseDTO> followers
        ){
}
