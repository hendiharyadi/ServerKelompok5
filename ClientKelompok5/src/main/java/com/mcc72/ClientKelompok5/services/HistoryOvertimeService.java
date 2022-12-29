package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.entities.HistoryOvertime;
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
public class HistoryOvertimeService {
    
    private RestTemplate restTemplate;
    
    @Autowired
    public HistoryOvertimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${server.baseUrl}/history-overtime")
    private String url;
    
    public List<HistoryOvertime> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<HistoryOvertime>>(){
                }).getBody();
    }
    
    public HistoryOvertime getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<HistoryOvertime>() {
                }).getBody();
    }
}
