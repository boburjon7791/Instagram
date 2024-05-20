package com.social_media.instagram.service;

import com.social_media.instagram.exceptions.NotFoundException;
import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.common.PaginationData;
import com.social_media.instagram.model.dto.CommentDto;
import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.Comment;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.CommentReq;
import com.social_media.instagram.model.response.CommentRes;
import com.social_media.instagram.repository.CommentRepository;
import com.social_media.instagram.repository.UserRepository;
import com.social_media.instagram.utils.Localization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final Localization localization;

    public CommentRes createComment(CommentReq request, User user) {
        User replied=null;
        if (request.repliedTo()!=null) {
            replied = userRepository.findById(request.repliedTo())
                    .orElseThrow(()->new NotFoundException(localization.getMessage("not_found")));
        }
        CommentDto commentDto = request.to(UserDto.from(user), replied == null ? null : UserDto.from(replied));
        Comment saved = commentRepository.save(commentDto.to());
        return CommentRes.from(CommentDto.from(saved));
    }
    public void delete(UUID commentId, User user){
        if (commentRepository.deleteByIdAndUserId(commentId, user.getId())==0) {
            throw new NotFoundException(localization.getMessage("not_found"));
        }
    }

    public Header<?> getAll(UUID postId, User user, Pageable pageable) {
        Page<CommentRes> comments = commentRepository.findAllByPostIdAndAuthor(postId, user.getId(), pageable).map(CommentDto::from).map(CommentRes::from);
        return Header.ok(comments.getContent(), PaginationData.of(comments));
    }
}
