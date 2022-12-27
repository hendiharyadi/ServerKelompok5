/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Hendi
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    public Optional<UserEntity> findByUsername(String username);
    
    @Modifying
    @Query("update UserEntity u set u.failedAttempt = ?1 where u.id = ?2")
    int setFailedAttemptForUser(Integer failedAttempt, Integer id);
    
    @Modifying
    @Query("update UserEntity u set u.stockLeave = ?1 where u.id = ?2")
    int setStockLeave(Integer stockLeave, Integer id);
    
}
