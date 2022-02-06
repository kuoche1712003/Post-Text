package io.kuoche.post.text.adapter.action;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostRequest {
    @NotEmpty
    private String content;
}
