package com.april.todolist.post;

import com.april.todolist.common.error.ValidationException;
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
        if (postDto.getSubject().contains("."))
            throw new ValidationException("제목에 '.'을 넣을 수 없습니다.");

        return this.postService.save(this.modelmapper.map(postDto, Post.class));
    }

    @GetMapping
    public List<Post> findAllPost() {
        return this.postRepository.findAll();
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid PostDto postDto) {
        postDto.setId(id);
        return this.postService.updatePost(this.modelmapper.map(postDto, Post.class));
    }

    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable Long id) {
        boolean result = true;
        try {
            this.postRepository.delete(id);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @GetMapping("/subjects/{subject}")
    public List<Post> findBySubject(@PathVariable String subject) {
        return this.postRepository.findBySubject(subject);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)    public String validation(ValidationException e) {
        return e.getMessage();
    }
}
