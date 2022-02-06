package io.kuoche.post.text.periphery.data.mapper;

import io.kuoche.post.text.periphery.data.model.UserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<UserDo> findById(String id);
}
