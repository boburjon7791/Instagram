package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.CommentDto;
import com.social_media.instagram.model.dto.UserDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentRes(
        UUID id,

        @NotNull
        UUID postId,

        UserRes author,

        UserRes repliedTo,

        LocalDateTime createdAt
) {
    public static CommentRes of(UUID id, UUID postId, UserDto author, UserDto repliedTo, LocalDateTime createdAt) {
        return new CommentRes(id, postId, UserRes.from(author), UserRes.from(repliedTo), createdAt);
    }
    public static CommentRes from(CommentDto comment) {
        return CommentRes.of(
                comment.id(),
                comment.postId(),
                comment.author(),
                comment.repliedTo(),
                comment.createdAt()
        );
    }
}
