package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HashtagRepository extends JpaRepository<Hashtag, UUID>, JpaSpecificationExecutor<Hashtag> {
    List<Hashtag> findAllByNameIn(List<String> hashtags);

    @Query("from Hashtag h join h.posts p where p.id = ?1 and p.user.id=?2")
    List<Hashtag> findAllByPostIdAndUserId(UUID postId, UUID userId);

    @Query("from Hashtag h where lower(h.name) like lower(concat('%',(?1), '%'))")
    Page<Hashtag> findAllByNameContains(String name, Pageable pageable);

    @Query("select count (p) from Hashtag h join h.posts p where h.name=?1")
    Long countByName(String name);
}