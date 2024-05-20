package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.UserUpdateReq;
import com.social_media.instagram.model.response.UserRes;
import com.social_media.instagram.service.UserService;
import com.social_media.instagram.utils.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UtilData.BASE_URL+"user")
public class UserController {
    private final UserService userService;
    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> update(@AuthenticationPrincipal User user, @RequestBody Header<UserUpdateReq> request){
        return Header.modifying(userService.update(user, request.data));
    }
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> delete(@AuthenticationPrincipal User user){
        userService.delete(user);
        return Header.modifying();
    }
}
