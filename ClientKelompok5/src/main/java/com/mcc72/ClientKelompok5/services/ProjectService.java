package com.mcc72.ClientKelompok5.services;

import com.mcc72.ClientKelompok5.models.dto.ProjectDto;
import com.mcc72.ClientKelompok5.models.dto.ProjectResponse;
import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.models.entities.Project;
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
public class ProjectService {
    
    private RestTemplate restTemplate;

    @Autowired
    public ProjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${server.baseUrl}/project")
    private String url;
    
    public List<ProjectResponse> getAll(){
        return restTemplate.exchange(url + "/manager", HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<ProjectResponse>>(){
                }).getBody();
    }

    public List<Employee> getAllMembers(int id){
        return restTemplate.exchange(url + "/members/" + id, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Employee>>(){
                }).getBody();
    }
    
    public ProjectResponse getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<ProjectResponse>() {
                }).getBody();
    }
    
    public Project create(ProjectDto project) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(project),
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }
    
    public Project update(int id, ProjectDto project) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(project),
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }
    
    public Project delete(int id) {
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }
}
