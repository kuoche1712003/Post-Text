package io.kuoche.post.text.core.boundary.exit;

import io.kuoche.post.text.core.domain.Text;

import java.util.List;
import java.util.Optional;


public interface TextRepository {
    Long save(Text text);
    Optional<Text> findByTextId(Long textId);
    List<Text> findAllByUserId(String userId);
    boolean delete(Long textId);
}
