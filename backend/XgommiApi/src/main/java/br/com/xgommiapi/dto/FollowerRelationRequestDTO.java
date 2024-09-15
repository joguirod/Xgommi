package br.com.xgommiapi.dto;

public record FollowerRelationRequestDTO (
        Long idFollower,
        Long idFollowed
){
}
