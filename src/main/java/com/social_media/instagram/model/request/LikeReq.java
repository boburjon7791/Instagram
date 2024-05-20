package com.social_media.instagram.model.request;

import com.social_media.instagram.model.dto.LikeDto;

import java.util.Set;
import java.util.UUID;

public record LikeReq(
        UUID userId,
        UUID postId
) {
    public static LikeReq of(UUID userId, UUID postId) {
        return new LikeReq(userId, postId);
    }
    public LikeDto to(){
        return new LikeDto(null, null, postId, Set.of(userId));
    }
}
