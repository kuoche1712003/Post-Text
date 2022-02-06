package io.kuoche.post.text.adapter;

import io.kuoche.post.text.core.domain.User;
import io.kuoche.post.text.periphery.security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {
    public User toEntity(CustomUserDetails userDetails){
        return new User(userDetails.getUsername(), userDetails.getName(), null);
    }
}
