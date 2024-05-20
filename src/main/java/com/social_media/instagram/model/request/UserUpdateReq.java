package com.social_media.instagram.model.request;

import java.time.LocalDate;

public record UserUpdateReq(
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
        String gender
) {}
