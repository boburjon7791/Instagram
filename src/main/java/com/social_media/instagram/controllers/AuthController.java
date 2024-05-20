package com.social_media.instagram.controllers;

import com.social_media.instagram.model.common.Header;
import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.ChangePasswordRequest;
import com.social_media.instagram.model.request.LoginReq;
import com.social_media.instagram.model.request.UserReq;
import com.social_media.instagram.model.response.LoginRes;
import com.social_media.instagram.model.response.UserRes;
import com.social_media.instagram.service.AuthService;
import com.social_media.instagram.utils.UtilData;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@RequestMapping(value = UtilData.BASE_URL+"auth")
public class AuthController {
    private final AuthService authService;
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Header<?> get(@AuthenticationPrincipal User user) {
        return Header.ok(UserRes.from(UserDto.from(user)));
    }
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Header<?> register(@Valid @RequestBody UserReq user) {
        return Header.created(authService.register(user));
    }
    @GetMapping("/login")
    public Header<?> login(@Valid @RequestBody LoginReq login) {
        return Header.ok(authService.login(login));
    }
    @PutMapping("/refresh-token")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> refreshToken(@RequestHeader(name = "refresh-token") String refreshToken) {
        return Header.created(authService.refreshToken(refreshToken));
    }
    @PutMapping("/change-password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Header<?> changePassword(@RequestBody ChangePasswordRequest request, @AuthenticationPrincipal User user){
        authService.updatePassword(request, user);
        return Header.modifying();
    }
}
