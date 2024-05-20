package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.SubscribeReq;
import com.social_media.instagram.model.response.SubscribeRes;
import com.social_media.instagram.service.SubscribeService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = UtilData.BASE_URL+"subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;
    @PutMapping
    public Header<?> follow(@RequestBody SubscribeReq request, @AuthenticationPrincipal User user) {
        return Header.modifying(subscribeService.follow(request, user));
    }
}
