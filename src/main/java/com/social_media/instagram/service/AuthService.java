package com.social_media.instagram.service;

import com.social_media.instagram.config.security.JwtProvider;
import com.social_media.instagram.exceptions.BadRequestException;
import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.Subscribe;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.request.ChangePasswordRequest;
import com.social_media.instagram.model.request.LoginReq;
import com.social_media.instagram.model.request.UserReq;
import com.social_media.instagram.model.response.LoginRes;
import com.social_media.instagram.model.response.UserRes;
import com.social_media.instagram.repository.SubscribeRepository;
import com.social_media.instagram.repository.UserRepository;
import com.social_media.instagram.utils.Localization;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Localization localization;
    private final PasswordEncoder passwordEncoder;
    private final SubscribeRepository subscribeRepository;
    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(@Lazy JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User loadUserByUsername(String source) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmailOrPhone(source)
                .orElseThrow(() -> new UsernameNotFoundException(localization.getMessage("user_not_found")));
    }
    @Transactional
    public LoginRes register(UserReq request){
        if (userRepository.existsByUsernameAndPhoneAndEmail(request.username(), request.phone(), request.email())) {
            throw new BadRequestException(localization.getMessage("already_exists"));
        }
        User user = request.to().to();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        subscribeRepository.save(Subscribe.builder().followingUserId(saved.getId()).build());
        log.info("Saved user: {} {}", saved.getId(), saved.getUsername());
        return new LoginRes(UserRes.from(UserDto.from(saved)), jwtProvider.generateToken(saved.getUsername()));
    }
    public LoginRes login(LoginReq login){
        User user = loadUserByUsername(login.source());
        if (!passwordEncoder.matches(login.password(), user.getPassword())) {
            throw new BadRequestException(localization.getMessage("incorrect_password"));
        }
        Map<String, String> tokens = jwtProvider.generateToken(user.getUsername());
        return new LoginRes(UserRes.from(UserDto.from(user)), tokens);
    }
    public Map<String,String> refreshToken(String refreshToken){
        return jwtProvider.refreshToken(refreshToken);
    }
    public void updatePassword(ChangePasswordRequest request, User user){
        if (userRepository.updatePassword(request.newPassword(), request.oldPassword(), user.getId())==0) {
            throw new BadRequestException(localization.getMessage("incorrect_password"));
        }
    }
}
