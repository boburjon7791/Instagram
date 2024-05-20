package com.social_media.instagram.model.request;

import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserReq(
        @NotBlank
        String username,

        @NotBlank
        String email,

        String profilePicture,

        String location,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        String bio,

        @NotNull
        LocalDate birthDate,

        String website,

        @NotBlank
        String phone,

        @NotBlank
        String gender,

        @NotBlank
        String password
) {
    public static UserReq of(
            String username,
            String email,
            String profilePicture,
            String location,
            String firstName,
            String lastName,
            String bio,
            LocalDate birthDate,
            String website,
            String phone,
            String gender,
            String password
    ){
        return new UserReq(
                username,
                email,
                profilePicture,
                location,
                firstName,
                lastName,
                bio,
                birthDate,
                website,
                phone,
                gender,
                password
        );
    }
    public UserDto to(){
        return UserDto.of(
                null,
                username,
                email,
                profilePicture,
                location,
                firstName,
                lastName,
                bio,
                birthDate,
                website,
                phone,
                Role.USER,
                gender,
                password
        );
    }
}
