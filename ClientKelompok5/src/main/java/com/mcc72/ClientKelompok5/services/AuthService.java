/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.dto.LoginRequest;
import groovy.util.logging.Log;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MSI-JO
 */
@Service
@Slf4j
public class AuthService {

    private final RestTemplate restTemplate;
    @Value("${server.baseUrl}")
    private String url;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean userSignIn(LoginRequest loginRequest) throws Exception {
        try {
            ResponseEntity<Map<String, List<String>>> response = restTemplate.exchange(url + "/login", HttpMethod.POST, new HttpEntity<>(loginRequest),
                    new ParameterizedTypeReference<Map<String, List<String>>>() {});
            if (response.getStatusCode() == HttpStatus.OK){
                setPrinciple(response.getBody(), loginRequest);
                System.out.println("login");
                return true;
            }
        } catch (Exception e){
            System.out.println("Error : " + e.getMessage());
        }
        return false;
    }

    public void setPrinciple(Map<String, List<String>> res, LoginRequest loginRequest) {
        List<GrantedAuthority> authorities = res.get("authorities")
                .stream().map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
