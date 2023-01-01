package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.dto.PermissionDto;
import com.mcc72.ClientKelompok5.models.dto.PermissionResponse;
import com.mcc72.ClientKelompok5.models.entities.Overtime;
import com.mcc72.ClientKelompok5.models.entities.Permission;
import com.mcc72.ClientKelompok5.util.BasicHeader;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author HP
 */
@Service
public class PermissionService {
    
    private final RestTemplate restTemplate;

    @Autowired
    public PermissionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${server.baseUrl}/permission")
    private String url;
    
    public List<PermissionResponse> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<PermissionResponse>>(){
                }).getBody();
    }
    
    public PermissionResponse getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<PermissionResponse>() {
                }).getBody();
    }
    
    public PermissionResponse create(PermissionDto permission) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(permission),
                new ParameterizedTypeReference<PermissionResponse>() {
                }).getBody();
    }
    
    public PermissionResponse update(int id, PermissionDto permisssion) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(permisssion),
                new ParameterizedTypeReference<PermissionResponse>() {
                }).getBody();
    }
    
    public Permission delete(int id) {
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Permission>() {
                }).getBody();
    }
}
