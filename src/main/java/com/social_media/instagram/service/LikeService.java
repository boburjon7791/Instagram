package com.social_media.instagram.service;

import com.social_media.instagram.exceptions.NotFoundException;
import com.social_media.instagram.model.dto.LikeDto;
import com.social_media.instagram.model.entity.Like;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.LikeReq;
import com.social_media.instagram.model.response.LikeRes;
import com.social_media.instagram.repository.LikeRepository;
import com.social_media.instagram.utils.Localization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final Localization localization;
    public LikeRes likeButton(LikeReq request, User user){
        Like like = likeRepository.findByPostId(request.postId())
                .orElseThrow(() -> new NotFoundException(localization.getMessage("not_found")));
        Set<UUID> users = like.getUsers();
        if (users.contains(user.getId())) {
            users.remove(user.getId());
        }else {
            users.add(user.getId());
        }
        Like saved=likeRepository.save(like);
        Long count=likeRepository.countByPostId(request.postId());
        log.info("Like button was clicked from {} to {} : {} . count : {}", user.getId(), saved.getPostId(), saved.getId(), count);
        return LikeRes.from(LikeDto.from(saved,count));
    }
}
