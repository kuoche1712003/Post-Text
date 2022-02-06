package io.kuoche.post.text.core.boundary.exit;

import io.kuoche.post.text.core.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long save(Comment comment);
    List<Comment> findAllByTextId(Long textId);
    boolean delete(Long commentId);
    Optional<Comment> findByCommentId(Long commentId);
}
