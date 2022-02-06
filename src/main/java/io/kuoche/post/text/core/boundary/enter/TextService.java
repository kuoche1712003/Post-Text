package io.kuoche.post.text.core.boundary.enter;

import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.core.domain.Like;
import io.kuoche.post.text.core.domain.Text;
import io.kuoche.post.text.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface TextService {
    Optional<Text> post(User user, String content);
    List<Text> getTexts(String userId);
    Optional<Comment> reply(User user, Long textId, String content);
    List<Comment> getComments(Long textId);
    List<Like> getLikingUsers(Long textId);
    boolean deleteText(User user, Long textId);
    boolean deleteComment(User user, Long commentId);
}
