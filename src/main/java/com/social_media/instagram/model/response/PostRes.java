package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.PostDto;

import java.util.Set;
import java.util.UUID;

public record PostRes(
        UUID id,
        Set<String> photoUrl,
        String description,
        String location,
        UserRes user,
        LikeRes like
) {
    public static PostRes of(UUID id, Set<String> photoUrl, String description, String location, UserRes user, LikeRes like){
        return new PostRes(id, photoUrl, description, location, user, like);
    }
    public static PostRes from(PostDto post, LikeRes like){
        return PostRes.of(
                post.id(),
                post.photoUrl(),
                post.description(),
                post.location(),
                UserRes.from(post.user()),
                like
        );
    }
}
