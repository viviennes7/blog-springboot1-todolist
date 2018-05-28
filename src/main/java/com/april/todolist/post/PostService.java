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
        //사실 변수로 받을 필요는 없지만 테스트 용이성을 고려
        Post savedPost = this.postRepository.save(post);

        savedPost.initCreateDate();
        //Domain에 로직을 넣을 지는 생각하지 않고,,,
        String subject = format("%s. %s", savedPost.getId(), savedPost.getSubject());
        savedPost.setSubject(subject);
        this.postRepository.save(savedPost);
        return savedPost;
    }
}
