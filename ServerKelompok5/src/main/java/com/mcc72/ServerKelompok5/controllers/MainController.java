/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.controllers;



import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.services.UserEntityService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendi
 */
@RestController
@AllArgsConstructor
// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class MainController {

    private UserEntityService ues;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
     
    @PostMapping("/login-user")
    public Map<String, Object> login(@RequestBody UserEntity userEntity) throws Exception{
    Authentication authentication;

            authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

       return ues.getLoginResponse();
    }
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("get-user")
    public String getUser() {
//        ALT 1
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return SecurityContextHolder.getContext().getName();
        
//        ALT 2
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("get-roles")
    public String getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toString();
    }
}
