package com.april.todolist.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Test
    public void savePost() throws Exception {
        given(this.postService.save(any(Post.class)))
                .willReturn(new Post(1L, "1. 블로그 작성", "Spring 기본원리", new Date(), false));

        this.mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"블로그 작성\", \"content\" : \"Spring 기본원리\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.subject").value("1. 블로그 작성"))
                .andExpect(jsonPath("$.content").value("Spring 기본원리"))
                .andDo(print());
    }

    @Test
    public void savePost_subject가_Empty인_경우() throws Exception {
        this.mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"content\" : \"Spring 기본원리\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void savePost_subject에_마침표가_포함된_경우() throws Exception {
        this.mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"블로그.작성\", \"content\" : \"Spring 기본원리\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("제목에 '.'을 넣을 수 없습니다."))
                .andDo(print());
    }

    @Test
    public void deletePost() throws Exception {
        this.mvc.perform(delete("/posts/{id}", 1L))
                .andExpect(content().string("true"));
    }

    @Test
    public void deletePost_실패한_경우() throws Exception {
        doThrow(Exception.class)
                .when(this.postRepository).delete(anyLong());

        this.mvc.perform(delete("/posts/{id}", 1L))
                .andExpect(content().string("false"));
    }

    @Test
    public void findBySubject() throws Exception {
        Post post1 = new Post(1L, "블로그", "Spring 기본원리", new Date(), false);
        Post post2 = new Post(2L, "블로그", "DDD", new Date(), false);

        given(this.postRepository.findBySubject(anyString()))
                .willReturn(asList(post1, post2));

        this.mvc.perform(get("/posts/subjects/{subject}", "블로그"))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].subject").value("블로그"))
                .andDo(print());
    }

    @Test
    public void findAllPost() throws Exception {
        given(this.postRepository.findAll())
                .willReturn(asList(new Post(), new Post()));

        this.mvc.perform(get("/posts"))
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @Test
    public void updatePost() throws Exception {
        given(this.postService.updatePost(any(Post.class)))
                .willReturn(new Post(1L, "1. 블로그", "Spring 기본원리", new Date(), false));

        this.mvc.perform(put("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"블로그\", \"content\" : \"Spring 기본원리\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.subject").value("1. 블로그"))
                .andExpect(jsonPath("$.content").value("Spring 기본원리"))
                .andDo(print());
    }

    @Test
    public void updatePost_subject가_없는_경우() throws Exception {
        this.mvc.perform(put("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"\", \"content\" : \"Spring 기본원리\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void changeIsSuccess() throws Exception {
        given(this.postService.changeIsSuccess(anyLong()))
                .willReturn(false);

        this.mvc.perform(patch("/posts/{id}/issuccess", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print());
    }
}