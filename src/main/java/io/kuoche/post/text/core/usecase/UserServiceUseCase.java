package io.kuoche.post.text.core.usecase;

import io.kuoche.post.text.core.boundary.enter.UserService;
import io.kuoche.post.text.core.boundary.exit.FollowerRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.domain.Follower;
import io.kuoche.post.text.core.domain.Like;
import io.kuoche.post.text.core.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceUseCase implements UserService {
    private final FollowerRepository followerRepository;
    private final LikeRepository likeRepository;

    @Override
    public boolean follow(User user, String userId) {
        Follower follower = user.follow(userId);
        boolean success = followerRepository.save(follower);
        return success;
    }

    @Override
    public List<Follower> getFollowing(String userId) {
        return followerRepository.getFollowing(userId);
    }

    @Override
    public boolean unfollow(User user, String userId){
        Follower follow = user.unfollow(userId);
        boolean success = followerRepository.delete(follow);
        return !success;
    }

    @Override
    public boolean like(User user, Long textId) {
        Like like = user.like(textId);
        boolean success = likeRepository.save(like);
        return success;
    }

    @Override
    public boolean unlike(User user, Long textId) {
        Like like = user.unlike(textId);
        boolean success = likeRepository.delete(like);
        return !success;
    }


}
