package com.social_media.instagram.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
        @NotBlank
        String source,

        @NotBlank
        String password
) {}
