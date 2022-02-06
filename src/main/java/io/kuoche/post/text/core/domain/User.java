package io.kuoche.post.text.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String credentials;

    public Follower follow(String userId){
        return new Follower(this.id, userId);
    }
    public Follower unfollow(String userId){
        return new Follower(this.id, userId);
    }

    public Like like(Long textId){
        return new Like(this.id, textId);
    }

    public Like unlike(Long textId){
        return new Like(this.id, textId);
    }

    public Text post(String content){
        return new Text(this.id, content);
    }
}
