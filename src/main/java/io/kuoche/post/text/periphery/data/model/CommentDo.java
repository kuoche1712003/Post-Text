package io.kuoche.post.text.periphery.data.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDo {
    private Long commentId;
    private Long textId;
    private String userId;
    private String content;
    private LocalDateTime replyTime;
}
