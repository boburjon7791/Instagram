package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.SubscribeDto;

import java.util.Set;
import java.util.UUID;

public record SubscribeRes(
        UUID id,
        UUID followingUserId,
        Set<UUID> followersUserIds
) {
    public static SubscribeRes of(UUID id, UUID followingUserId, Set<UUID> followersUserIds) {
        return new SubscribeRes(id, followingUserId, followersUserIds);
    }
    public static SubscribeRes from(SubscribeDto dto){
        return SubscribeRes.of(
                dto.id(),
                dto.followingUserId(),
                dto.followersUserIds()
        );
    }
}
