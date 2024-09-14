package br.com.xgommiapi.utils;

import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.domain.entity.Post;
import br.com.xgommiapi.dto.GommiUserResponseDTO;
import br.com.xgommiapi.dto.GommiUserSimpleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GommiUserUtils {
    public static GommiUserSimpleResponseDTO parseGommiUserToSimpleResponseDTO(GommiUser gommiUser) {
        return GommiUserSimpleResponseDTO.builder()
                .idGommiUser(gommiUser.getIdGommiUser())
                .login(gommiUser.getLogin())
                .email(gommiUser.getEmail())
                .name(gommiUser.getName())
                .biography(gommiUser.getBiography())
                .registrationDate(gommiUser.getRegistrationDate())
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
                .build();
    }

    public static List<GommiUserResponseDTO> parseGommiUserListToResponseDTOList(List<GommiUser> gommiUserList) {
        return gommiUserList.stream()
                .map(GommiUserUtils::parseGommiUserToResponseDTO)
                .collect(Collectors.toList());
    }
}
