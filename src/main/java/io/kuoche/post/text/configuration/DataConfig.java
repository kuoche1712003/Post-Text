package io.kuoche.post.text.configuration;

import io.kuoche.post.text.core.boundary.exit.*;
import io.kuoche.post.text.periphery.data.*;
import io.kuoche.post.text.periphery.data.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataConfig {
    @Bean
    public UserRepository userRepository(UserMapper userMapper){
        return new UserRepositoryImpl(userMapper);
    }

    @Bean
    public LikeRepository likeRepository(LikeMapper likeMapper){
        return new LikeRepositoryImpl(likeMapper);
    }

    @Bean
    public FollowerRepository followerRepository(FollowerMapper followerMapper){
        return new FollowerRepositoryImpl(followerMapper);
    }

    @Bean
    public TextRepository textRepository(
            TextMapper textMapper,
            CommentMapper commentMapper,
            DataSourceTransactionManager transactionManager){
        return new TextRepositoryImpl(textMapper, commentMapper, transactionManager);
    }

    @Bean
    public CommentRepository commentRepository(CommentMapper commentMapper){
        return new CommentRepositoryImpl(commentMapper);
    }
}
