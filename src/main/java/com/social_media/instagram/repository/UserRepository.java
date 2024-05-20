package com.social_media.instagram.repository;

import com.social_media.instagram.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
  @Query("from User u where u.username=?1 or u.email=?1 or u.phone=?1")
  Optional<User> findByUsernameOrEmailOrPhone(String username);

  @Query("select (count(u) > 0) from User u where u.username = ?1 and u.phone = ?2 and u.email = ?3")
  boolean existsByUsernameAndPhoneAndEmail(@NonNull String username, @NonNull String phone, @NonNull String email);

  @Modifying
  @Transactional
  @Query("update User u set u.deleted=true where u.id=?1")
  void updateDeletedTrueById(UUID id);

  @Modifying
  @Transactional
  @Query("update User u set u.password=?1 where u.password=?2 and u.id=?3")
  int updatePassword(@NonNull String newPassword, @NonNull String oldPassword, @NonNull UUID id);
  @Modifying
  @Transactional
  @Query("""
    update User u
    set u.username=coalesce(?1, u.username),
        u.email=coalesce(?2, u.email),
        u.phone=coalesce(?3, u.phone),
        u.birthDate=coalesce(?4, u.birthDate),
        u.firstName=coalesce(?5, u.firstName),
        u.lastName=coalesce(?6, u.lastName),
        u.gender=coalesce(?7, u.gender),
        u.profilePicture=coalesce(?8, u.profilePicture),
        u.website=coalesce(?9, u.website),
        u.bio=coalesce(?10, u.bio),
        u.location=coalesce(?11, u.location)
    where u.id=?12 and u.deleted=false and u.blocked=false
    """)
  void updateById(
          String username, // ?1
          String email, // ?2
          String phone, // ?3
          LocalDate birthDate, // ?4
          String firstName, // ?5
          String lastName, // ?6
          String gender, // ?7
          String profilePicture, // ?8
          String website,  // ?9
          String bio,  // ?10
          String location,  // ?11
          UUID id  // ?12
  );
}