package com.social_media.instagram.service;

import com.social_media.instagram.exceptions.NotFoundException;
import com.social_media.instagram.model.dto.SubscribeDto;
import com.social_media.instagram.model.entity.Subscribe;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.SubscribeReq;
import com.social_media.instagram.model.response.SubscribeRes;
import com.social_media.instagram.repository.SubscribeRepository;
import com.social_media.instagram.utils.Localization;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final Localization localization;
    @Transactional
    public SubscribeRes follow(SubscribeReq request, User user){
        UUID id = request.followingUserId();
        Subscribe subscribe = subscribeRepository.findByFollowingUserId(id)
                .orElse(subscribeRepository.save(Subscribe.builder().followingUserId(id).build()));
        Set<UUID> userIds = subscribe.getFollowersUserIds();
        if (userIds.contains(user.getId())) {
            userIds.remove(user.getId());
        }else {
            userIds.add(user.getId());
        }
        userIds.add(user.getId());
        subscribe.setFollowersUserIds(userIds);
        Subscribe saved = subscribeRepository.save(subscribe);
        log.info("{} followed to {}  : {}" ,user.getId(), saved.getFollowingUserId(), saved.getId());
        return SubscribeRes.from(SubscribeDto.from(saved));
    }
}
