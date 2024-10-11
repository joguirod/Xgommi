package br.com.xgommiapi.controller;

import br.com.xgommiapi.dto.PostRequestDTO;
import br.com.xgommiapi.dto.PostResponseDTO;
import br.com.xgommiapi.dto.PostSimpleResponseDTO;
import br.com.xgommiapi.dto.PostUpdateRequestDTO;
import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.exception.PostNotFoundException;
import br.com.xgommiapi.service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xgommi/post")
public class PostController {
    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAll() {
        return new ResponseEntity<>(postService.getAllPostResponseDTO(), HttpStatus.OK);
    }

    @GetMapping("/id/{postId}")
    public ResponseEntity<PostResponseDTO> getById(@PathVariable Long postId) throws PostNotFoundException {
        return new ResponseEntity<>(postService.getPostResponseDTOById(postId), HttpStatus.OK);
    }

    @GetMapping("/author/id/{authorId}")
    public ResponseEntity<List<PostResponseDTO>> getAllByAuthorId(@PathVariable Long authorId) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.getPostsResponseDTOByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping("/author/login/{login}")
    public ResponseEntity<List<PostResponseDTO>> getAllByAuthorLogin(@PathVariable String login) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.getPostsResponseDTOByAuthorLogin(login), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO post) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }

    @PostMapping("/upvote/id/{postId}")
    public ResponseEntity<PostSimpleResponseDTO> upVote(@PathVariable Long postId) throws PostNotFoundException {
        return new ResponseEntity<>(postService.upVotePost(postId), HttpStatus.OK);
    }

    @PostMapping("/downvote/id/{postId}")
    public ResponseEntity<PostSimpleResponseDTO> downVotePost(@PathVariable Long postId) throws PostNotFoundException {
        return new ResponseEntity<>(postService.downVotePost(postId), HttpStatus.OK);
    }

    @DeleteMapping(("/{postId}"))
    public ResponseEntity delete(@PathVariable Long postId) throws PostNotFoundException {
        postService.deletePost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<PostSimpleResponseDTO> update(@RequestBody PostUpdateRequestDTO postUpdateRequestDTO) throws PostNotFoundException {
        return new ResponseEntity<>(postService.update(postUpdateRequestDTO), HttpStatus.OK);
    }
}
