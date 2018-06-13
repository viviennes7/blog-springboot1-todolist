package com.april.todolist.post;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void initCreateDate() throws InterruptedException {
        Date startDate = new Date();
        Post post = new Post();
        post.initCreateDate();

        Thread.sleep(1000);
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

    @Test
    public void setPrefixIdAtSubject_제목전에_ID가_있는_경우() {
        Post post = new Post();
        post.setSubject("4. 블로그");
        post.setPrefixIdAtSubject();
        assertThat(post.getSubject()).isEqualTo("4. 블로그");
    }

    @Test
    public void changeIsSuccess_현재_false인_경우() {
        Post post = new Post();
        post.changeIsSuccess();
        assertThat(post.isSuccess()).isTrue();
    }

    @Test
    public void changeIsSuccess_현재_true인_경우() {
        Post post = new Post();
        post.setSuccess(true);
        post.changeIsSuccess();
        assertThat(post.isSuccess()).isFalse();
    }
}