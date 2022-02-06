package io.kuoche.post.text.periphery.data;

import io.kuoche.post.text.core.boundary.exit.UserRepository;
import io.kuoche.post.text.core.domain.User;
import io.kuoche.post.text.periphery.data.mapper.UserMapper;
import io.kuoche.post.text.periphery.data.model.UserDo;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(String id) {
        Optional<UserDo> userDoOpt = userMapper.findById(id);
        if(!userDoOpt.isPresent())
            return Optional.empty();
        UserDo userDo = userDoOpt.get();
        return Optional.of(toEntity(userDo));
    }

    private User toEntity(UserDo userDo){
        User user = new User(
                userDo.getId(),
                userDo.getName(),
                userDo.getCredentials()
        );
        return user;
    }
}
