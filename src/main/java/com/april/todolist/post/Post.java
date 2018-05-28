package com.april.todolist.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String subject;

    @Column
    private String content;

    @Temporal(TemporalType.DATE)
    @Column
    private Date createDate;

    public Post(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    void initCreateDate() {
        this.createDate = new Date();
    }
}
