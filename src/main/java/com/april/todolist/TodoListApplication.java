package com.april.todolist;

import com.april.todolist.post.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TodoListApplication {

    private final PostRepository postRepository;

    public TodoListApplication(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

}
