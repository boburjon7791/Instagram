package com.social_media.instagram.model.response;

import com.social_media.instagram.model.dto.UserDto;
import com.social_media.instagram.model.entity.enums.Role;

import java.time.LocalDate;
import java.util.UUID;

public record UserRes(
        UUID id,
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
        Role role,
        String gender
) {
    public static UserRes of(UUID id,
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
                             Role role,
                             String gender
    ){
        return new UserRes(
                id,
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
                role,
                gender
        );
    }
    public static UserRes from(UserDto dto) {
        return UserRes.of(
                dto.id(),
                dto.username(),
                dto.email(),
                dto.profilePicture(),
                dto.location(),
                dto.firstName(),
                dto.lastName(),
                dto.bio(),
                dto.birthDate(),
                dto.website(),
                dto.phone(),
                dto.role(),
                dto.gender()
        );
    }
}
