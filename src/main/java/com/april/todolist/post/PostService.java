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

        //Domain에 로직을 넣을 지는 생각하지 않고,,,
        String subject = format("%s. %s", savedPost.getId(), savedPost.getSubject());
        return savedPost;
    }
}
