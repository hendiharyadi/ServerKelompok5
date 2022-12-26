/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class PermissionService {
    
    private PermissionRepository permissionRepository;
    
    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }
    
    public Permission getById(int id){
        return permissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Permission create(Permission permission){
        return permissionRepository.save(permission);
    }
    
    public Permission update(int id, Permission permission){
        getById(id);
        permission.setId(id);
        return permissionRepository.save(permission);
    }
    
    public Permission delete (int id){
        Permission permission = getById(id);
        permissionRepository.delete(permission);
        return permission;
    }
}
