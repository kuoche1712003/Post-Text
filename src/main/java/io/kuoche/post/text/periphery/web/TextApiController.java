package io.kuoche.post.text.periphery.web;

import io.kuoche.post.text.adapter.UserAdapter;
import io.kuoche.post.text.adapter.action.*;
import io.kuoche.post.text.adapter.vo.TextVo;
import io.kuoche.post.text.adapter.vo.UserVo;
import io.kuoche.post.text.core.boundary.enter.TextService;
import io.kuoche.post.text.core.domain.Comment;
import io.kuoche.post.text.core.domain.Text;
import io.kuoche.post.text.core.domain.User;
import io.kuoche.post.text.periphery.data.model.TextDo;
import io.kuoche.post.text.periphery.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v3")
@RequiredArgsConstructor
@Tag(name = "Texts", description = "the text API")
@SecurityRequirement(name = "BasicAuth")
public class TextApiController {
    private final TextService textService;
    private final UserAdapter userAdapter;

    @PostMapping("/texts")
    public ResponseEntity<PostResponse> post(
            @RequestBody @Valid PostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userAdapter.toEntity(userDetails);
        Optional<Text> textOptional = textService.post(user, request.getContent());
        if(!textOptional.isPresent())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        Text text = textOptional.get();
        PostResponse response = new PostResponse(
                text.getTextId(),
                text.getContent()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/texts")
    public ResponseEntity<List<TextVo>> getText(
            @PathVariable String userId){
        List<TextVo> texts = textService.getTexts(userId).stream()
                .map(text -> {
                    return new TextVo(
                            text.getTextId(),
                            text.getUserId(),
                            text.getContent(),
                            text.getPostTime()
                    );
                }).collect(Collectors.toList());
        return ResponseEntity.ok(texts);
    }

    @DeleteMapping("/texts/{textId}")
    public ResponseEntity<DeleteResponse> deleteText(
            @PathVariable Long textId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userAdapter.toEntity(userDetails);
        boolean deleted = textService.deleteText(user, textId);
        return ResponseEntity.ok(new DeleteResponse(deleted));
    }

    @PostMapping("/texts/{textId}/comments")
    public ResponseEntity<ReplyResponse> reply(
            @PathVariable Long textId,
            @RequestBody @Valid ReplyRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userAdapter.toEntity(userDetails);
        Optional<Comment> commentOptional = textService.reply(user, textId, request.getContent());
        if(!commentOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Comment comment = commentOptional.get();
        ReplyResponse response = new ReplyResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getTextId()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/texts/{textId}/comments/{commentId}")
    public ResponseEntity<DeleteResponse> deleteComment(
            @PathVariable Long textId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userAdapter.toEntity(userDetails);
        boolean deleted = textService.deleteComment(user, commentId);
        return ResponseEntity.ok(new DeleteResponse(deleted));
    }

    @GetMapping("/texts/{textId}/comments")
    public ResponseEntity<List<TextVo>> getComments(
            @PathVariable Long textId){
        List<TextVo> comments = textService.getComments(textId).stream()
                .map(comment -> new TextVo(
                        comment.getCommentId(),
                        comment.getUserId(),
                        comment.getContent(),
                        comment.getReplyTime())
                ).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }
}
