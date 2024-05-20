package com.social_media.instagram.config;

import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.utils.UtilData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class CommonConfig {
    @Bean
    public AuditorAware<UUID> auditorAware(){
        return () -> Optional.of(Objects.requireNonNull(UtilData.getCurrentUser()).getId());
    }
}
