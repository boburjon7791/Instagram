package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, UUID>, JpaSpecificationExecutor<Subscribe> {
    Optional<Subscribe> findByFollowingUserId(UUID id);
}