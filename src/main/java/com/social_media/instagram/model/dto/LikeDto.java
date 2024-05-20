package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.Like;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.social_media.instagram.model.entity.Like}
 */
public record LikeDto(
        UUID id,
        Long count,
        UUID postId,
        Set<UUID> users
) {
  public static LikeDto of(UUID id, Long count, UUID postId, Set<UUID> users) {
    return LikeDto.of(id,count, postId, users);
  }
  public Like to(){
    return Like.of(id, postId, users);
  }
  public static LikeDto from(Like like, Long count){
    return LikeDto.of(
            like.getId(),
            count,
            like.getPostId(),
            like.getUsers()
    );
  }
}