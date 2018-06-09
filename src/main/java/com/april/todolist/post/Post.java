package com.april.todolist.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static java.lang.String.format;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String content;

    @Temporal(TemporalType.DATE)
    private Date createDate;
    private boolean isSuccess;

    public Post(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    void initCreateDate() {
        this.createDate = new Date();
    }

    void setPrefixIdAtSubject() {
        this.subject = format("%s. %s", this.id, this.subject);
    }
}
