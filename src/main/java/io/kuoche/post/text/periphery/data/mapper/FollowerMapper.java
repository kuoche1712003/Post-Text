package io.kuoche.post.text.periphery.data.mapper;

import io.kuoche.post.text.periphery.data.model.FollowerDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowerMapper {
    int save(FollowerDo followerDo);
    List<FollowerDo> findAllByFollowerId(String followerId);
    int delete(FollowerDo followerDo);
}
