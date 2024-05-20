package com.social_media.instagram.config.security;

import com.social_media.instagram.exceptions.UnauthorizedException;
import com.social_media.instagram.model.entity.User;
import com.social_media.instagram.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value(value = "${secret.key}")
    private String secret;

    private final AuthService authService;

    private static final long EXPIRATION_TIME_FOR_ACCESS_TOKEN = 60 * 60 * 24;
    private static final Date EXPIRATION_DATE_FOR_ACCESS_TOKEN = new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_ACCESS_TOKEN);

    private static final long EXPIRATION_TIME_FOR_REFRESH_TOKEN = 60 * 60 * 24 * 7;
    private static final Date EXPIRATION_DATE_FOR_REFRESH_TOKEN = new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_REFRESH_TOKEN);
    public Map<String,String> generateToken(@NonNull String source) {
        return Map.of(
                "accessToken",Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer("Instagram")
                .setExpiration(EXPIRATION_DATE_FOR_ACCESS_TOKEN)
                .setSubject(source)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact(),
                "refreshToken",Jwts.builder()
                        .setIssuedAt(new Date())
                        .setIssuer("Instagram")
                        .setExpiration(EXPIRATION_DATE_FOR_REFRESH_TOKEN)
                        .setSubject(source)
                        .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                        .compact());
    }
    public User extractToken(@NonNull String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException();
            }
            return authService.loadUserByUsername(claims.getSubject());
        }catch (Exception e){
            throw new UnauthorizedException("Jwt token could not be parsed");
        }
    }
    public Map<String, String> refreshToken(@NonNull String refreshToken) {
        return generateToken(extractToken(refreshToken).getUsername());
    }
    private Key getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
