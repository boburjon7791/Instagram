package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    Page<Post> findAllByUser_Id(UUID id, Pageable pageable);

    @Modifying
    @Transactional
    void deleteByUser_IdAndId(UUID userId,UUID postId);

    @Modifying
    @Transactional
    @Query("""
        update Post p
        set p.photoUrl=coalesce(?1, p.photoUrl),
            p.description=coalesce(?2, p.description),
            p.location=coalesce(?3, p.location)
        where p.id=?4 and p.deleted=false and p.blocked=false and p.user.id=?5
        """)
    void updatePostById(Set<String> photoUrl, String description, String location, UUID id, UUID userId);
}