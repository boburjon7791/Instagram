package com.social_media.instagram.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hashtag extends BaseEntity{
    @NotBlank
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "post_hashtag",
            joinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            indexes = {
                @Index(name = "post_hashtag_indexes", columnList = "hashtag_id"),
                @Index(name = "post_hashtag_indexes", columnList = "post_id")
            })
    private Set<Post> posts;
    public static Hashtag of(UUID id, String name, Set<Post> posts){
        Hashtag hashtag = new Hashtag(name, posts);
        if (id!=null) {
            hashtag.setId(id);
        }
        return hashtag;
    }
}
