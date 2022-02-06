package io.kuoche.post.text.core.usecase;

import io.kuoche.post.text.core.boundary.enter.TextService;
import io.kuoche.post.text.core.boundary.exit.CommentRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.boundary.exit.TextRepository;
import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.core.domain.Like;
import io.kuoche.post.text.core.domain.Text;
import io.kuoche.post.text.core.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TextServiceUseCase implements TextService {
    private final TextRepository textRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public Optional<Text> post(User user, String content) {
        Text text = user.post(content);
        Long textId = textRepository.save(text);
        if(textId == null)
            return Optional.empty();
        Text result = new Text(textId, text);
        return Optional.of(result);
    }

    @Override
    public List<Text> getTexts(String userId) {
        return textRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Comment> reply(User user, Long textId, String content){
        Optional<Text> textOptional = textRepository.findByTextId(textId);
        if(!textOptional.isPresent())
            return Optional.empty();
        Text text = textOptional.get();
        Comment comment = text.reply(user.getId(), content);
        Long commentId = commentRepository.save(comment);
        if(commentId == null)
            return Optional.empty();
        Comment result = new Comment(commentId, comment);
        return Optional.of(result);
    }

    @Override
    public List<Comment> getComments(Long textId) {
        return commentRepository.findAllByTextId(textId);
    }

    @Override
    public List<Like> getLikingUsers(Long textId) {
        return likeRepository.findAllByTextId(textId);
    }

    @Override
    public boolean deleteText(User user, Long textId) {
        Optional<Text> textOptional = textRepository.findByTextId(textId);
        if(textOptional.isPresent() && user.getId().equals(textOptional.get().getUserId())){
            return textRepository.delete(textId);
        }
        return false;
    }

    @Override
    public boolean deleteComment(User user, Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findByCommentId(commentId);
        if(commentOptional.isPresent() && user.getId().equals(commentOptional.get().getUserId())){
            return commentRepository.delete(commentId);
        }
        return false;
    }
}
