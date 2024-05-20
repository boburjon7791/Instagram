package com.social_media.instagram.utils;

import com.social_media.instagram.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UtilData {
    String BASE_URL = "/api/";
    static User getCurrentUser() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
}
