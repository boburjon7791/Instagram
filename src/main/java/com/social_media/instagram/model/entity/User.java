package com.social_media.instagram.model.entity;

import com.social_media.instagram.model.entity.enums.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email", "phone"})
        },
        indexes = {
            @Index(name="users_indexes",columnList = "gender, birthDate, location, ")
        })
public class User extends BaseEntity implements UserDetails {
    @NotBlank
    private String username;
    
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    
    @NotBlank
    @Column(nullable = false)
    private String password;
    
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    
    private String bio;
    
    @Column(name = "profile_picture")
    private String profilePicture;

    @NotBlank
    @Column(nullable = false)
    private String gender;

    @NotNull
    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @NotBlank
    @Column(nullable = false)
    private String location;
    
    private String website;
    
    @NotBlank
    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User of(UUID id,
                          String username,
                          String email,
                          String password,
                          String phone,
                          String firstName,
                          String lastName,
                          String location,
                          String website,
                          String bio,
                          String gender,
                          LocalDate birthDate,
                          String profilePicture,
                          Role role){
        User user = new User(
                username,
                email,
                password,
                firstName,
                lastName,
                bio,
                profilePicture,
                gender,
                birthDate,
                location,
                website,
                phone,
                role
        );
        if (id!=null) {
            user.setId(id);
        }
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return !getBlocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !getBlocked();
    }

    @Override
    public boolean isEnabled() {
        return !getBlocked();
    }
}
