package com.social_media.instagram.model.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes",
        indexes = @Index(name="likes_indexes",columnList = "post_id"))
public class Like extends BaseEntity {
    @NotNull
    @Column(nullable = false, name = "post_id")
    private UUID postId;

    @ElementCollection
    @CollectionTable(name = "user_likes" ,
                indexes = @Index(name = "user_like_index",columnList = "like_id"),
                uniqueConstraints = @UniqueConstraint(name="like_users_unique_constraints", columnNames = {"user_id"}),
                joinColumns = {
                    @JoinColumn(name = "like_id", nullable = false)
    })
    @Column(name = "user_id", nullable = false)
    private Set<UUID> users;
    public static Like of(UUID id, @NotNull UUID postId, @NotNull Set<UUID> users) {
        Like like = new Like(postId, users);
        if (id!=null) {
            like.setId(id);
        }
        return like;
    }
}
