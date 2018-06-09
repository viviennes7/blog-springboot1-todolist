package com.april.todolist.post;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final ModelMapper modelmapper;

    public PostController(PostService postService, PostRepository postRepository, ModelMapper modelmapper) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.modelmapper = modelmapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post savePost(@RequestBody @Valid PostDto postDto) {
        return this.postService.save(this.modelmapper.map(postDto, Post.class));
    }

    @GetMapping
    public List<Post> findBySubject(String subject) {
        return this.postRepository.findBySubject(subject);
    }
}
