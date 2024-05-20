package com.social_media.instagram.service;

import com.social_media.instagram.exceptions.NotFoundException;
import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.common.PaginationData;
import com.social_media.instagram.model.dto.LikeDto;
import com.social_media.instagram.model.dto.PostDto;
import com.social_media.instagram.model.entity.Hashtag;
import com.social_media.instagram.model.entity.Like;
import com.social_media.instagram.model.entity.Post;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.PostReq;
import com.social_media.instagram.model.request.PostUpdateReq;
import com.social_media.instagram.model.response.LikeRes;
import com.social_media.instagram.model.response.PostRes;
import com.social_media.instagram.repository.HashtagRepository;
import com.social_media.instagram.repository.LikeRepository;
import com.social_media.instagram.repository.PostRepository;
import com.social_media.instagram.utils.Localization;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final Localization localization;
    private final LikeRepository likeRepository;
    @Transactional
    public PostRes save(User user, PostReq request){
        List<String> newHashtags = extractHashtags(request.description());
        List<Hashtag> foundHashtags = hashtagRepository.findAllByNameIn(newHashtags);
        Post saved = postRepository.save(request.to(user).to());
        addToHashtags(saved, foundHashtags, newHashtags);
        log.info("Saved: {}", saved.getId());
        Like like=likeRepository.findByPostId(saved.getId())
                .orElseThrow(()->new NotFoundException(localization.getMessage("not_found")));
        Long count = likeRepository.countByPostId(saved.getId());
        return PostRes.from(PostDto.from(saved),like==null?null:LikeRes.from(LikeDto.from(like, count)));
    }
    public List<String> extractHashtags(String description){
        return Pattern.compile("(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)")
                .matcher(description)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
    public void addToHashtags(Post post, List<Hashtag> foundHashtags, List<String> newHashtags){
        Map<String, Hashtag> foundHashtagsMap = foundHashtags.stream()
                .collect(Collectors.toMap(Hashtag::getName, Function.identity()));
        newHashtags.forEach(hashtag -> {
            Hashtag existingHashtag = foundHashtagsMap.get(hashtag);
            if (existingHashtag == null) {
                existingHashtag = Hashtag.builder()
                        .name(hashtag)
                        .posts(new HashSet<>())
                        .build();
            }
            existingHashtag.getPosts().add(post);
            hashtagRepository.save(existingHashtag);
        });
    }
    public Header<?> findAll(User user, Pageable pageable){
        Page<PostRes> posts = postRepository.findAllByUser_Id(user.getId(), pageable).map(PostDto::from).map(postDto -> {
            Long count=likeRepository.countByPostId(postDto.id());
            Like like=likeRepository.findByPostId(postDto.id())
                            .orElseThrow(() -> new NotFoundException(localization.getMessage("not_found")));
            return PostRes.from(postDto, like==null?null:LikeRes.from(LikeDto.from(like, count)));
        });
        return Header.ok(posts.getContent(), PaginationData.of(posts));
    }
    @Transactional
    public void delete(User user, UUID postId){
        List<Hashtag> hashtags = hashtagRepository.findAllByPostIdAndUserId(postId, user.getId());
        hashtags.forEach(hashtag -> {
            Set<Post> posts = hashtag.getPosts();
            posts.removeIf(post -> post.getId().equals(postId));
            if (hashtagRepository.countByName(hashtag.getName())==0) {
                hashtagRepository.delete(hashtag);
                hashtags.removeIf(h -> h.getName().equals(hashtag.getName()));
            }
        });
        hashtagRepository.saveAll(hashtags);
        postRepository.deleteByUser_IdAndId(user.getId(), postId);
    }
    @Transactional
    public PostRes updateById(User user, PostUpdateReq request){
        postRepository.updatePostById(request.photoUrl(), request.description(), request.location(), request.id(), user.getId());
        Post post = postRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(localization.getMessage("not_found")));
        List<String> newHashtags=extractHashtags(request.description());
        List<Hashtag> foundHashtags = hashtagRepository.findAllByNameIn(newHashtags);
        addToHashtags(post, foundHashtags, newHashtags);
        Like like=likeRepository.findByPostId(post.getId())
                .orElseThrow(()->new NotFoundException(localization.getMessage("not_found")));
        Long count = likeRepository.countByPostId(post.getId());
        return PostRes.from(PostDto.from(post), like==null?null:LikeRes.from(LikeDto.from(like, count)));
    }
}
