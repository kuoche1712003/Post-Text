package io.kuoche.post.text.core.usecase;

import io.kuoche.post.text.core.boundary.enter.UserService;
import io.kuoche.post.text.core.boundary.exit.FollowerRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeTextTest {
    @Mock
    private FollowerRepository followerRepository;
    @Mock
    private LikeRepository likeRepository;

    @Test
    public void like_text_exist(){
        UserService userService = new UserServiceUseCase(followerRepository, likeRepository);
        User user = new User("andy123", "Andy", null);

        Mockito.when(likeRepository.save(Mockito.any()))
                .thenReturn(true);

        boolean liked = userService.like(user, 1L);

        Assertions.assertThat(liked).isTrue();
    }

    @Test
    public void like_text_does_not_exist(){
        UserService userService = new UserServiceUseCase(followerRepository, likeRepository);
        User user = new User("andy123", "Andy", null);

        Mockito.when(likeRepository.save(Mockito.any()))
                .thenReturn(false);

        boolean liked = userService.like(user, 1L);

        Assertions.assertThat(liked).isFalse();
    }
}
