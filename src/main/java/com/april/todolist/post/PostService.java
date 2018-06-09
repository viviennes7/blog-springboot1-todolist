package com.april.todolist.post;

import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        Post savedPost = this.postRepository.save(post);
        savedPost.initCreateDate();
        savedPost.setPrefixIdAtSubject();
        this.postRepository.save(savedPost);
        return savedPost;
    }

    public Post updatePost(Post post) {
        post.setPrefixIdAtSubject();
        return this.postRepository.save(post);
    }
}
