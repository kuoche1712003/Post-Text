package io.kuoche.post.text.core.usecase;

import io.kuoche.post.text.core.boundary.enter.TextService;
import io.kuoche.post.text.core.boundary.exit.CommentRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.boundary.exit.TextRepository;
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
public class PostTextTest {
    @Mock
    private TextRepository textRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private LikeRepository likeRepository;

    @Test
    public void post_repository_failed(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        Mockito.when(textRepository.save(Mockito.any(Text.class)))
                .thenReturn(null);
        User user = new User("andy123", "Andy", null);
        Optional<Text> textOptional = textService.post(user, "hello world");
        Assertions.assertThat(textOptional.isPresent()).isFalse();
    }

    @Test
    public void post_success(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        Mockito.when(textRepository.save(Mockito.any(Text.class)))
                .thenReturn(1L);
        User user = new User("andy123", "Andy", null);
        Optional<Text> textOptional = textService.post(user, "hello world");
        Assertions.assertThat(textOptional.isPresent()).isTrue();
        Text text = textOptional.get();
        Assertions.assertThat(text.getTextId()).isEqualTo(1L);
        Assertions.assertThat(text.getContent()).isEqualTo("hello world");
        Assertions.assertThat(text.getUserId()).isEqualTo("andy123");
    }

    @Test
    public void delete_self_text(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        Text text = new Text(1L, "andy123", "hello world", LocalDateTime.now());
        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.of(text));
        Mockito.when(textRepository.delete(1L))
                .thenReturn(true);
        User user = new User("andy123", "Andy", null);
        boolean deleted = textService.deleteText(user, 1L);
        Assertions.assertThat(deleted).isTrue();
    }

    @Test
    public void delete_other_text(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);
        Text text = new Text(1L, "cindy456", "hello world", LocalDateTime.now());
        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.of(text));

        User user = new User("andy123", "Andy", null);
        boolean deleted = textService.deleteText(user, 1L);
        Assertions.assertThat(deleted).isFalse();
    }

    @Test
    public void delete_text_does_not_exist(){
        TextService textService = new TextServiceUseCase(
                textRepository, commentRepository, likeRepository);

        Mockito.when(textRepository.findByTextId(1L))
                .thenReturn(Optional.empty());

        User user = new User("andy123", "Andy", null);
        boolean deleted = textService.deleteText(user, 1L);
        Assertions.assertThat(deleted).isFalse();
    }
}
