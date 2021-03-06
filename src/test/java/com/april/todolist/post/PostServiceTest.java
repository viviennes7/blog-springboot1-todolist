package com.april.todolist.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Before
    public void setup() {
        this.postService = new PostService(this.postRepository);
    }

    @Test
    public void save() throws InterruptedException {
        Date start = new Date();

        given(this.postRepository.save(any(Post.class)))
                .willReturn(new Post(1L, "블로그 작성", "Spring 기본원리 작성", null, false));

        Post savedPost = this.postService.save(new Post("블로그 작성", "Spring 기본원리 작성"));

        Thread.sleep(1000);
        assertThat(savedPost.getId()).isEqualTo(1L);
        assertThat(savedPost.getSubject()).isEqualTo("1. 블로그 작성");
        assertThat(savedPost.getContent()).isEqualTo("Spring 기본원리 작성");
        assertThat(savedPost.getCreateDate()).isBetween(start, new Date());
    }

    @Test
    public void updatePost() {
        given(this.postRepository.save(any(Post.class)))
                .willReturn(new Post(1L, "1. 블로그 작성", "Spring 기본원리 작성", new Date(), false));
        Post savedPost = this.postService.save(new Post("블로그 작성", "Spring 기본원리 작성"));

        assertThat(savedPost.getSubject()).isEqualTo("1. 블로그 작성");
    }

    @Test
    public void changeIsSuccess() {
        given(this.postRepository.findOne(anyLong()))
                .willReturn(new Post(1L, "1. 블로그 작성", "Spring 기본원리 작성", new Date(), false));

        boolean result = this.postService.changeIsSuccess(1L);

        assertThat(result).isTrue();
    }
}