/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.services.PermissionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendi
 */
@AllArgsConstructor
@RestController
@RequestMapping("permission")
public class PermissionController {
    
    private PermissionService permissionService;
    
    @GetMapping
    public List<Permission> getAll(){
        return permissionService.getAll();
    }
    
    @GetMapping("/{id}")
    public Permission getById(@PathVariable int id) {
        return permissionService.getById(id);
    }
    
    @PostMapping
    public Permission insert(@RequestBody PermissionDto permission){
        return permissionService.create(permission);
    }
    
    @PutMapping("/{id}")
    public Permission update(@PathVariable Integer id, @RequestBody PermissionDto permission) {
        return permissionService.update(id, permission);
    }
    
    @DeleteMapping("/{id}")
    public Permission delete (@PathVariable Integer id){
        return permissionService.delete(id);
    }
}
