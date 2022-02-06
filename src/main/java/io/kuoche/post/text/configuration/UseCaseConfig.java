package io.kuoche.post.text.configuration;

import io.kuoche.post.text.core.boundary.enter.TextService;
import io.kuoche.post.text.core.boundary.enter.UserService;
import io.kuoche.post.text.core.boundary.exit.CommentRepository;
import io.kuoche.post.text.core.boundary.exit.FollowerRepository;
import io.kuoche.post.text.core.boundary.exit.LikeRepository;
import io.kuoche.post.text.core.boundary.exit.TextRepository;
import io.kuoche.post.text.core.usecase.TextServiceUseCase;
import io.kuoche.post.text.core.usecase.UserServiceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public UserService userService(
            FollowerRepository followerRepository,
            LikeRepository likeRepository){
        return new UserServiceUseCase(followerRepository, likeRepository);
    }

    @Bean
    public TextService textService(
            TextRepository textRepository,
            CommentRepository commentRepository,
            LikeRepository likeRepository){
        return new TextServiceUseCase(textRepository, commentRepository, likeRepository);
    }
}
