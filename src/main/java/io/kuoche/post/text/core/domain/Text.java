package io.kuoche.post.text.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
public class Text {
    private Long textId;
    private String userId;
    private String content;
    private LocalDateTime postTime;

    public Text(Long textId, Text text){
        this.textId = textId;
        this.userId = text.getUserId();
        this.content = text.getContent();
        this.postTime = text.getPostTime();
    }

    public Text(String userId, String content){
        this.userId = userId;
        this.content = content;
        this.postTime = LocalDateTime.now();
    }


    public Comment reply(String userId, String content){
        Comment comment = new Comment(this.textId, userId, content);
        return comment;
    }


}
