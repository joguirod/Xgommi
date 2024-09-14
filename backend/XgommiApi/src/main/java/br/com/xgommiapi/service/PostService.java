package br.com.xgommiapi.service;

import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.domain.entity.Post;
import br.com.xgommiapi.domain.repository.PostRepository;
import br.com.xgommiapi.dto.PostRequestDTO;
import br.com.xgommiapi.dto.PostResponseDTO;
import br.com.xgommiapi.dto.PostSimpleResponseDTO;
import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.exception.PostNotFoundException;
import br.com.xgommiapi.utils.PostUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final GommiUserService gommiUserService;

    public PostService(PostRepository postRepository, GommiUserService gommiUserService) {
        this.postRepository = postRepository;
        this.gommiUserService = gommiUserService;
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostUtils::parsePostToResponseDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getPostById(Long id) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) throw new PostNotFoundException("Post with id " + id + " not found");

        return  PostUtils.parsePostToResponseDTO(post.get());
    }

    public List<PostResponseDTO> getPostsByAuthorId(Long userId) throws GommiUserNotFoundException {
        // try to get user with the specified id, if it doesn't exist, the method will throw not found exception
        GommiUser gommiUser = gommiUserService.getGommiUserById(userId);
        List<Post> postsFound = postRepository.findByAuthorIdGommiUser(userId);
        return PostUtils.parsePostListToResponseDTOList(postsFound);
    }

    public List<PostResponseDTO> getPostByAuthorLogin(String login) throws GommiUserNotFoundException {
        // try to get user with the specified login, if it doesn't exist, the method will throw not found exception
        GommiUser gommiUser = gommiUserService.getGommiUserByLogin(login);
        List<Post> postsFound = postRepository.findByAuthorLogin(login);
        return PostUtils.parsePostListToResponseDTOList(postsFound);
    }

    public PostResponseDTO createPost(PostRequestDTO postDTO) throws GommiUserNotFoundException {
        GommiUser author = gommiUserService.getGommiUserById(postDTO.gommiUserId());
        Post post = new Post(author, postDTO.text());
        postRepository.save(post);
        return PostUtils.parsePostToResponseDTO(post);
    }
}
