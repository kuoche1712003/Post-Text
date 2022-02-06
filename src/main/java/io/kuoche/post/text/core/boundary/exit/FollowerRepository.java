package io.kuoche.post.text.core.boundary.exit;

import io.kuoche.post.text.core.domain.Follower;

import java.util.List;

public interface FollowerRepository {
    boolean save(Follower follower);
    List<Follower> getFollowing(String userId);
    boolean delete(Follower follower);
}
