/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.config;


import com.github.javafaker.Faker;
import com.mcc72.ServerKelompok5.services.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Hendi
 */
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

//    private CustomAuthenticationProvider authenticationProvider;
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic();
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/role***").permitAll()
//                .antMatchers("/user***").permitAll()
////                .antMatchers("/user/*").hasAnyRole("ADMIN","USER")
//                .antMatchers("/activatedUser*").hasAnyRole("USER")
////                .antMatchers("/**").authenticated()
////                .antMatchers("/landing-page").permitAll()
////                .and()
////                .formLogin()
//                .and()
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .authenticationProvider(authenticationProvider);
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }

}
