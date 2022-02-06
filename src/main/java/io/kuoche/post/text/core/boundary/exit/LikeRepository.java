package io.kuoche.post.text.core.boundary.exit;

import io.kuoche.post.text.core.domain.Like;

import java.util.List;

public interface LikeRepository {
    boolean save(Like like);
    List<Like> findAllByTextId(Long textId);
    boolean delete(Like like);
}
