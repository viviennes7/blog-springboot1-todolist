package com.april.todolist.post;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public Post savePost(Post post) {
        return this.postService.save(post);
    }
}
