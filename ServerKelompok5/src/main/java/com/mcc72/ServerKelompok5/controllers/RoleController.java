/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.controllers;


import com.mcc72.ServerKelompok5.models.entity.Role;
import com.mcc72.ServerKelompok5.services.RoleService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("role")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {
 private RoleService roleService;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return roleService.getAllMap();
    }
    
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Role insert(@RequestBody Role role){
        return roleService.insert(role);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Role update(@RequestBody Role role){
        return roleService.update(role);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return roleService.deleteById(id);
    }
}
