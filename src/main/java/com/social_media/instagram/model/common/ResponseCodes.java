package com.social_media.instagram.model.common;

public enum ResponseCodes {
    OK(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    NO_CONTENT(204);
    final int code;
    ResponseCodes(int code) {
        this.code = code;
    }
}
