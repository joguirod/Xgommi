package br.com.xgommiapi.utils;

import br.com.xgommiapi.domain.entity.Post;
import br.com.xgommiapi.dto.PostResponseDTO;
import br.com.xgommiapi.dto.PostSimpleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostUtils {
    public static PostResponseDTO parsePostToResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .idPost(post.getIdPost())
                .text(post.getText())
                .author(GommiUserUtils.parseGommiUserToSimpleResponseDTO(post.getAuthor()))
                .text(post.getText())
                .upvotes(post.getUpvotes())
                .downvotes(0)
                .build();
    }

    public static List<PostResponseDTO> parsePostListToResponseDTOList(List<Post> postList) {
        return postList.stream()
                .map(PostUtils::parsePostToResponseDTO)
                .collect(Collectors.toList());
    }

    public static PostSimpleResponseDTO parsePostToSimpleResponseDTO(Post post) {
        return PostSimpleResponseDTO.builder()
                .idPost(post.getIdPost())
                .text(post.getText())
                .upvotes(post.getUpvotes())
                .downvotes(post.getDownvotes())
                .build();
    }

    public static List<PostSimpleResponseDTO> parsePostListToSimpleResponseDTOList(List<Post> postList) {
        return postList.stream()
                .map(PostUtils::parsePostToSimpleResponseDTO)
                .collect(Collectors.toList());
    }
}
