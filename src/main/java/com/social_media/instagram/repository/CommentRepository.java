package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>, JpaSpecificationExecutor<Comment> {
    @Transactional
    @Modifying
    @Query("delete Comment c where c.id=?1 and c.author.id=?2")
    int deleteByIdAndUserId(UUID id, UUID userId);

    @Query("from Comment c where c.id=?1 and c.author.id=?2")
    Page<Comment> findAllByPostIdAndAuthor(UUID postId, UUID authorId, Pageable pageable);
}