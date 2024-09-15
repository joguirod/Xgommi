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

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) throw new PostNotFoundException("Post with id " + id + " not found");

        return post.get();
    }

    public List<Post> getPostsByAuthorId(Long userId) throws GommiUserNotFoundException {
        // try to get user with the specified id, if it doesn't exist, the method will throw not found exception
        GommiUser gommiUser = gommiUserService.getGommiUserById(userId);
        return postRepository.findByAuthorIdGommiUser(userId);
    }

    public List<Post> getPostByAuthorLogin(String login) throws GommiUserNotFoundException {
        // try to get user with the specified login, if it doesn't exist, the method will throw not found exception
        GommiUser gommiUser = gommiUserService.getGommiUserByLogin(login);
        return postRepository.findByAuthorLogin(login);
    }

    public List<PostResponseDTO> getAllPostResponseDTO() {
        return PostUtils.parsePostListToResponseDTOList(postRepository.findAll());
    }

    public PostResponseDTO getPostResponseDTOById(Long id) throws PostNotFoundException {
        Post postFound = getPostById(id);

        return PostUtils.parsePostToResponseDTO(postFound);
    }

    public List<PostResponseDTO> getPostsResponseDTOByAuthorId(Long userId) throws GommiUserNotFoundException {
        List<Post> postsFound = getPostsByAuthorId(userId);
        return PostUtils.parsePostListToResponseDTOList(postsFound);
    }

    public List<PostResponseDTO> getPostsResponseDTOByAuthorLogin(String login) throws GommiUserNotFoundException {
        List<Post> postsFounds = getPostByAuthorLogin(login);
        return PostUtils.parsePostListToResponseDTOList(postsFounds);
    }

    public PostResponseDTO createPost(PostRequestDTO postDTO) throws GommiUserNotFoundException {
        GommiUser author = gommiUserService.getGommiUserById(postDTO.gommiUserId());
        Post post = new Post(author, postDTO.text());
        postRepository.save(post);
        return PostUtils.parsePostToResponseDTO(post);
    }

    public PostSimpleResponseDTO upVotePost(Long id) throws PostNotFoundException {
        Post post = getPostById(id);
        post.incrementUpVotes();
        postRepository.save(post);
        return PostUtils.parsePostToSimpleResponseDTO(post);
    }

    public PostSimpleResponseDTO downVotePost(Long id) throws PostNotFoundException {
        Post post = getPostById(id);
        post.incrementDownVotes();
        postRepository.save(post);
        return PostUtils.parsePostToSimpleResponseDTO(post);
    }
}
