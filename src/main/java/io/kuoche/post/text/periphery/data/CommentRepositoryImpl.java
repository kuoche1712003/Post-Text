package io.kuoche.post.text.periphery.data;

import io.kuoche.post.text.core.boundary.exit.CommentRepository;
import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.periphery.data.mapper.CommentMapper;
import io.kuoche.post.text.periphery.data.model.CommentDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentMapper commentMapper;

    @Override
    public Long save(Comment comment) {
        CommentDo commentDo = toDo(comment);
        try{
            commentMapper.save(commentDo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return commentDo.getCommentId();
    }

    @Override
    public List<Comment> findAllByTextId(Long textId) {
        List<CommentDo> comments = new ArrayList<>();
        try {
            comments = commentMapper.findAllByTextId(textId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return comments.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long commentId) {
        boolean result = false;
        try{
            commentMapper.delete(commentId);
            result = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return true;
    }

    @Override
    public Optional<Comment> findByCommentId(Long commentId) {
        Optional<CommentDo> commentDoOptional = Optional.empty();
        try{
            commentDoOptional = commentMapper.findByCommentId(commentId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(!commentDoOptional.isPresent())
            return Optional.empty();
        return Optional.of(toEntity(commentDoOptional.get()));
    }

    private CommentDo toDo(Comment comment){
        CommentDo commentDo = new CommentDo();
        commentDo.setCommentId(comment.getCommentId());
        commentDo.setTextId(comment.getTextId());
        commentDo.setUserId(comment.getUserId());
        commentDo.setContent(comment.getContent());
        commentDo.setReplyTime(comment.getReplyTime());
        return commentDo;
    }

    private Comment toEntity(CommentDo commentDo){
        return new Comment(
                commentDo.getCommentId(),
                commentDo.getTextId(),
                commentDo.getUserId(),
                commentDo.getContent(),
                commentDo.getReplyTime()
        );
    }
}
