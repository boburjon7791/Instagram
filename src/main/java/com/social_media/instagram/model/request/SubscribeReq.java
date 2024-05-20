package com.social_media.instagram.model.request;

import com.social_media.instagram.model.dto.SubscribeDto;

import java.util.UUID;

public record SubscribeReq (
        UUID followingUserId
){
    public static SubscribeReq od(UUID followingUserId) {
        return new SubscribeReq(followingUserId);
    }
    public SubscribeDto to(){
        return SubscribeDto.of(
                null,
                followingUserId,
                null
        );
    }
}
