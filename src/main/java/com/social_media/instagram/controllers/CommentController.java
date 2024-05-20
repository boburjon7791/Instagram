package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.CommentReq;
import com.social_media.instagram.model.response.CommentRes;
import com.social_media.instagram.service.CommentService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UtilData.BASE_URL+"comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Header<?> save(@RequestBody Header<CommentReq> request, @AuthenticationPrincipal User user) {
        return Header.created(commentService.createComment(request.data, user));
    }
    @GetMapping
    public Header<?> getAll(@AuthenticationPrincipal User user,
                                       @RequestParam(name = "postId") String postId,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(name = "size", required = false, defaultValue = "25") int size) {
        return commentService.getAll(UUID.fromString(postId), user, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
    @DeleteMapping("/{id}")
    public Header<?> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        commentService.delete(id, user);
        return Header.modifying();
    }
}
