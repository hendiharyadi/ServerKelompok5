/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.Role;

import java.util.List;
import java.util.Optional;

import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hendi
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Optional<Role> findByName(String name);
}
