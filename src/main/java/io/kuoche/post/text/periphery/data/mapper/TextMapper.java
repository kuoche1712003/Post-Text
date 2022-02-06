package io.kuoche.post.text.periphery.data.mapper;

import io.kuoche.post.text.periphery.data.model.TextDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TextMapper {
    int save(TextDo textDo);
    int update(TextDo textDo);
    Optional<TextDo> findByTextId(Long textId);
    List<TextDo> findAllByUserId(String userId);
    int delete(Long textId);
}
