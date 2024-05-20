package com.social_media.instagram.model.entity;

import lombok.*;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "subscriptions",
        uniqueConstraints = {
        @UniqueConstraint(name = "subscriptions_uniques", columnNames = {"following_user_id"})
})
public class Subscribe extends BaseEntity{
    @Id
    @Column(name = "following_user_id", nullable = false)
    private UUID followingUserId;

    @ElementCollection
    @CollectionTable(name = "subscribed_users",
        joinColumns = @JoinColumn(name = "following_user"),
        indexes = @Index(name = "subscribed_users_indexes", columnList = "follower_user, following_user")
    )
    @Column(name = "follower_user")
    private Set<UUID> followersUserIds;

    public static Subscribe of(UUID id, UUID followingUserId, Set<UUID> followersUserIds) {
        Subscribe subscribe = new Subscribe(followingUserId, followersUserIds);
        if (id!=null) {
            subscribe.setId(id);
        }
        return subscribe;
    }
}
