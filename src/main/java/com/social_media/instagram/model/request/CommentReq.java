package com.social_media.instagram.model.request;

import com.social_media.instagram.model.dto.CommentDto;
import com.social_media.instagram.model.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentReq(
        @NotNull
        UUID postId,

        @NotBlank
        String content,

        UUID repliedTo
) {
    public static CommentReq of(UUID postId, String content, UUID repliedTo) {
        return new CommentReq(postId, content, repliedTo);
    }
    public CommentDto to(UserDto author, UserDto repliedTo){
        return CommentDto.of(
                null,
                postId,
                content,
                author,
                repliedTo,
                null
        );
    }
}
