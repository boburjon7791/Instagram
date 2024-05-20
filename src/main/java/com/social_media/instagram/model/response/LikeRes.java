package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.LikeDto;

import java.util.Set;
import java.util.UUID;

public record LikeRes(
        UUID id,
        Long count,
        UUID postId,
        Set<UUID> users
) {
    public static LikeRes of(UUID id, Long count, UUID postId, Set<UUID> users) {
        return new LikeRes(id, count, postId, users);
    }
    public static LikeRes from(LikeDto dto){
        return LikeRes.of(
                dto.id(),
                dto.count(),
                dto.postId(),
                dto.users()
        );
    }
}
