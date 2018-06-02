package com.april.todolist.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void findBySubject() {
        Post study1 = new Post("스터디", "Spring");
        Post study2 = new Post("스터디", "JPA");
        Post study3 = new Post("스터디", "DDD");
        Post love = new Post("연애", "보블리");
        
        this.entityManager.persist(study1);
        this.entityManager.persist(study2);
        this.entityManager.persist(study3);
        this.entityManager.persist(love);

        List<Post> posts = this.postRepository.findBySubject("스터디");

        assertThat(posts.size()).isEqualTo(3);
        assertThat(posts.get(0).getSubject()).isEqualTo("스터디");
        assertThat(posts.get(0).getContent()).isEqualTo("Spring");
    }
}