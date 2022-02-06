package io.kuoche.post.text.adapter.action;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class FollowerRequest {
    @NotEmpty
    @Size(min = 1, max = 25)
    private String userId;
}
