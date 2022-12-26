/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.config;


import com.mcc72.ServerKelompok5.models.entity.Role;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hendi
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository ur;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<UserEntity> optional = ur.findByUsername(username);
        if (optional.isPresent()) {
            UserEntity user = optional.get();
            if (!user.getIsActive()) {
                throw new UsernameNotFoundException("User not activated!!!");
            }
            //if attemp failed > 3 throw new error account locked
            if (user.getFailedAttempt() >= 3) {
                throw new BadCredentialsException("Account locked. Contact developer for more information.");
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<GrantedAuthority> authorities = getAuthorities(user.getUserRole());
//                authorities.add(new SimpleGrantedAuthority(user.getUserRole().get(0).getName()));
                
                 ur.setFailedAttemptForUser(0, user.getId());
                
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                ur.setFailedAttemptForUser(user.getFailedAttempt() + 1, user.getId());
                System.out.println("updateAttempt success");
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    public List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles
                .forEach(
                        role -> {
//                            String roleName = "ROLE_" + role.getName().toUpperCase();
                            authorities.add(new SimpleGrantedAuthority(role.getName()));
                            role.getPrivileges().forEach(
                                    privilege -> {
//                                        String privilegeName = privilege.getName().toUpperCase();
                                        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                                    }
                            );
                        }
                );
        System.out.println(authorities);
        return authorities;
    }
}
