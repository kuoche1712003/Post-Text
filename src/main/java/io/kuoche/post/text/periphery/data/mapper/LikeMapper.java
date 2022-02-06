package io.kuoche.post.text.periphery.data.mapper;

import io.kuoche.post.text.core.domain.Like;
import io.kuoche.post.text.periphery.data.model.LikeDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeMapper {
    int save(LikeDo likeDo);
    List<LikeDo> findAllByTextId(Long textId);
    int delete(LikeDo likeDo);
}
