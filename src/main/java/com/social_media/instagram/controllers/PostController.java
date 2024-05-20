package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.PostReq;
import com.social_media.instagram.model.request.PostUpdateReq;
import com.social_media.instagram.model.response.PostRes;
import com.social_media.instagram.service.PostService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = UtilData.BASE_URL+"post")
public class PostController {
    private final PostService postService;
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Header<?> save(@AuthenticationPrincipal User user, @RequestBody PostReq post){
        return Header.created(postService.save(user, post));
    }
    @GetMapping
    public Header<?> getAllMine(@AuthenticationPrincipal User user,
                                @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(name = "size", required = false, defaultValue = "25") int size){
        return postService.findAll(user, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> delete(@AuthenticationPrincipal User user, @PathVariable UUID id){
        postService.delete(user, id);
        return Header.modifying();
    }
    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> update(@AuthenticationPrincipal User user, @RequestBody PostUpdateReq request){
        return Header.modifying(postService.updateById(user, request));
    }
}
