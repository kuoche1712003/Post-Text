package io.kuoche.post.text.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {
    private String userId;
    private Long textId;
}
