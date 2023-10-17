package com.project.antifakebook.repository;

import com.project.antifakebook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    @Query("select u from UserEntity u join VerificationTokenEntity v on u.id = v.userId where v.token = ?1")
    UserEntity findUserByToken(String token);
}
