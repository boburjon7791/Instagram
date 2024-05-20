package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.HashtagDto;
import com.social_media.instagram.model.dto.LikeDto;
import com.social_media.instagram.model.dto.PostDto;
import com.social_media.instagram.model.entity.Like;
import com.social_media.instagram.repository.LikeRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record HashtagRes(
        UUID id,
        String name,
        Set<PostRes> posts
) {
    public static HashtagRes of(UUID id, String name, Set<PostRes> posts) {
        return new HashtagRes(id, name, posts);
    }
    public static HashtagRes from(HashtagDto dto, LikeRepository likeRepository) {
        return HashtagRes.of(
                dto.id(),
                dto.name(),
                dto.posts().stream().map(postDto ->
                        PostRes.from(postDto, LikeRes.from(LikeDto.from(
                        likeRepository.findByPostId(postDto.id()).orElseThrow(),
                        likeRepository.countByPostId(postDto.id()))))).collect(Collectors.toSet())
        );
    }
}
