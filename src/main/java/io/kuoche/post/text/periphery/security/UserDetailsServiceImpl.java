package io.kuoche.post.text.periphery.security;

import io.kuoche.post.text.core.boundary.exit.UserRepository;
import io.kuoche.post.text.core.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        CustomUserDetails userDetails = toUserDetails(user);
        return userDetails;
    }

    private CustomUserDetails toUserDetails(User user){
        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getCredentials()
        );
    }
}
