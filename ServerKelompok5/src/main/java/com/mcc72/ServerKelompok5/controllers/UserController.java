/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.controllers;


import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.services.UserEntityService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendi
 */
@AllArgsConstructor
@RestController
@RequestMapping("user")
//@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    private UserEntityService userEntityService;
    
    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return userEntityService.getAllMap();
    }
    
    @PostMapping
    public UserEntity insert(@RequestBody UserRegistrationDto userEntity){
        System.out.println("controller here");
        UserEntity urd = userEntityService.insert(userEntity);
        userEntityService.sendVerifyMail(userEntity);
        return urd;
    }

    @GetMapping("/verify/{username}/{token}")
    public String verify(@PathVariable String username,@PathVariable String token){
       Boolean isActivated = userEntityService.verify(username,token);
         return isActivated ? "Account Activated." : "Invalid Verification Code.";
    }

    @GetMapping("/managers")
    public List<UserEntity> findManagers(){
        return userEntityService.getManagers();
    }
}
