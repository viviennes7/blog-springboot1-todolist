package com.april.todolist.post;

import com.april.todolist.common.error.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
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
    public Post savePost(@RequestBody @Validated PostDto postDto) {
        if (postDto.getSubject().contains("."))
            throw new ValidationException("제목에 '.'을 넣을 수 없습니다.");

        return this.postService.save(this.modelmapper.map(postDto, Post.class));
    }

    @GetMapping
    public List<Post> findAllPost() {
        return this.postRepository.findAll();
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        postDto.setId(id);
        return this.postService.updatePost(this.modelmapper.map(postDto, Post.class));
    }

    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable Long id) {
        boolean result = true;
        try {
            this.postRepository.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }
        return result;
    }

    @GetMapping("/subjects/{subject}")
    public List<Post> findBySubject(@PathVariable String subject) {
        return this.postRepository.findBySubject(subject);
    }

    @PatchMapping("/{id}/issuccess")
    public boolean changeIsSuccess(@PathVariable Long id) {
        return this.postService.changeIsSuccess(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    private String validation(ValidationException e) {
        return e.getMessage();
    }
}
