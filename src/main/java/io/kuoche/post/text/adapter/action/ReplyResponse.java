package io.kuoche.post.text.adapter.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReplyResponse {
    private Long commentId;
    private String content;
    private Long textId;
}
