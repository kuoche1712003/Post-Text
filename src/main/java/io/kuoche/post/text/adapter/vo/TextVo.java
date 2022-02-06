package io.kuoche.post.text.adapter.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TextVo {
    private Long id;
    private String authorId;
    private String content;
    private LocalDateTime createTime;
}
