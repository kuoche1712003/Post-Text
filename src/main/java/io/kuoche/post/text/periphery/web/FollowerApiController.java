package io.kuoche.post.text.periphery.web;

import io.kuoche.post.text.adapter.UserAdapter;
import io.kuoche.post.text.adapter.action.FollowerRequest;
import io.kuoche.post.text.adapter.action.FollowerResponse;
import io.kuoche.post.text.adapter.vo.UserVo;
import io.kuoche.post.text.core.boundary.enter.UserService;
import io.kuoche.post.text.core.domain.User;
import io.kuoche.post.text.periphery.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v3")
@RequiredArgsConstructor
@Tag(name = "Follows", description = "the follow API")
@SecurityRequirement(name = "BasicAuth")
public class FollowerApiController {
    private final UserService userService;
    private final UserAdapter userAdapter;

    @PostMapping("/users/{followerId}/following")
    public ResponseEntity<FollowerResponse> follow(
            @PathVariable String followerId,
            @RequestBody @Valid FollowerRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        if(!userDetails.sameUser(followerId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        User user = userAdapter.toEntity(userDetails);
        boolean following = userService.follow(user, request.getUserId());
        FollowerResponse response = new FollowerResponse(following);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/users/{followerId}/following/{userId}")
    public ResponseEntity<FollowerResponse> unfollow(
            @PathVariable String followerId,
            @PathVariable String userId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        if(!userDetails.sameUser(followerId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        User user = userAdapter.toEntity(userDetails);
        boolean following = userService.unfollow(user, userId);
        FollowerResponse response = new FollowerResponse(following);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<UserVo>> getFollowing(
            @PathVariable String userId){
        List<UserVo> following = userService.getFollowing(userId).stream()
                .map(follower -> new UserVo(follower.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(following);
    }
}
