package io.kuoche.post.text.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Comment {
    private Long commentId;
    private Long textId;
    private String userId;
    private String content;
    private LocalDateTime replyTime;

    public Comment(Long commentId, Comment comment){
        this.commentId = commentId;
        this.textId = comment.getTextId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.replyTime = comment.getReplyTime();
    }
    public Comment(Long textId, String userId, String content){
        this.textId = textId;
        this.userId = userId;
        this.content = content;
        this.replyTime = LocalDateTime.now();
    }

}
