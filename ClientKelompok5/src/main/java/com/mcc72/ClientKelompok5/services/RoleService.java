package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.dto.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoleService {
    private RestTemplate restTemplate;

    @Value("${server.baseUrl}")
    private String url;
    @Autowired
    public RoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RoleResponse> getRoles(){
        return restTemplate.exchange(url + "/role", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<RoleResponse>>() {}).getBody();
    }

}
