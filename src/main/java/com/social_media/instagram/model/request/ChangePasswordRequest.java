package com.social_media.instagram.model.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {}
