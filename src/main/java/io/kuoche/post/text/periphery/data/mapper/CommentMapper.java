package io.kuoche.post.text.periphery.data.mapper;

import io.kuoche.post.text.periphery.data.model.CommentDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    int save(CommentDo commentDo);
    int update(CommentDo commentDo);
    List<CommentDo> findAllByTextId(Long textId);
    int deleteByTextId(Long textId);
    int delete(Long commentId);
    Optional<CommentDo> findByCommentId(Long commentId);
}
