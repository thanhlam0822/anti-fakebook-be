package com.project.antifakebook.repository;

import com.project.antifakebook.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerifyTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {
    @Query(value = "select t from VerificationTokenEntity t join UserEntity a on t.userId = a.id where a.email = ?1")
    VerificationTokenEntity getVerifyTokenByEmail(String email);

    @Query("select v from VerificationTokenEntity v where v.userId  = ?1")
    VerificationTokenEntity getVerifyTokenByUserId(Long userId);
}
