package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.social_media.instagram.model.entity.Comment}
 */
public record CommentDto(
        UUID id,

        @NotNull
        UUID postId,

        @NotBlank String content,
        UserDto author,

        UserDto repliedTo,

        LocalDateTime createdAt
) {
  public static CommentDto of(UUID id, UUID postId, String content, UserDto author, UserDto repliedTo, LocalDateTime createdAt) {
    return new CommentDto(id, postId, content, author, repliedTo, createdAt);
  }
  public Comment to(){
    return Comment.of(id, postId, content, author.to(), repliedTo.to());
  }
  public static CommentDto from(Comment comment){
    return CommentDto.of(
            comment.getId(),
            comment.getPostId(),
            comment.getContent(),
            UserDto.from(comment.getAuthor()),
            UserDto.from(comment.getRepliedTo()),
            comment.getCreatedDate()
    );
  }
}