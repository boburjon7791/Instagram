package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.Post;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.social_media.instagram.model.entity.Post}
 */
public record PostDto(
        UUID id,
        Set<String> photoUrl,
        String description,
        String location,
        UserDto user
){
    public static PostDto of(UUID id, Set<String> photoUrl, String description, String location, UserDto user) {
        return new PostDto(id, photoUrl, description, location, user);
    }
    public Post to(){
        return Post.of(id, photoUrl, description, location, user.to());
    }
    public static PostDto from(Post post){
        return PostDto.of(
                post.getId(),
                post.getPhotoUrl(),
                post.getDescription(),
                post.getLocation(),
                UserDto.from(post.getUser())
        );
    }
}