package io.kuoche.post.text.core.usecase;

import io.kuoche.post.text.core.boundary.enter.TextService;
import io.kuoche.post.text.core.boundary.exit.CommentRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.boundary.exit.TextRepository;
import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.core.domain.Text;
import io.kuoche.post.text.core.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReplyCommentTest {
    @Mock
    private TextRepository textRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private LikeRepository likeRepository;

    @Test
    public void reply_success(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);
        Text text = new Text(1L, "andy123", "hello world", LocalDateTime.now());
        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.of(text));
        Mockito.when(commentRepository.save(Mockito.any()))
                .thenReturn(1L);

        Optional<Comment> commentOptional = textService.reply(user, 1L, "reply");

        Assertions.assertThat(commentOptional.isPresent()).isTrue();
        Comment comment = commentOptional.get();
        Assertions.assertThat(comment.getCommentId()).isEqualTo(1L);
        Assertions.assertThat(comment.getTextId()).isEqualTo(1L);
        Assertions.assertThat(comment.getUserId()).isEqualTo("andy123");
        Assertions.assertThat(comment.getContent()).isEqualTo("reply");
    }

    @Test
    public void reply_text_does_not_exist(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);

        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.empty());

        Optional<Comment> commentOptional = textService.reply(user, 1L, "reply");

        Assertions.assertThat(commentOptional.isPresent()).isFalse();
    }

    @Test
    public void reply_repository_failed(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);
        Text text = new Text(1L, "andy123", "hello world", LocalDateTime.now());
        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.of(text));
        Mockito.when(commentRepository.save(Mockito.any()))
                .thenReturn(null);

        Optional<Comment> commentOptional = textService.reply(user, 1L, "reply");

        Assertions.assertThat(commentOptional.isPresent()).isFalse();
    }

    @Test
    public void delete_self_comment(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);
        Comment comment = new Comment(1L, 1L, "andy123", "reply", LocalDateTime.now());
        Mockito.when(commentRepository.findByCommentId(1L))
                .thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.delete(1L))
                .thenReturn(true);

        boolean deleted = textService.deleteComment(user, 1L);

        Assertions.assertThat(deleted).isTrue();
    }

    @Test
    public void delete_other_comment(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);
        Comment comment = new Comment(1L, 1L, "cindy456", "reply", LocalDateTime.now());
        Mockito.when(commentRepository.findByCommentId(1L))
                .thenReturn(Optional.of(comment));


        boolean deleted = textService.deleteComment(user, 1L);

        Assertions.assertThat(deleted).isFalse();
    }

    @Test
    public void delete_comment_does_not_exist(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        User user = new User("andy123", "Andy", null);
        Mockito.when(commentRepository.findByCommentId(1L))
                .thenReturn(Optional.empty());


        boolean deleted = textService.deleteComment(user, 1L);

        Assertions.assertThat(deleted).isFalse();
    }

}
