package com.project.antifakebook.repository;

import com.project.antifakebook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    @Query("select u from UserEntity u where u.email = ?1 ")
    UserEntity findUserByEmailCustom(String email);
    @Query("select u from UserEntity u join VerificationTokenEntity v on u.id = v.userId where v.token = ?1 and u.email = ?2")
    UserEntity findUserByTokenAndEmail(String token,String email);
    @Transactional
    @Modifying
    @Query("update UserEntity u set u.coins = u.coins -4 where u.id = ?1")
    void minusUserFees(Long userId);
    @Query("select u.coins from UserEntity u where u.id = ?1")
    Integer getCoinsOfUser(Long userId);
}
