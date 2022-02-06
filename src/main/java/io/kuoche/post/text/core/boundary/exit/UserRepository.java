package io.kuoche.post.text.core.boundary.exit;

import io.kuoche.post.text.core.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
}
