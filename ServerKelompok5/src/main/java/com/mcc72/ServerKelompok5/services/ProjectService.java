/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.ProjectDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.ProjectRepository;
import java.util.List;

import com.mcc72.ServerKelompok5.repositories.UserRepository;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public List<Project> getAll(){
        return projectRepository.findAll();
    }
    
    public Project getById(int id){
        return projectRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Project create(ProjectDto projectDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();

        Project project = new Project();
        project.setId(0);
        project.setStatus(false);
        project.setManager(employeeRepository.findById(user.getId()).get());
        project.setName(projectDto.getName());
        Employee employee = employeeRepository.findById(projectDto.getManagerId()).get();
        project.setEmployeeProject(Collections.singletonList(employee));
        return projectRepository.save(project);
    }
    
    public Project update(int id, ProjectDto projectDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Project project = new Project();
        getById(id);
        project.setId(id);
        project.setStatus(projectDto.getStatus());
        project.setName(projectDto.getName());
        project.setManager(employeeRepository.findById(user.getId()).get());
        return projectRepository.save(project);
    }
    
    public Project delete(int id){
        Project project = getById(id);
        projectRepository.delete(project);
        return project;
    }
}
