package br.com.xgommiapi.utils;

import br.com.xgommiapi.domain.entity.FollowerRelation;
import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class GommiUserUtils {
    public static GommiUserSimpleResponseDTO parseGommiUserToSimpleResponseDTO(GommiUser gommiUser) {
        return GommiUserSimpleResponseDTO.builder()
                .idGommiUser(gommiUser.getIdGommiUser())
                .login(gommiUser.getLogin())
                .email(gommiUser.getEmail())
                .name(gommiUser.getName())
                .build();
    }

    public static List<GommiUserSimpleResponseDTO> parseGommiUserListToSimplesResponseDTOList(List<GommiUser> gommiUserList) {
        return gommiUserList.stream()
                .map(GommiUserUtils::parseGommiUserToSimpleResponseDTO)
                .collect(Collectors.toList());
    }

    public static GommiUserResponseDTO parseGommiUserToResponseDTO(GommiUser gommiUser) {
        return GommiUserResponseDTO.builder()
                .idGommiUser(gommiUser.getIdGommiUser())
                .login(gommiUser.getLogin())
                .email(gommiUser.getEmail())
                .name(gommiUser.getName())
                .biography(gommiUser.getBiography())
                .registrationDate(gommiUser.getRegistrationDate())
                .posts(PostUtils.parsePostListToSimpleResponseDTOList(gommiUser.getPosts()))
                .following(convertFollowerRelationsToFollowingResponseDTOs(gommiUser.getFollowing()))
                .followers(convertFollowerRelationsToFollowingResponseDTOs(gommiUser.getFollowers()))
                .build();
    }

    public static List<GommiUserResponseDTO> parseGommiUserListToResponseDTOList(List<GommiUser> gommiUserList) {
        return gommiUserList.stream()
                .map(GommiUserUtils::parseGommiUserToResponseDTO)
                .collect(Collectors.toList());
    }

    public static FollowerRelationResponseDTO parseFollowerRelationToResponseDTO(FollowerRelation followerRelation) {
        return FollowerRelationResponseDTO.builder()
                .idFollowRelation(followerRelation.getIdRelation())
                .follower(parseGommiUserToSimpleResponseDTO(followerRelation.getFollower()))
                .followed(parseGommiUserToSimpleResponseDTO(followerRelation.getFollowed()))
                .build();
    }

    public static List<FollowerRelationResponseDTO> parseFollowerRelationListToResponseDTOList(List<FollowerRelation> followerRelationList) {
        return followerRelationList.stream()
                .map(GommiUserUtils::parseFollowerRelationToResponseDTO)
                .collect(Collectors.toList());
    }

    public static FollowerResponseDTO convertToFollowerResponseDTO(FollowerRelation followerRelation) {
        return FollowerResponseDTO.builder()
                .idFollowRelation(followerRelation.getIdRelation())
                .follower(parseGommiUserToSimpleResponseDTO(followerRelation.getFollower()))
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
                .followed(parseGommiUserToSimpleResponseDTO(followerRelation.getFollower()))
                .build();
    }

    public static List<FollowingResponseDTO> convertFollowerRelationsToFollowingResponseDTOs(List<FollowerRelation> followerRelationList) {
        return followerRelationList.stream()
                .map(GommiUserUtils::convertToFollowingResponseDTO)
                .collect(Collectors.toList());
    }

}
