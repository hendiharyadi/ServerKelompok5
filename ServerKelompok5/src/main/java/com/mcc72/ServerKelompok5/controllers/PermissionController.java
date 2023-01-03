/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import com.mcc72.ServerKelompok5.services.HistoryPermissionService;
import com.mcc72.ServerKelompok5.services.PermissionService;
import com.mcc72.ServerKelompok5.services.StockLeaveService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private HistoryPermissionService historyPermissionService;
    private StockLeaveService stockLeaveService;
    private EmployeeRepository er;
    private UserRepository ur;
    private PermissionRepository pr;
    
    @GetMapping
    public Object getAll(){
        return permissionService.getAll();
    }

    @GetMapping("/manager")
    public List<Permission> getPermitByManager(){
        return permissionService.getByManager();
    }
    
    @GetMapping("/{id}")
    public Permission getById(@PathVariable int id) {
        return permissionService.getById(id);
    }
    
    @PostMapping
    public Permission insert(@RequestBody PermissionDto permission){
        Permission permit = permissionService.create(permission);
        if(permission.getLeave_type().equals(true)){
            permissionService.sendRequestMail(permission);
        } else {
            permissionService.sendRequestPermitMail(permission);
        }
        return permit;
    }
    
    @PutMapping("/{id}")
    public Permission update(@PathVariable Integer id, @RequestBody PermissionDto permission, Employee e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = ur.findByUsername(authentication.getName()).get();
        Permission permit = permissionService.update(id, permission);
            if (permission.getStatus().equals(true) && permission.getLeave_type().equals(true)){
                permissionService.sendConfirmationMail(id, permission);
                stockLeaveService.updateCuti(permission.getEmployee_id(), permission.getLeave_day());
            } else {
                permissionService.sendConfirmationMail(id, permission);
            }
            return permit;
    }
    
    @DeleteMapping("/{id}")
    public Permission delete (@PathVariable Integer id){
        return permissionService.delete(id);
    }
}
