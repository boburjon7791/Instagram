package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.response.HashtagRes;
import com.social_media.instagram.service.HashtagService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UtilData.BASE_URL+"hashtag")
public class HashtagController {
    private final HashtagService hashtagService;
    @GetMapping
    public Header<?> getByName(@RequestParam(name = "name") String name,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(name = "size", required = false, defaultValue = "25") int size) {
        return hashtagService.findAllByContainsName(name, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
