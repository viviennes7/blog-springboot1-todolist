package com.april.todolist.post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping
    public Post savePost(@RequestBody Post post) {
        return this.postService.save(post);
    }

    @GetMapping
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }
}
