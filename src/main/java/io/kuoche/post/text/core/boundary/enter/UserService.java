package io.kuoche.post.text.core.boundary.enter;

import io.kuoche.post.text.core.domain.Follower;
import io.kuoche.post.text.core.domain.User;

import java.util.List;

public interface UserService {
    boolean follow(User user, String userId);
    boolean unfollow(User user, String userId);
    List<Follower> getFollowing(String userId);
    boolean like(User user, Long textId);
    boolean unlike(User user, Long textId);
}
