package br.com.xgommiapi.controller;

import br.com.xgommiapi.dto.PostRequestDTO;
import br.com.xgommiapi.dto.PostResponseDTO;
import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.service.PostService;
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
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/author/id/{authorId}")
    public ResponseEntity<List<PostResponseDTO>> getAllByAuthorId(@PathVariable Long authorId) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.getPostsByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping("/author/login/{login}")
    public ResponseEntity<List<PostResponseDTO>> getAllByAuthorLogin(@PathVariable String login) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.getPostByAuthorLogin(login), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO post) throws GommiUserNotFoundException {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }
}
