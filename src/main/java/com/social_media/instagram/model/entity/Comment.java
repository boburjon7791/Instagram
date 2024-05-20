package com.social_media.instagram.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments",
        indexes = @Index(name = "comments_indexes", columnList = "post_id, author_id, replied_to_id"))
public class Comment extends BaseEntity{
    @NotNull
    @Column(name = "post_id",nullable = false)
    private UUID postId;

    @NotBlank
    @Column(nullable = false)
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "replied_to_id")
    private User repliedTo;

    public static Comment of(UUID id, UUID postId, String content, User author, User repliedTo) {
        Comment comment = new Comment(postId, content, author, repliedTo);
        if (id!=null) {
            comment.setId(id);
        }
        return comment;
    }
}
