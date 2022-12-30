package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.MyUser;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    UserEntity user = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));
    return new MyUser(user);
  }
}
