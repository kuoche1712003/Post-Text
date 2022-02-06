package io.kuoche.post.text.adapter.action;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LikeRequest {
    @NotNull
    private Long textId;
}
