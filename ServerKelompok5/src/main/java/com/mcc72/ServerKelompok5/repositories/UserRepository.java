/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.Role;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hendi
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    public Optional<UserEntity> findByUsername(String username);
    
    @Modifying
    @Query("update UserEntity u set u.failedAttempt = ?1 where u.id = ?2")
    int setFailedAttemptForUser(Integer failedAttempt, Integer id);

    @Query("select u from UserEntity u where u.userRole = ?1")
    List<UserEntity> findByUserRole(Role userRole);
}
