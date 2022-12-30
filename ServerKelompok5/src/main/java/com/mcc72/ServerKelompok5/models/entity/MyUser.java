package com.mcc72.ServerKelompok5.models.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUser implements UserDetails{

    private final UserEntity userEntity;

    public MyUser(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userEntity.getUserRole()
        .forEach(
        role ->  {
            String roleName = role.getName().toUpperCase();
            authorities.add(new SimpleGrantedAuthority(roleName));
            role.getPrivileges().forEach(
            privilege -> {
                String privilegeName = privilege.getName().toUpperCase();
                authorities.add(new SimpleGrantedAuthority(privilegeName));
            }
            );
        }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  userEntity.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
