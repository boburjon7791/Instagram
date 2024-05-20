package com.social_media.instagram.model.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts",
        indexes = @Index(name = "posts_indexes",columnList = "location, user_id"))
public class Post extends BaseEntity{
    @ElementCollection
    @CollectionTable(name = "post_photo",
        joinColumns = @JoinColumn(name = "post"),
            indexes = @Index(name = "post_photo_indexes", columnList = "post, photo_url")
    )
    @Column(name = "photo_url")
    private Set<String> photoUrl;

    private String description;
    
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    public static Post of(UUID id, Set<String> photoUrl, String description, String location, User user) {
        Post post = new Post(photoUrl, description, location, user);
        if (id!=null) {
            post.setId(id);
        }
        return post;
    }
}
