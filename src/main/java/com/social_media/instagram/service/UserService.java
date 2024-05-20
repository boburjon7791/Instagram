package com.social_media.instagram.service;

import com.social_media.instagram.exceptions.NotFoundException;
import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.UserUpdateReq;
import com.social_media.instagram.model.response.UserRes;
import com.social_media.instagram.repository.UserRepository;
import com.social_media.instagram.utils.Localization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Localization localization;
    public void delete(User user){
        userRepository.updateDeletedTrueById(user.getId());
    }

    public UserRes update(User user, UserUpdateReq request) {
        userRepository.updateById(
                request.username(),
                request.email(),
                request.phone(),
                request.birthDate(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                request.profilePicture(),
                request.website(),
                request.bio(),
                request.location(),
                user.getId()
        );
        Supplier<RuntimeException> supplier=()->new NotFoundException(localization.getMessage("user_not_found"));
        User response = userRepository.findById(user.getId()).orElseThrow(supplier);
        return UserRes.from(UserDto.from(response));
    }
}
