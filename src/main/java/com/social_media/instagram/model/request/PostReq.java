package com.social_media.instagram.model.request;

import com.social_media.instagram.model.dto.PostDto;
import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.User;

import java.util.Set;

public record PostReq(
        Set<String> photoUrl,
        String description,
        String location
) {
    public static PostReq of(Set<String> photoUrl,
                             String description,
                             String location
    ){
        return new PostReq(photoUrl, description, location);
    }
    public PostDto to(User user){
        return PostDto.of(
                null,
                photoUrl,
                description,
                location,
                UserDto.from(user)
        );
    }
}
