package com.social_media.instagram.model.request;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record PostUpdateReq(
        @NotNull
        UUID id,
        Set<String> photoUrl,
        String description,
        String location
) {}
