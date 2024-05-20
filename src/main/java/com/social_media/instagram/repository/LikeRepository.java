package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID>, JpaSpecificationExecutor<Like> {
    Optional<Like> findByPostId(UUID uuid);
    Long countByPostId(UUID postId);
}