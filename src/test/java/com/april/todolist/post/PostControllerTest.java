package com.april.todolist.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void savePost() throws Exception {
        given(this.postService.save(any(Post.class)))
                .willReturn(new Post(1L, "1. 블로그 작성", "Spring 기본원리", new Date()));

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
    public void findBySubject() throws Exception {
        Post post1 = new Post(1L, "블로그", "Spring 기본원리", new Date());
        Post post2 = new Post(2L, "블로그", "DDD", new Date());

        given(this.postRepository.findBySubject(anyString()))
                .willReturn(asList(post1, post2));

        this.mvc.perform(get("/posts").param("subject", "블로그"))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].subject").value("블로그"))
                .andDo(print());
    }
}