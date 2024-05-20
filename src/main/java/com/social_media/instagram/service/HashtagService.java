package com.social_media.instagram.service;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.common.PaginationData;
import com.social_media.instagram.model.dto.HashtagDto;
import com.social_media.instagram.model.response.HashtagRes;
import com.social_media.instagram.repository.HashtagRepository;
import com.social_media.instagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {
    private final LikeRepository likeRepository;
    private final HashtagRepository hashtagRepository;
    public Header<?> findAllByContainsName(String name, Pageable pageable) {
        Page<HashtagRes> response = hashtagRepository.findAllByNameContains(name, pageable).map(HashtagDto::from).map(dto -> HashtagRes.from(dto, likeRepository));
        return Header.ok(response.getContent(), PaginationData.of(response));
    }
}
