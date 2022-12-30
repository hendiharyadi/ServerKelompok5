package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ClientKelompok5.models.entities.Employee;
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
public class EmployeeService {
    
    private RestTemplate restTemplate;
    
    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${server.baseUrl}/employee")
    private String url;
    
    public List<Employee> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Employee>>(){
                }).getBody();
    }
    
    public Employee getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }
    
    public Employee create(UserRegistrationDto employee) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(employee),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }
    
    public Employee update(int id, UserRegistrationDto employee) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(employee),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }
    
    public String delete(int id) {
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
    }
}
