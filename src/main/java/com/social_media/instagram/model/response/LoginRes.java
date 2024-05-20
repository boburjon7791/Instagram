package com.social_media.instagram.model.response;

import java.util.Map;

public record LoginRes(
        UserRes user,
        Map<String, String> token
) {}
