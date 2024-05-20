package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.Hashtag;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.social_media.instagram.model.entity.Hashtag}
 */
public record HashtagDto(
        UUID id,
        String name,
        Set<PostDto> posts
){
  public static HashtagDto of(UUID id, String name, Set<PostDto> posts){
     return new HashtagDto(id, name, posts);
  }
  public Hashtag to(){
    return Hashtag.of(id, name, posts.stream().map(PostDto::to).collect(Collectors.toSet()));
  }
  public static HashtagDto from(Hashtag hashtag){
    return HashtagDto.of(
            hashtag.getId(),
            hashtag.getName(),
            hashtag.getPosts().stream()
                    .map(PostDto::from)
                    .collect(Collectors.toSet())
    );
  }
}