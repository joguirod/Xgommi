package br.com.xgommiapi.utils;

import br.com.xgommiapi.domain.entity.FollowerRelation;
import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class GommiUserUtils {
    public static GommiUserSimpleResponseDTO convertToSimpleResponseDTO(GommiUser gommiUser) {
        return GommiUserSimpleResponseDTO.builder()
                .idGommiUser(gommiUser.getIdGommiUser())
                .login(gommiUser.getLogin())
                .email(gommiUser.getEmail())
                .name(gommiUser.getName())
                .build();
    }

    public static List<GommiUserSimpleResponseDTO> convertToSimpleResponseDTOList(List<GommiUser> gommiUserList) {
        return gommiUserList.stream()
                .map(GommiUserUtils::convertToSimpleResponseDTO)
                .collect(Collectors.toList());
    }

    public static GommiUserResponseDTO convertToResponseDTO(GommiUser gommiUser) {
        return GommiUserResponseDTO.builder()
                .idGommiUser(gommiUser.getIdGommiUser())
                .login(gommiUser.getLogin())
                .email(gommiUser.getEmail())
                .name(gommiUser.getName())
                .biography(gommiUser.getBiography())
                .registrationDate(gommiUser.getRegistrationDate())
                .posts(PostUtils.parsePostListToSimpleResponseDTOList(gommiUser.getPosts()))
                .following(convertFollowerRelationsToFollowingResponseDTOs(gommiUser.getFollowing()))
                .followers(convertFollowerRelationsToFollowerResponseDTOs(gommiUser.getFollowers()))
                .build();
    }

    public static List<GommiUserResponseDTO> convertToResponseDTOList(List<GommiUser> gommiUserList) {
        return gommiUserList.stream()
                .map(GommiUserUtils::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public static FollowerRelationResponseDTO convertToFollowerRelationResponseDTO(FollowerRelation followerRelation) {
        return FollowerRelationResponseDTO.builder()
                .idFollowRelation(followerRelation.getIdRelation())
                .follower(convertToSimpleResponseDTO(followerRelation.getFollower()))
                .followed(convertToSimpleResponseDTO(followerRelation.getFollowed()))
                .build();
    }

    public static List<FollowerRelationResponseDTO> convertFollowerRelationsToResponseDTOs(List<FollowerRelation> followerRelationList) {
        return followerRelationList.stream()
                .map(GommiUserUtils::convertToFollowerRelationResponseDTO)
                .collect(Collectors.toList());
    }

    public static FollowerResponseDTO convertToFollowerResponseDTO(FollowerRelation followerRelation) {
        return FollowerResponseDTO.builder()
                .idFollowRelation(followerRelation.getIdRelation())
                .follower(convertToSimpleResponseDTO(followerRelation.getFollower()))
                .build();
    }

    public static List<FollowerResponseDTO> convertFollowerRelationsToFollowerResponseDTOs(List<FollowerRelation> followerRelationList) {
        return followerRelationList.stream()
                .map(GommiUserUtils::convertToFollowerResponseDTO)
                .collect(Collectors.toList());
    }

    public static FollowingResponseDTO convertToFollowingResponseDTO(FollowerRelation followerRelation) {
        return FollowingResponseDTO.builder()
                .idFollowRelation(followerRelation.getIdRelation())
                .followed(convertToSimpleResponseDTO(followerRelation.getFollowed()))
                .build();
    }

    public static List<FollowingResponseDTO> convertFollowerRelationsToFollowingResponseDTOs(List<FollowerRelation> followerRelationList) {
        return followerRelationList.stream()
                .map(GommiUserUtils::convertToFollowingResponseDTO)
                .collect(Collectors.toList());
    }

}
