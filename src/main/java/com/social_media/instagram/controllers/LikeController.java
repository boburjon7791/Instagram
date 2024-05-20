package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.LikeReq;
import com.social_media.instagram.model.response.LikeRes;
import com.social_media.instagram.service.LikeService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UtilData.BASE_URL+"like")
public class LikeController {
    private final LikeService likeService;
    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> likeButton(@RequestBody LikeReq like, @AuthenticationPrincipal User user){
        return Header.modifying(likeService.likeButton(like, user));
    }
}
