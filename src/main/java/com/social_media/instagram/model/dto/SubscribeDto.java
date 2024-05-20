package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.Subscribe;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Subscribe}
 */
public record SubscribeDto(
        UUID id,
        UUID followingUserId,
        Set<UUID> followersUserIds
) {
  public static SubscribeDto of(UUID id, UUID followingUserId, Set<UUID> followersUserIds) {
    return new SubscribeDto(id, followingUserId, followersUserIds);
  }
  public Subscribe to(){
    return Subscribe.of(id, followingUserId, followersUserIds);
  }
  public static SubscribeDto from(Subscribe subscribe){
    return SubscribeDto.of(
            subscribe.getId(),
            subscribe.getFollowingUserId(),
            subscribe.getFollowersUserIds()
    );
  }
}