package com.social_media.instagram.model.dto;

import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.model.entity.enums.Role;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
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
        String gender,
        String password
) {
    public static UserDto of(
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
            String gender,
            String password
    ) {
        return new UserDto(
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
                gender,
                password
        );
    }
    public User to(){
        return User.of(
                id,
                username,
                email,
                password,
                phone,
                firstName,
                lastName,
                location,
                website,
                bio,
                gender,
                birthDate,
                profilePicture,
                role
        );
    }
    public static UserDto from(User user){
        return UserDto.of(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfilePicture(),
                user.getLocation(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getBirthDate(),
                user.getWebsite(),
                user.getPhone(),
                user.getRole(),
                user.getGender(),
                user.getPassword()
        );
    }
}
