package com.social_media.instagram.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at",nullable = false,updatable = false)
    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by",nullable = false,updatable = false)
    private UUID lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date",nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false,updatable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted",columnDefinition = "boolean default false",nullable = false)
    private Boolean deleted;

    @Column(name = "blocked",columnDefinition = "boolean default false",nullable = false)
    private Boolean blocked;
}
