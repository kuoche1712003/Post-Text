package io.kuoche.post.text.periphery.data;

import io.kuoche.post.text.core.boundary.exit.FollowerRepository;
import io.kuoche.post.text.core.domain.Follower;
import io.kuoche.post.text.periphery.data.mapper.FollowerMapper;
import io.kuoche.post.text.periphery.data.model.FollowerDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class FollowerRepositoryImpl implements FollowerRepository {
    private final FollowerMapper followerMapper;

    @Override
    public boolean save(Follower follower) {
        FollowerDo followerDo = toDo(follower);
        boolean result = false;
        try{
            followerMapper.save(followerDo);
            result = true;
        }catch (DuplicateKeyException e){
            result = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Follower> getFollowing(String userId) {
        List<FollowerDo> following = new ArrayList<>();
        try{
            following = followerMapper.findAllByFollowerId(userId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return following.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Follower follower) {
        FollowerDo followerDo = toDo(follower);
        boolean success = false;
        try{
            followerMapper.delete(followerDo);
            success = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return success;
    }

    private Follower toEntity(FollowerDo followerDo){
        return new Follower(
                followerDo.getFollowerId(),
                followerDo.getUserId()
        );
    }

    private FollowerDo toDo(Follower follower){
        FollowerDo followerDo = new FollowerDo();
        followerDo.setFollowerId(follower.getFollowerId());
        followerDo.setUserId(follower.getUserId());
        return followerDo;
    }
}
