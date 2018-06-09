package com.april.todolist.post;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void initCreateDate() {
        Date startDate = new Date();
        Post post = new Post();
        post.initCreateDate();
        assertThat(post.getCreateDate()).isBetween(startDate, new Date());
    }

    @Test
    public void setPrefixIdAtSubject() {
        Post post = new Post();
        post.setId(5L);
        post.setSubject("제목");
        post.setPrefixIdAtSubject();
        assertThat(post.getSubject()).isEqualTo("5. 제목");
    }
}