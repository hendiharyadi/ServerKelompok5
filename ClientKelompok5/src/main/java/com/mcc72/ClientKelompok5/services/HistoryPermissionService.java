package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.entities.HistoryPermission;
import com.mcc72.ClientKelompok5.util.BasicHeader;
import java.util.List;
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
public class HistoryPermissionService {
    
    private RestTemplate restTemplate;
    
    @Autowired
    public HistoryPermissionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${server.baseUrl}/history-permission")
    private String url;
    
    public List<HistoryPermission> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<HistoryPermission>>(){
                }).getBody();
    }
    
    public HistoryPermission getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<HistoryPermission>() {
                }).getBody();
    }
}
