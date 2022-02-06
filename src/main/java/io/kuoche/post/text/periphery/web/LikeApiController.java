package io.kuoche.post.text.periphery.web;

import io.kuoche.post.text.adapter.UserAdapter;
import io.kuoche.post.text.adapter.action.LikeRequest;
import io.kuoche.post.text.adapter.action.LikeResponse;
import io.kuoche.post.text.adapter.vo.UserVo;
import io.kuoche.post.text.core.boundary.enter.TextService;
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
@RequestMapping("/api/v3/")
@RequiredArgsConstructor
@Tag(name = "Likes", description = "the like API")
@SecurityRequirement(name = "BasicAuth")
public class LikeApiController {
    private final UserAdapter userAdapter;
    private final UserService userService;
    private final TextService textService;

    @PostMapping("/users/{userId}/likes")
    public ResponseEntity<LikeResponse> like(
            @PathVariable String userId,
            @RequestBody @Valid LikeRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        if(!userDetails.sameUser(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        User user = userAdapter.toEntity(userDetails);
        boolean liked = userService.like(user, request.getTextId());
        return ResponseEntity.ok(new LikeResponse(liked));
    }

    @DeleteMapping("/users/{userId}/likes/{textId}")
    public ResponseEntity<LikeResponse> unlike(
            @PathVariable String userId,
            @PathVariable Long textId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        if(!userDetails.sameUser(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        User user = userAdapter.toEntity(userDetails);
        boolean liked = userService.unlike(user, textId);
        return ResponseEntity.ok(new LikeResponse(liked));
    }

    @GetMapping("/texts/{textId}/liking_users")
    public ResponseEntity<List<UserVo>> getLikingUser(
            @PathVariable Long textId){
        List<UserVo> likingUsers = textService.getLikingUsers(textId).stream()
                .map(like -> new UserVo(like.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(likingUsers);
    }
}
